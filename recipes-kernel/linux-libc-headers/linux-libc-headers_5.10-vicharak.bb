# Copyright (C) 2021, Rockchip Electronics Co., Ltd
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

inherit auto-patch

inherit local-git

SRCREV = "3982f6379937fa8f30687f36bf5fac473bc2b55a"
SRC_URI = " \
	git://github.com/vicharak-in/rockchip-linux-kernel;protocol=https;nobranch=1;branch=master; \
	file://${THISDIR}/files/rk3399_vaaman.cfg \
"

S = "${WORKDIR}/git"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
