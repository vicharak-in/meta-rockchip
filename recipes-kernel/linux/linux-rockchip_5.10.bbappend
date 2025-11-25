FILESEXTRAPATHS:prepend := "${THISDIR}:"

KERNEL_DEFCONFIG := "rockchip_linux_defconfig"

SRC_URI += "file://${SOC_FAMILY}_${SOC_BOARD}.cfg"

KERNEL_CONFIG_FRAGMENTS += "${SOC_FAMILY}_${SOC_BOARD}.cfg"
