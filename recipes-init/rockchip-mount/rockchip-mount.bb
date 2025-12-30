inherit systemd

SUMMARY = "Install and start a mount service"
SECTION = "examples"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://mountboot.sh"
SRC_URI += "file://rockchip-mount.service"

S = "${WORKDIR}"
SYSTEMD_AUTO_ENABLE = "enable"

RDEPENDS:${PN} += "bash"
SYSTEMD_SERVICE:${PN} = "rockchip-mount.service"

do_install() {
    # Create directories
    install -d ${D}${sysconfdir}
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${sysconfdir}/rc4.d

    # Install script
    install -m 0755 ${WORKDIR}/mountboot.sh ${D}${sysconfdir}/mountboot.sh

    # Install systemd service
    install -m 0644 ${WORKDIR}/rockchip-mount.service ${D}${systemd_system_unitdir}
}

FILES:${PN} += "${sysconfdir}/"
FILES:${PN} += "${systemd_system_unitdir}/"

REQUIRED_DISTRO_FEATURES = "systemd"
