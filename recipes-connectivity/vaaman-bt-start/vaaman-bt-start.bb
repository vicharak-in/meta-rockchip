SUMMARY = "Realtek HCI attach script to restart bluetooth"
DESCRIPTION = "Realtek HCI attach script to restart bluetooth"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = " \
    file://vaaman-bt-start.service \
    file://vaaman-bt-start \
"

FILES_${PN} += " \
    ${systemd_system_unitdir}/vaaman-bt-start.service \
    ${sbindir}/vaaman-bt-start \
"

inherit systemd

RDEPENDS_${PN} += "rfkill"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/vaaman-bt-start.service ${D}${systemd_system_unitdir}
    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/vaaman-bt-start ${D}${sbindir}
}

SYSTEMD_SERVICE:${PN} = "vaaman-bt-start.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
