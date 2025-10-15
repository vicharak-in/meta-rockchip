# Copyright (C) 2021, Rockchip Electronics Co., Ltd
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-yocto.inc
require linux-rockchip.inc

inherit local-git

#SRCREV = "938df9cf0910f8f225d85f06b52be7f615857aa8"
SRCREV = "8685847a83d879cf66ec099c567233df3c832cac"
SRC_URI = " \
	git://github.com/vicharak-in/vicharak-linux-kernel.git;protocol=https;branch=master; \
	file://${THISDIR}/files/cgroups.cfg \
	file://${THISDIR}/files/rk3588_axon.cfg \
	file://${THISDIR}/files/ext4.cfg \
    file://${THISDIR}/files/0001-makefile-add-rockchip-specific-include-paths.patch \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

KERNEL_VERSION_SANITY_SKIP = "1"
LINUX_VERSION ?= "5.10"

SRC_URI:append = " ${@bb.utils.contains('IMAGE_FSTYPES', 'ext4', \
		   'file://${THISDIR}/files/ext4.cfg', \
		   '', \
		   d)}"

do_patch:append() {
	sed -i 's/-I\($(BCMDHD_ROOT)\)/-I$(srctree)\/\1/g' \
		${S}/drivers/net/wireless/rockchip_wlan/rkwifi/bcmdhd/Makefile
}
