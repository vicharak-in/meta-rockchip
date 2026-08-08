SUMMARY = "Vicharak configuration utility"
DESCRIPTION = "Configuration management tool for Vicharak boards"
HOMEPAGE = "https://github.com/vicharak-in/vicharak-config"
LICENSE = "CLOSED"


# Update this with your actual git repository
PV = "1.2.0"
SRC_URI = "git://github.com/vicharak-in/vicharak-config.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

# Dependencies
DEPENDS += "vicharak-user" 
RDEPENDS:${PN} += "vicharak-user bash ncurses-terminfo ncurses-terminfo-base"

inherit pkgconfig systemd

do_compile() {
    # Set variables for the Makefile
    export PROJECT="vicharak-config"
    export PREFIX="${prefix}"
    export BINDIR="${bindir}"
    export LIBDIR="${libdir}"
    export MANDIR="${mandir}"
    
    # Build documentation only (skip man pages if pandoc not available)
    oe_runmake build-doc || bbwarn "Failed to build documentation"
    
    # Try building man pages if pandoc is available
    if which pandoc > /dev/null 2>&1; then
        oe_runmake build-man || bbwarn "Failed to build man pages"
    else
        bbwarn "pandoc not found, skipping man page generation"
    fi
}

do_install() {
    # Set variables for the Makefile
    export PROJECT="vicharak-config"
    export PREFIX="${prefix}"
    export BINDIR="${bindir}"
    export LIBDIR="${libdir}"
    export MANDIR="${mandir}"
    export DESTDIR="${D}"
    
    # Create necessary directories
    install -d ${D}${bindir}
    install -d ${D}${libdir}
    #install -d ${D}${mandir}/man8
    install -d ${D}${datadir}/${PN}
    
    # Install the main executable
    if [ -f ${S}/src/usr/bin/vicharak-config ]; then
        install -m 0755 ${S}/src/usr/bin/vicharak-config ${D}${bindir}/
    fi
    
    # Install scripts and libraries
    if [ -d ${S}/src/usr/lib ]; then
        cp -r ${S}/src/usr/lib/* ${D}${libdir}/ || true
    fi
    
    # Install SOURCE file
    if [ -f ${S}/src/SOURCE ]; then
        install -m 0644 ${S}/src/SOURCE ${D}${datadir}/${PN}/
    fi
    
    # Install any additional files from src directory
    if [ -d ${S}/src/usr/share/${PN} ]; then
        cp -r ${S}/src/usr/share/${PN}/* ${D}${datadir}/${PN}/ || true
    fi
}

FILES:${PN} = "${bindir}/* \
               ${libdir}/* \
               ${datadir}/${PN}/* \
"

FILES:${PN}-doc = "${mandir}/man8/*"

BBCLASSEXTEND = "native nativesdk"
