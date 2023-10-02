# vicharak-extlinux-config.bbclass
#
# This class allow the extlinux.conf generation for U-Boot use. The
# U-Boot support for it is given to allow the Generic Distribution
# Configuration specification use by OpenEmbedded-based products.
#
# External variables:
#
# UBOOT_EXTLINUX_CONSOLE           - Set to "console=ttyX" to change kernel boot
#                                    default console.
# UBOOT_EXTLINUX_LABELS            - A list of targets for the automatic config.
# UBOOT_EXTLINUX_KERNEL_ARGS       - Add additional kernel arguments.
# UBOOT_EXTLINUX_KERNEL_IMAGE      - Kernel image name.
# UBOOT_EXTLINUX_FDTDIR            - Device tree directory.
# UBOOT_EXTLINUX_FDT               - Device tree file.
# UBOOT_EXTLINUX_INITRD            - Indicates a list of filesystem images to
#                                    concatenate and use as an initrd (optional).
# UBOOT_EXTLINUX_MENU_DESCRIPTION  - Name to use as description.
# UBOOT_EXTLINUX_ROOT              - Root kernel cmdline.
# UBOOT_EXTLINUX_TIMEOUT           - Timeout before DEFAULT selection is made.
#                                    Measured in 1/10 of a second.
# UBOOT_EXTLINUX_DEFAULT_LABEL     - Target to be selected by default after
#                                    the timeout period
#
# If there's only one label system will boot automatically and menu won't be
# created. If you want to use more than one labels, e.g linux and alternate,
# use overrides to set menu description, console and others variables.
#
# Ex:
#
# UBOOT_EXTLINUX_LABELS ??= "default fallback"
#
# UBOOT_EXTLINUX_DEFAULT_LABEL ??= "Linux Default"
# UBOOT_EXTLINUX_TIMEOUT ??= "30"
#
# UBOOT_EXTLINUX_KERNEL_IMAGE:default ??= "../zImage"
# UBOOT_EXTLINUX_MENU_DESCRIPTION:default ??= "Linux Default"
#
# UBOOT_EXTLINUX_KERNEL_IMAGE:fallback ??= "../zImage-fallback"
# UBOOT_EXTLINUX_MENU_DESCRIPTION:fallback ??= "Linux Fallback"
#
# Results:
#
# menu title Select the boot mode
# TIMEOUT 30
# DEFAULT Linux Default
# LABEL Linux Default
#   KERNEL ../zImage
#   FDTDIR ../
#   APPEND root=/dev/mmcblk2p2 rootwait rw console=${console}
# LABEL Linux Fallback
#   KERNEL ../zImage-fallback
#   FDTDIR ../
#   APPEND root=/dev/mmcblk2p2 rootwait rw console=${console}
#
# Copyright (C) 2016, O.S. Systems Software LTDA.  All Rights Reserved
# Released under the MIT license (see packages/COPYING)
#
# The kernel has an internal default console, which you can override with
# a console=...some_tty...
UBOOT_EXTLINUX_CONSOLE ??= "console=${console},${baudrate}"
UBOOT_EXTLINUX_LABELS ??= "linux-vicharak"
UBOOT_EXTLINUX_FDT ??= ""
UBOOT_EXTLINUX_FDTDIR ??= "../"
UBOOT_EXTLINUX_KERNEL_IMAGE ??= "../${KERNEL_IMAGETYPE}"
UBOOT_EXTLINUX_KERNEL_ARGS ??= "rootwait rw"
UBOOT_EXTLINUX_MENU_DESCRIPTION:linux ??= "${DISTRO_NAME}"

UBOOT_EXTLINUX_CONFIG = "${B}/extlinux.conf"

python do_create_extlinux_config() {
    if d.getVar("UBOOT_EXTLINUX") != "1":
      return

    if not d.getVar('WORKDIR'):
        bb.error("WORKDIR not defined, unable to package")

    labels = d.getVar('UBOOT_EXTLINUX_LABELS')
    if not labels:
        bb.fatal("UBOOT_EXTLINUX_LABELS not defined, nothing to do")

    if not labels.strip():
        bb.fatal("No labels, nothing to do")

    cfile = d.getVar('UBOOT_EXTLINUX_CONFIG')
    if not cfile:
        bb.fatal('Unable to read UBOOT_EXTLINUX_CONFIG')

    localdata = bb.data.createCopy(d)

    try:
        with open(cfile, 'w') as cfgfile:
            cfgfile.write('# Auto-generated extlinux configuration for Vicharak\n')

            if len(labels.split()) > 1:
                cfgfile.write('menu title Select the boot mode\n')

            timeout =  localdata.getVar('UBOOT_EXTLINUX_TIMEOUT')
            if timeout:
                cfgfile.write('TIMEOUT %s\n' % (timeout))

            if len(labels.split()) > 1:
                default = localdata.getVar('UBOOT_EXTLINUX_DEFAULT_LABEL')
                if default:
                    cfgfile.write('DEFAULT %s\n' % (default))

            # Need to deconflict the labels with existing overrides
            label_overrides = labels.split()
            default_overrides = localdata.getVar('OVERRIDES').split(':')
            # We're keeping all the existing overrides that aren't used as a label
            # an override for that label will be added back in while we're processing that label
            keep_overrides = list(filter(lambda x: x not in label_overrides, default_overrides))

            for label in labels.split():

                localdata.setVar('OVERRIDES', ':'.join(keep_overrides + [label]))

                extlinux_console = localdata.getVar('UBOOT_EXTLINUX_CONSOLE')

                menu_description = localdata.getVar('UBOOT_EXTLINUX_MENU_DESCRIPTION')
                if not menu_description:
                    menu_description = label

                root = localdata.getVar('UBOOT_EXTLINUX_ROOT')
                if not root:
                    bb.fatal('UBOOT_EXTLINUX_ROOT not defined')

                kernel_image = localdata.getVar('UBOOT_EXTLINUX_KERNEL_IMAGE')
                fdtdir = localdata.getVar('UBOOT_EXTLINUX_FDTDIR')

                fdt = localdata.getVar('UBOOT_EXTLINUX_FDT')

                if fdt:
                    cfgfile.write('LABEL %s\n\tKERNEL %s\n\tFDT %s\n' %
                                 (menu_description, kernel_image, fdt))
                elif fdtdir:
                    cfgfile.write('LABEL %s\n\tKERNEL %s\n\tFDTDIR %s\n' %
                                 (menu_description, kernel_image, fdtdir))
                else:
                    cfgfile.write('LABEL %s\n\tKERNEL %s\n' % (menu_description, kernel_image))

                kernel_args = localdata.getVar('UBOOT_EXTLINUX_KERNEL_ARGS')

                initrd = localdata.getVar('UBOOT_EXTLINUX_INITRD')
                if initrd:
                    cfgfile.write('\tINITRD %s\n'% initrd)

                kernel_args = root + " " + kernel_args
                cfgfile.write('\tAPPEND %s %s\n' % (kernel_args, extlinux_console))

    except OSError:
        bb.fatal('Unable to open %s' % (cfile))
}
UBOOT_EXTLINUX_VARS = "CONSOLE MENU_DESCRIPTION ROOT KERNEL_IMAGE FDTDIR FDT KERNEL_ARGS INITRD"
do_create_extlinux_config[vardeps] += "${@' '.join(['UBOOT_EXTLINUX_%s_%s' % (v, l) for v in d.getVar('UBOOT_EXTLINUX_VARS').split() for l in d.getVar('UBOOT_EXTLINUX_LABELS').split()])}"
do_create_extlinux_config[vardepsexclude] += "OVERRIDES"

addtask create_extlinux_config before do_install do_deploy after do_compile
