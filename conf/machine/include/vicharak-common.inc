# Copyright (c) 2023 Vicharak Computers LLP
# Released under the MIT license (see COPYING.MIT for the terms)

# Do not use compressed kernel images
ROCKCHIP_KERNEL_COMPRESSED := "0"

# Enable debian package management
PACKAGE_CLASSES = "package_deb"
EXTRA_IMAGE_FEATURES += "package-management"

# Enable gnupg support
IMAGE_INSTALL:append = "gnupg"

MACHINE_EXTRA_RRECOMMENDS:append = " \
	drm-cursor \
	exfat-utils \
	fuse-exfat \
	gptfdisk \
	ntfs-3g \
	ntp \
	parted \
	pciutils \
	rk-resize-helper \
	usbutils \
	vim \
"

IMAGE_INSTALL:append = " \
	bash-completion \
	curl \
	ethtool \
	evtest \
	git \
	glibc-gconv-utf-16 \
	i2c-tools \
	iperf3 \
	libgpiod \
	networkmanager \
	networkmanager-nmcli \
	networkmanager-nmtui \
	ntpdate \
	packagegroup-tools-bluetooth \
	pm-utils \
	python3 \
	python3-pip \
"
