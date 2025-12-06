SUMMARY = "Install and start a rah service"
SECTION = "examples"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
inherit pkgconfig

BBCLASSEXTEND = "native"
SYSTEMD_AUTO_ENABLE = "enable"
DEPENDS = "sqlite3"
INSANE_SKIP:${PN} += "ldflags"

SRCREV = "${AUTOREV}"
SRC_URI = " \
	git://github.com/vicharak-in/recipe-vicharak.git;protocol=https;branch=main; \
"

S = "${WORKDIR}/git"
RDEPENDS:${PN} += "bash"
opt_prefix="/opt"

do_install() {
    install -d ${D}${bindir}
    install -d ${D}${includedir}/bitman/internal
    install -d ${D}${libdir}
    install -d ${D}${localstatedir}/lib/bitman
    install -d ${D}${opt_prefix}/bitman

    # Headers
    install -m 0644 ${S}/bitman/include/bitman/bitman.h \
        ${D}${includedir}/bitman/

    install -m 0644 ${S}/bitman/include/bitman/internal/* \
        ${D}${includedir}/bitman/internal/

    # Executable
    install -m 0755 ${S}/bitman/bin/bitman \
        ${D}${bindir}/

    # Shared library
    install -m 0755 ${S}/bitman/bin/libbitman.so.0.0.2 \
        ${D}${libdir}/

    # Runtime symlink
    ln -sf libbitman.so.0.0.2 ${D}${libdir}/libbitman.so.0

    # DEV symlink (THIS IS CRITICAL)
    ln -sf libbitman.so.0 ${D}${libdir}/libbitman.so

}

# Post-installation script that runs on target device
pkg_postinst_ontarget:${PN}() {
    #!/bin/sh
    bitman -v > /dev/null
    exit 0
}

pkg_postrm:${PN}() {
    if [ "$1" = "remove" ] || [ "$1" = "purge" ]; then
        rm -rf /var/lib/bitman
        rm -rf /opt/bitman
        ;;
    fi
}

FILES:${PN} = " ${libdir}/libbitman.so.0.* ${localstatedir}/lib/bitman ${bindir} ${opt_prefix} ${opt_prefix}/bitman ${includedir}/bitman ${libdir}"
