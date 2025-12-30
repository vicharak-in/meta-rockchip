SUMMARY = "Install and start a periplex service"
SECTION = "examples"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit pkgconfig systemd

BBCLASSEXTEND = "native"

SRCREV = "${AUTOREV}"
SRC_URI = " \
	git://github.com/vicharak-in/recipe-vicharak.git;protocol=https;branch=main; \
"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${sysconfdir}/X11
    install -d ${D}${sysconfdir}/X11/xorg.conf.d
    
    install -m 0644 ${S}/x11-vicharak/X11/xorg.conf.d/20-modesetting.conf ${D}${sysconfdir}/X11/xorg.conf.d
}

FILES:${PN} += "${sysconfdir}/X11/xorg.conf.d"
