# Copyright (C) 2020, Rockchip Electronics Co., Ltd
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

inherit auto-patch

inherit local-git

SRCREV = "0b91897440fd55f9b1fa1b3da2e8bb08e1871def"

SRC_URI = " \
	git://github.com/UtsavBalar1231/kernel_rockchip_linux;protocol=https;nobranch=1;branch=vaaman-4.19; \
	file://${THISDIR}/files/rk3399_vaaman.cfg \
"

S = "${WORKDIR}/git"

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
