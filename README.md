# meta-rockchip

Yocto BSP layer for the Rockchip SOC boards
  - wiki <http://opensource.rock-chips.com/wiki_Main_Page>.

This README file contains information on building and booting the meta-rockchip BSP layers.

Please see the corresponding sections below for details.

## Dependencies

This layer depends on:

```bash
# Clone Poky (Yocto Project Reference Distribution)
git clone -b scarthgap https://github.com/yoctoproject/poky.git

# Clone OpenEmbedded Meta Layer
git clone -b scarthgap https://github.com/openembedded/meta-openembedded.git

# Clone Clang Compiler Layer
git clone -b scarthgap https://github.com/kraj/meta-clang.git

# Clone Board Support Package (BSP) Layer for Rockchip / Axon
git clone -b scarthgap https://github.com/vicharak-in/meta-rockchip.git
```

## Table of Contents

I. Configure yocto/oe Environment

II. Building meta-rockchip BSP Layers

III. Booting your Device

IV. Tested Hardwares

V. Supporting new Machine

### I. Configure yocto/oe Environment

In order to build an image with BSP support for a given release, you need to download the corresponding layers described in the "Dependencies" section. Be sure that everything is in the same directory.

```shell
mkdir yocto && cd yocto
```

```shell
git clone git://git.yoctoproject.org/poky -b scarthgap
git clone git://git.openembedded.org/meta-openembedded.git -b scarthgap
git clone https://github.com/kraj/meta-clang.git -b scarthgap
```

And put the meta-rockchip layer here too.

```bash
git clone -b scarthgap https://github.com/vicharak-in/meta-rockchip.git
```
Then you need to source the configuration script:

```shell
source poky/oe-init-build-env
```

Having done that, you can build a image for a rockchip board by adding the location of the meta-rockchip layer to bblayers.conf, along with any other layers needed.

For example:

```makefile
# POKY_BBLAYERS_CONF_VERSION is increased each time build/conf/bblayers.conf
# changes incompatibly
POKY_BBLAYERS_CONF_VERSION = "2"
BBPATH = "${TOPDIR}"
BBFILES ?= ""
BBLAYERS ?= " \
  ${TOPDIR}/../poky/meta \
  ${TOPDIR}/../poky/meta-poky \
  ${TOPDIR}/../poky/meta-yocto-bsp \
  ${TOPDIR}/../meta-rockchip \
  ${TOPDIR}/../meta-openembedded/meta-oe \
  ${TOPDIR}/../meta-openembedded/meta-xfce \
  ${TOPDIR}/../meta-openembedded/meta-gnome \
  ${TOPDIR}/../meta-openembedded/meta-python \
  ${TOPDIR}/../meta-openembedded/meta-networking \
  ${TOPDIR}/../meta-openembedded/meta-filesystems \
  ${TOPDIR}/../meta-openembedded/meta-multimedia \
  ${TOPDIR}/../meta-clang \
  ${TOPDIR}/workspace \
"
```

To enable a particular machine, you need to add a MACHINE line naming the BSP to the local.conf file:

```makefile
  MACHINE = "xxx"
```

For, RK3588 Based Axon : `rk3588-axon`

All supported machines can be found in meta-rockchip/conf/machine.

And skip a few patch checks:
```makefile
  WARN_QA:remove = "patch-fuzz"
  ERROR_QA:remove = "patch-status"
```

### II. Building meta-rockchip BSP Layers

```shell
bitbake core-image-minimal -v
```

At the end of a successful build, you should have an .wic image in `/path/to/yocto/build/tmp/deploy/images/<MACHINE>/`, also with an rockchip firmware image: `update.img`.

### III. Booting your Device

Under Linux, you can use upgrade_tool: <http://opensource.rock-chips.com/wiki_Upgradetool> to flash the image:

1. Put your device into rockusb mode: <http://opensource.rock-chips.com/wiki_Rockusb>

2. If it's maskrom rockusb mode, try to enter miniloader rockusb mode:

```shell
$ sudo upgrade_tool db <IMAGE PATH>/loader.bin
```

3. Flash the image (wic image or rockchip firmware image)

For RK3588 Based Axon, Use **.wic** image.

```shell
$ sudo upgrade_tool wl 0 <IMAGE PATH>/<IMAGE NAME>.wic # For wic image
```


```shell
$ sudo upgrade_tool uf <IMAGE PATH>/update.img # For rockchip firmware image
```

### IV. Tested Hardwares

The following undergo regular basic testing with their respective MACHINE types.

* RK3588 based Axon board

### V. Supporting new Machine

To support new machine, you can either add new machine config in meta-rockchip/conf/machine, or choose a similar existing machine and override it's configurations in local config file.
