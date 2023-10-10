# meta-rockchip (Yocto BSP layer for Vicharak SBC boards)

Welcome to the meta-rockchip Yocto BSP layer for Vicharak SBC boards.

This README provides information on building and booting with this layer.

## Dependencies

This layer depends on the following components:

- **Yocto Project Poky Layer**:
  - URI: git://git.yoctoproject.org/poky
  - Branch: mickledore

- **OpenEmbedded Layer**:
  - URI: git://git.openembedded.org/meta-openembedded
  - Layers: meta-oe
  - Branch: mickledore

## Table of Contents

- I. Configure Yocto/OE Environment
- II. Building meta-rockchip BSP Layers
- III. Booting Your Device

### I. Configure Yocto/OE Environment

To build an image with BSP support for a specific release, follow these steps:

1. Create a directory for your Yocto environment and navigate to it:

```bash
mkdir yocto; cd yocto
```

2. Clone the Yocto Poky layer:

```bash
git clone git://git.yoctoproject.org/poky -b mickledore
```

3. Clone the OpenEmbedded layer:

```bash
git clone git://git.openembedded.org/meta-openembedded -b mickledore
```

4. Place the `meta-rockchip` layer in the same directory.

```bash
git clone https://github.com/vicharak-in/meta-rockchip -b mickledore
```

5. Source the configuration script:

```bash
source oe-init-build-env
```

6. Ensure that your `bblayers.conf` file includes the location of the
   **meta-rockchip** layer along with other required layers.

> [!NOTE]
> Example `conf/bblayers.conf`:
>
> ```Makefile
> POKY_BBLAYERS_CONF_VERSION = "2"
>
> BBPATH = "${TOPDIR}"
> BBFILES ?= ""
>
> BBLAYERS ?= " \
>   ${TOPDIR}/../meta-openembedded/meta-oe \
>   ${TOPDIR}/../meta-openembedded/meta-python \
>   ${TOPDIR}/../meta-openembedded/meta-networking \
>   ${TOPDIR}/../meta-openembedded/meta-filesystems \
>   ${TOPDIR}/../meta-openembedded/meta-multimedia \
>   ${TOPDIR}/../meta-qt5 \
>   ${TOPDIR}/../meta-clang \
>   ${TOPDIR}/../meta-rockchip \
>   ${TOPDIR}/../poky/meta \
>   ${TOPDIR}/../poky/meta-poky \
>   ${TOPDIR}/../poky/meta-yocto-bsp \
> "
> ```

7. To enable a specific machine, add a `MACHINE` line to your `local.conf` file.

> [!NOTE]
> You can find the list of supported machines in the `meta-rockchip/conf/machine`
>
> Example `conf/local.conf`:
>
> ```Makefile
> MACHINE ?= "rk3399-vaaman"
> ```

8. Enable systemd in your Yocto configuration by adding the following to your
   `local.conf` file

> [!NOTE]
> Example `conf/local.conf`:
>
> ```Makefile
> INIT_MANAGER = "systemd"
> ```

### II. Building meta-rockchip BSP Layers

Once your environment is configured, you can build an image as follows:

```bash
bitbake core-image-full-cmdline
```

> [!NOTE]
> The list of buildable targets are:
>
> ```text
> core-image-minimal
> core-image-full-cmdline
> core-image-sato
> core-image-weston
> meta-toolchain
> meta-ide-support
> ```

After a successful build, you will find the resulting `.wic` image in
`/path/to/yocto/build/tmp/deploy/images/<MACHINE>/`, along with a Rockchip
firmware image named `update.img`.

### III. Booting Your Device

To boot your device under Linux, you can use `Rockchip upgrade_tool`.
Here are the steps:

1. Put your device into Rockusb or Maskrom mode. [Learn more](http://docs.vicharak.in/vaaman-maskrom-mode.html)

> [!NOTE]
> If your device is in maskrom Rockusb mode, try entering miniloader Rockusb mode:
>
> ```bash
> sudo upgrade_tool db <image_path>/loader.bin
> ```

2. Flash the firmware image:

Flash the image (either the `.wic` image or the Rockchip
firmware image `update.img`) to your device.:

For `.wic` image:

```bash
sudo upgrade_tool wl 0 <path/to/yocto/build/tmp/deploy/images/<MACHINE>/<IMAGE_NAME>.wic
```

For Rockchip firmware image:

```bash
sudo upgrade_tool wl 0 <path/to/yocto/build/tmp/deploy/images/<MACHINE>/update.img
```

## License

[MIT License](./LICENSE)
