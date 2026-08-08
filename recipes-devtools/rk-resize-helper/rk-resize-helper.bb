SUMMARY = "Resize all internal mounted partitions"
DESCRIPTION = "Resize all internal mounted partitions"
SECTION = "devel"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""
RDEPENDS:rk-resize-helper = " \
    bash \
    e2fsprogs-resize2fs \
    gptfdisk \
    parted \
    udev \
    util-linux \
"

FILES_${PN} += " \
    ${systemd_system_unitdir}/rk-resize-helper.service \
    ${sbindir}/rk-resize-helper \
"

SRC_URI = " \
    file://rk-resize-helper.service \
    file://rk-resize-helper \
"

inherit systemd

RDEPENDS_${PN} += "e2fsprogs-resize2fs gptfdisk parted util-linux udev"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/rk-resize-helper.service ${D}${systemd_system_unitdir}
    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/rk-resize-helper ${D}${sbindir}
}

SYSTEMD_SERVICE:${PN} = "rk-resize-helper.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
