SUMMARY = "Static u-boot-menu configuration for RK3399"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV = "${AUTOREV}"
SRC_URI = " \
	git://github.com/vicharak-in/recipe-vicharak.git;protocol=https;branch=main; \
"

S = "${WORKDIR}/git"
RDEPENDS:${PN} += "bash"

do_install() {
    install -d ${D}${bindir}
    install -d ${D}${sysconfdir}
    install -d ${D}${sysconfdir}/default
    install -d ${D}${sysconfdir}/default/u-boot
    install -d ${D}${sysconfdir}/u-boot-menu
    install -d ${D}${sysconfdir}/u-boot-menu/conf.d
  
    install -m 0755 ${S}/u-boot-update/u-boot-update ${D}${bindir}/
}

FILES:${PN} += "${sysconfdir}/default/u-boot \
                 ${sysconfdir}/u-boot-menu/conf.d \
		 ${bindir}/u-boot-menu"

