SUMMARY = "Universal markup converter"
DESCRIPTION = "Pandoc is a Haskell library for converting from one markup format to another, and a command-line tool that uses this library."
HOMEPAGE = "https://pandoc.org"

# Use CLOSED license for pre-built binary without accessible license file
LICENSE = "CLOSED"

PV = "3.8.3"

# ARM64/AArch64 binary from GitHub releases
SRC_URI = "https://github.com/jgm/pandoc/releases/download/${PV}/pandoc-${PV}-linux-arm64.tar.gz"

SRC_URI[sha256sum] = "166a5a37387eb10bd4c4f242a8109beef755ac1e8d4eb039c6b5ebd1d918d8d7"

S = "${WORKDIR}/pandoc-${PV}"

# Pandoc is distributed as a pre-built binary
# No compilation needed
do_compile[noexec] = "1"
do_configure[noexec] = "1"

do_install() {
    install -d ${D}${bindir}
    install -d ${D}${datadir}/man/man1
    
    # Install the pandoc binary
    install -m 0755 ${S}/bin/pandoc ${D}${bindir}/
    
    # Install man page if it exists
    if [ -f ${S}/share/man/man1/pandoc.1.gz ]; then
        install -m 0644 ${S}/share/man/man1/pandoc.1.gz ${D}${datadir}/man/man1/
    fi
}

# Skip QA checks for pre-built binary
INSANE_SKIP:${PN} += "already-stripped ldflags file-rdeps build-deps"

FILES:${PN} = "${bindir}/pandoc"
FILES:${PN}-doc = "${datadir}/man/man1/pandoc.1.gz"

# Runtime dependencies
RDEPENDS:${PN} += "gmp zlib"

# Only compatible with ARM64/AArch64
COMPATIBLE_HOST = "aarch64.*-linux"
COMPATIBLE_MACHINE = ".*"

BBCLASSEXTEND = "native nativesdk"
