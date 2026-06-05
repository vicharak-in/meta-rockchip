SUMMARY = "Install and start a rah service"
SECTION = "examples"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
inherit pkgconfig systemd

BBCLASSEXTEND = "native"
SYSTEMD_AUTO_ENABLE = "enable"

SRCREV = "${AUTOREV}"
SRC_URI = " \
	git://github.com/vicharak-in/recipe-vicharak.git;protocol=https;branch=main; \
"

S = "${WORKDIR}/git"
SYSTEMD_AUTO_ENABLE = "enable"

DEPENDS += "libdrm"
RDEPENDS:${PN} += "libdrm"
RDEPENDS:${PN} += "bash"
SYSTEMD_SERVICE:${PN} = "rah.service"


do_install() {
    install -d ${D}${sysconfdir}/rah
    install -d ${D}${includedir}/uapi/
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${bindir}
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    install -d ${D}${libdir}/firmware

    install -m 0644 ${S}/rah/etc/rah/queue ${D}/${sysconfdir}/rah

    # Header
    install -m 0644 ${S}/rah/include/uapi/rah.h ${D}${includedir}/

    # Runtime binary
    install -m 0755 ${S}/rah/bin/rah_service ${D}${bindir}/

    # Shared library
    install -m 0755 ${S}/rah/bin/librah.so ${D}${libdir}/librah.so.1.0.0

    # Flasher
    install -m 0755 ${S}/rah/bin/rah_fw_flasher ${D}${bindir}

    # Firmware
    install -m 07555 ${S}/rah/firmware/rah_firmware.bin ${D}${libdir}/firmware/

    ln -sf librah.so.1.0.0 ${D}${libdir}/librah.so.1
    ln -sf librah.so.1 ${D}${libdir}/librah.so

    # systemd service
    install -m 0644 ${S}/rah/rah.service ${D}${systemd_system_unitdir}/rah.service
}

pkg_postinst_ontarget:${PN}() {
    #!/bin/sh
    set -e
    resolution=$(cat /sys/class/drm/card0-eDP-1/modes | head -n 1)

    if [[ ${resolution} != *"320x240"* ]]; then
	rah_fw_flasher /usr/lib/firmware/rah_firmware.bin
	echo "Powering off the device and unplugging the adapter is required to apply the firmware update."
    fi
}

FILES:${PN} += "${bindir}/* ${libdir}/*.so* ${includedir}/*"
FILES:${PN} += "${systemd_system_unitdir}/*.service ${sysconfdir}/rah/* ${base_libdir}/firmware/*"
FILES:${PN}-dev += "${libdir}/librah.so ${libdir}/firmware/rah_firmware.bin"

REQUIRED_DISTRO_FEATURES = "systemd"
