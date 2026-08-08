SUMMARY = "Vicharak APT repository sources list"
DESCRIPTION = "Installs Vicharak APT repository configuration"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r1"
SRC_URI = " \
    file://vicharak.list \
    file://vicharak.gpg \
"

inherit allarch 

S = "${WORKDIR}"

RDEPENDS:${PN} += "bash"

do_install() {
    install -d ${D}${sysconfdir}/apt/sources.list.d/
    install -d ${D}${sysconfdir}/apt/trusted.gpg.d/

    sed \
        -e "s|@YOCTO_VERSION@|${DISTRO_CODENAME}|g" \ 
        -e "s|@SOC@|${SOC}|g" \  
        -e "s|@SOC_FAMILY@|${SOC_FAMILY}|g" \ 
        -e "s|@SOC_BOARD@|${SOC_BOARD}|g" \   
        ${WORKDIR}/vicharak.list \
        > ${D}${sysconfdir}/apt/sources.list.d/vicharak.list

    install -m 0644 ${WORKDIR}/vicharak.gpg \
        ${D}${sysconfdir}/apt/trusted.gpg.d/vicharak.gpg
}

do_populate_sysroot[noexec] = "1"
