SUMMARY = "Vicharak system setup utility "
DESCRIPTION = "Vicharak system setup utility "
SECTION = "devel"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

RDEPENDS:${PN} = " \
    dtc \
	gptfdisk \
    ncurses \
    whiptail \
"

inherit local-git
inherit logging

SRC_URI = " \
	git://github.com/vicharak-in/vicharak-config.git;protocol=https;branch=main; \
    file://${THISDIR}/files/0001-vicharak-config-Remove-makefile-for-yocto.patch; \
"
SRCREV = "46013d96487539d5debad94b41f0d539fc61b914"
S = "${WORKDIR}/git"

do_install() {
    # Install systemd service
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/debian/service ${D}${systemd_system_unitdir}/${PN}.service

    # Install vicharak-config binary to /usr/bin
    install -d ${D}${bindir}
    install -m 0755 ${S}/src/usr/bin/vicharak-config ${D}${bindir}/vicharak-config

    # Install vicharak-config libraries to /usr/lib
    install -d ${D}${libdir}/vicharak-config
    cp -r ${S}/src/usr/lib/vicharak-config/* ${D}${libdir}/vicharak-config/

    # Install kernel postinst and postrm scripts
    install -d ${D}${sysconfdir}/kernel/postinst.d
    install -m 0755 ${S}/src/etc/kernel/postinst.d/yz-update-overlays ${D}${sysconfdir}/kernel/postinst.d/
    install -d ${D}${sysconfdir}/kernel/postrm.d
    install -m 0755 ${S}/src/etc/kernel/postrm.d/yz-update-overlays ${D}${sysconfdir}/kernel/postrm.d/
}

inherit systemd

SYSTEMD_SERVICE:${PN} = "${PN}.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
