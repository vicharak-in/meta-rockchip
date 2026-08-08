# Yocto Configuration for `rk3588-axon` MACHINE

## I. `local.conf` File

For Minimal Image, Look at **local-minimal.conf**

You have to copy the content of **local-minimal.conf** to **local.conf** located in `build/conf/` directory.

## II. Compiling Image

```bash
source poky/oe-init-build-env
```

For Minimal Image :

```bash
bitbake core-image-minimal -v
```

### III. Flash image in Axon

1. Put Device into [MaskRom Mode](!https://www.youtube.com/watch?v=rW-R1MJhBGA&amp;ab_channel=Vicharak)

2. If it's maskrom mode, try to load `rk3588_spl_loader_v1.14.113.bin`:

**Image_PATH** : `build/tmp/deploy/images/rk3588-axon/`

```shell
sudo upgrade_tool db <IMAGE PATH>/rk3588_spl_loader_v1.14.113.bin
```

3. Flash the image (wic image or rockchip firmware image)

For RK3588 Based Axon, Use **.wic** image.

```shell
sudo upgrade_tool wl 0 <IMAGE PATH>/<IMAGE NAME>.wic # For wic image
```
