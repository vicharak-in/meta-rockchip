SUMMARY = "Install and start a periplex service"
SECTION = "examples"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit pkgconfig systemd

DEPENDS += "libxml2 cjson sqlite3 curl libwebsockets json-c openssl rah bitman util-linux icu"
RPROVIDES:${PN} += "librah.so()(64bit)"

SRCREV = "${AUTOREV}"
SRC_URI = " \
	git://github.com/vicharak-in/recipe-vicharak.git;protocol=https;branch=main; \
"

S = "${WORKDIR}/git"
#DEVICE_JSON = "${S}/device.json"

RDEPENDS:${PN} += "bash curl libwebsockets rah bitman "
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
opt_prefix="/opt"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${bindir}
    install -d ${D}${prefix}/local
    install -d ${D}${prefix}/local/bin
    install -d ${D}${prefix}/local/lib
    install -d ${D}${opt_prefix}
    install -d ${D}${opt_prefix}/periplex

    install -m 0755 ${S}/periplex/bin/periplex-sync ${D}${bindir}/
    install -m 0755 ${S}/periplex/bin/periplex_parser ${D}${bindir}/
    install -m 0755 ${S}/periplex/bin/periplexer ${D}${bindir}/
    install -m 0755 ${S}/periplex/opt/periplex/queue ${D}${opt_prefix}/periplex/

    # systemd service
    install -m 0644 ${S}/periplex/periplexer.service ${D}${systemd_system_unitdir}/
}

FILES:${PN} += "${bindir}/ ${prefix} ${opt_prefix} ${opt_prefix}/periplex"
FILES:${PN} += "${systemd_system_unitdir}/"
SYSTEMD_SERVICE:${PN} = "periplexer.service"

REQUIRED_DISTRO_FEATURES = "systemd"
