# Copyright (C) 2021, Rockchip Electronics Co., Ltd
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

inherit auto-patch

inherit local-git

SRCREV = "9b2323f8b2c6ed50b1cd1fbfc97ffa42d6298ff7"
SRC_URI = " \
	git://github.com/vicharak-in/rockchip-linux-kernel;protocol=https;nobranch=1;branch=master; \
	file://${THISDIR}/files/rk3399_vaaman.cfg \
"

S = "${WORKDIR}/git"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
