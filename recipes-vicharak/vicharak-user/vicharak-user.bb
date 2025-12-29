SUMMARY = "Install and start a vicharak-user "
SECTION = "examples"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit pkgconfig systemd

BBCLASSEXTEND = "native"

DEPENDS += "curl"

SRCREV = "${AUTOREV}"
SRC_URI = " \
        git://github.com/vicharak-in/recipe-vicharak.git;protocol=https;branch=main; \
"

S = "${WORKDIR}/git"

RDEPENDS:${PN} += "bash curl"

do_install() {
    install -d ${D}${prefix}/local/bin
    install -d ${D}${localstatedir}/lib/
    install -d ${D}${localstatedir}/lib/vicharak
    install -d ${D}${prefix}/lib/
    install -d ${D}${prefix}/lib/vicharak-config
    install -d ${D}${prefix}/lib/vicharak-config/user
    install -d ${D}${prefix}/local
    install -d ${D}${prefix}/local/bin


    install -m 0755 ${S}/vicharak-user/bin/identity ${D}${prefix}/local/bin/
    install -m 0755 ${S}/vicharak-user/bin/trustbase ${D}${prefix}/lib/vicharak-config/user
    install -m 0755 ${S}/vicharak-user/assets/server_pub.pem ${D}${prefix}/lib/vicharak-config/user
}


# Post-installation script that runs on target device
pkg_postinst_ontarget:${PN}() {
#!/bin/sh

if [ -x /usr/local/bin/identity ]; then
    /usr/local/bin/identity 
    status=$?
    if [ "$status" -ne 0 ]; then
        echo "ERROR: Identity initialization failed with status $status"
        rm -f /usr/local/bin/identity
        exit 1
    fi
    # Remove identity binary after successful execution
    rm -f /usr/local/bin/identity
    echo "Identity initialization completed successfully"
fi
exit 0
}

pkg_postrm:${PN}() {
    if [ "$1" = "remove" ] || [ "$1" = "purge" ]; then
        rm -rf /usr/lib/vicharak-config/user || true
    fi
}

FILES:${PN} += "${bindir}/ ${prefix}"
FILES:${PN} += "${systemd_system_unitdir}/"

REQUIRED_DISTRO_FEATURES = "systemd"
