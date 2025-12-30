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

    install -m 0644 ${S}/rah/etc/rah/queue ${D}/${sysconfdir}/rah

    # Header
    install -m 0644 ${S}/rah/include/uapi/rah.h ${D}${includedir}/

    # Runtime binary
    install -m 0755 ${S}/rah/bin/rah_service ${D}${bindir}/

    # Shared library
    install -m 0755 ${S}/rah/bin/librah.so ${D}${libdir}/librah.so.1.0.0

    ln -sf librah.so.1.0.0 ${D}${libdir}/librah.so.1
    ln -sf librah.so.1 ${D}${libdir}/librah.so

    # systemd service
    install -m 0644 ${S}/rah/rah.service ${D}${systemd_system_unitdir}/rah.service
}


FILES:${PN} += "${bindir}/* ${libdir}/*.so* ${includedir}/*"
FILES:${PN} += "${systemd_system_unitdir}/*.service ${sysconfdir}/rah/*"
FILES:${PN}-dev += "${libdir}/librah.so"

REQUIRED_DISTRO_FEATURES = "systemd"
