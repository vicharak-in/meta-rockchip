FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Tell Yocto to use the in-tree kernel config as baseline
KERNEL_DEFCONFIG = "rockchip_linux_defconfig"

# Include your board-specific fragment from the files/ directory
SRC_URI += "file://${SOC_FAMILY}_${SOC_BOARD}.cfg"

# Point to the unpacked fragment location in WORKDIR
KERNEL_CONFIG_FRAGMENTS += "${WORKDIR}/${SOC_FAMILY}_${SOC_BOARD}.cfg"
