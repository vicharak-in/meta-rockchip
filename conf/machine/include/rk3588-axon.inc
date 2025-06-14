SOC_FAMILY = "rk3588"

require conf/machine/include/arm/arch-armv8a.inc
require conf/machine/include/soc-family.inc
MACHINEOVERRIDES =. "mali-gpu:mali-midgard:"

PREFERRED_PROVIDER_virtual/kernel = "linux-rockchip"
PREFERRED_VERSION_linux-rockchip = "5.10%"

SERIAL_CONSOLES = "1500000;ttyFIQ0"
KERNEL_IMAGETYPE = "Image"
KBUILD_DEFCONFIG = "rockchip_linux_defconfig"
KBUILD_DEFCONFIGS = "rk3588_axon.config"

PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-rockchip"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
   rkwifibt-firmware-rtl8852bs-bt \
   kernel-modules \
"
