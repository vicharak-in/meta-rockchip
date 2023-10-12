# Copyright (c) 2023 Vicharak Computers LLP
# Released under the MIT license (see COPYING.MIT for the terms)

# Do not use compressed kernel images
ROCKCHIP_KERNEL_COMPRESSED := "0"

# Enable debian package management
PACKAGE_CLASSES = "package_deb"
EXTRA_IMAGE_FEATURES += "package-management"

# Enable gnupg support
IMAGE_INSTALL:append = "gnupg"

# Install Vicharak overlay directory
VICHARAK_OVERLAY_DIRS:append = " \
	${TOPDIR}/vicharak-overlay/overlay \
"

BB_BASEHASH_IGNORE_VARS:append = " VICHARAK_OVERLAY_DIRS"
ROOTFS_POSTPROCESS_COMMAND:append = " do_vicharak_post_rootfs;"
do_rootfs[depends] += "rsync-native:do_populate_sysroot"
do_vicharak_post_rootfs() {
	for overlay in ${VICHARAK_OVERLAY_DIRS};do
		[ -d "${overlay}" ] || continue
		echo "Installing overlay: ${overlay}..."
		rsync -av --chmod=u=rwX,go=rX "${overlay}/" "${IMAGE_ROOTFS}"
	done
}

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
    alsa-utils \
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
	packagegroup-tools-bluetooth \
	pm-utils \
	python3 \
	python3-pip \
"

# Enable systemd support
INIT_MANAGER = "systemd"
DISTRO_FEATURES:append = " systemd pam "
DISTRO_FEATURES_BACKFILL_CONSIDERED:append = " sysvinit"
VIRTUAL-RUNTIME_init_manager = "systemd"
VIRTUAL-RUNTIME_initscripts = "systemd-compat-units"
VIRTUAL-RUNTIME_login_manager = "shadow-base"
VIRTUAL-RUNTIME_dev_manager = "systemd"

# Enable bluetooth and wifi
DISTRO_FEATURES:append = "bluetooth wifi"
