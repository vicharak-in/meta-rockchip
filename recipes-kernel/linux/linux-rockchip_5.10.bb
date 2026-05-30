# Copyright (C) 2021, Rockchip Electronics Co., Ltd
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-yocto.inc
require linux-rockchip.inc

inherit local-git

SRCREV = "60704737f1be3e10d03c6194d53eb5fc2c0d4dcf"

SRC_URI = " \
	git://github.com/vicharak-in/vicharak-linux-kernel.git;protocol=https;branch=master \
	file://${THISDIR}/files/cgroups.cfg \
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
