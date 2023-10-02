# Copyright (C) 2020, Rockchip Electronics Co., Ltd
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-yocto.inc
require linux-rockchip.inc

inherit local-git
inherit vicharak-extlinux-config

SRCREV = "0b91897440fd55f9b1fa1b3da2e8bb08e1871def"

SRC_URI = " \
	git://github.com/UtsavBalar1231/kernel_rockchip_linux;protocol=https;branch=vaaman-4.19; \
	file://${THISDIR}/files/rk3399_vaaman.cfg \
	file://${THISDIR}/files/cgroups.cfg \
"

DEPENDS += "u-boot-rockchip"
PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES:${KERNEL_PACKAGE_NAME}-image += " \
    /boot/Image* \
    /boot/extlinux \
    /boot/overlays \
    /boot/rockchip \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

BB_GIT_SHALLOW = "1"

KERNEL_VERSION_SANITY_SKIP = "1"
LINUX_VERSION ?= "4.19"

SRC_URI:append = " ${@bb.utils.contains('IMAGE_FSTYPES', 'ext4', \
		   'file://${THISDIR}/files/ext4.cfg', \
		   '', \
		   d)}"

do_compile:append() {
    oe_runmake dtbs
}

addtask do_create_extlinux_config before do_install

do_install:append() {
    install -d ${D}/boot/extlinux
    install -m 0644 ${B}/arch/${ARCH}/boot/${KERNEL_IMAGETYPE} ${D}/boot/
    install -d ${D}/boot/rockchip
    install -m 0644 ${B}/arch/${ARCH}/boot/dts/${KERNEL_DEVICETREE} ${D}/boot/${KERNEL_DEVICETREE}
    if [ -d ${B}/arch/${ARCH}/boot/dts/rockchip/overlays ]; then
        install -d ${D}/boot/overlays
        install -m 0644 ${B}/arch/${ARCH}/boot/dts/rockchip/overlays/*.disabled ${D}/boot/overlays/
    fi
	install -m 0644 ${B}/extlinux.conf* ${D}/boot/extlinux/
}
