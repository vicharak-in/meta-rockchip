# Copyright (C) 2019, Fuzhou Rockchip Electronics Co., Ltd

SUMMARY = "Rockchip WIFI/BT firmware files"
SECTION = "kernel"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=919c961282a1817c7f9a6bf495fa7b2e"

SRCREV = "4c7ba27cf3306fd6531e1cef3146adfd383b0cba"
SRC_URI = "git://github.com/radxa/rkwifibt.git;protocol=https;branch=develop"

S = "${WORKDIR}/git"

inherit allarch deploy
RPROVIDES_${PN}-scripts += "rkwifibit-firmware-rtl8852bs-bt"

do_install() {
    install -d ${D}/lib/firmware/rtlbt/
   	install -m 0644 ${S}/firmware/realtek/RTL8852BS/* -t ${D}/lib/firmware/rtlbt/
    cp -u $(find ${S}/firmware/ -type f) ${D}/lib/firmware/
#    ln -rsf ${D}/lib/firmware/*rtl*_* ${D}/lib/firmware/rtlbt/
}

PACKAGES =+ " \
	${PN}-ap6212a1-wifi \
	${PN}-ap6212a1-bt \
	${PN}-ap6236-wifi \
	${PN}-ap6236-bt \
	${PN}-ap6255-wifi \
	${PN}-ap6255-bt \
	${PN}-ap6256-wifi \
	${PN}-ap6256-bt \
	${PN}-ap6356-wifi \
	${PN}-ap6356-bt \
	${PN}-ap6398s-wifi \
	${PN}-ap6398s-bt \
	${PN}-rtl8723ds-bt \
	${PN}-rtl8723du-bt \
	${PN}-rtl8821cu-bt \
	${PN}-rtl8852bs-bt \
"

FILES:${PN}-ap6212a1-wifi = " \
	lib/firmware/brcm/brcmfmac43430-sdio* \
"

FILES:${PN}-ap6212a1-bt = " \
	lib/firmware/brcm/bcm43438a1.hcd \
"

FILES:${PN}-ap6236-wifi = " \
	lib/firmware/brcm/fw_bcm43436b0.bin \
	lib/firmware/brcm/nvram_ap6236.txt \
"

FILES:${PN}-ap6236-bt = " \
	lib/firmware/brcm/BCM4343B0.hcd \
"

FILES:${PN}-ap6255-wifi = " \
	lib/firmware/brcm/fw_bcm43455c0_ag.bin \
	lib/firmware/brcm/fw_bcm43455c0_ag_p2p.bin \
	lib/firmware/brcm/nvram_ap6255.txt \
"

FILES:${PN}-ap6255-bt = " \
	lib/firmware/brcm/BCM4345C0.hcd \
"

FILES:${PN}-ap6256-wifi = " \
	lib/firmware/brcm/brcmfmac43456-sdio* \
"

FILES:${PN}-ap6256-bt = " \
	lib/firmware/brcm/BCM4345C5.hcd \
"

FILES:${PN}-ap6356-wifi = " \
	lib/firmware/brcm/fw_bcm4356a2_ag.bin \
	lib/firmware/brcm/nvram_ap6356.txt \
"

FILES:${PN}-ap6356-bt = " \
	lib/firmware/brcm/BCM4356A2.hcd \
"

FILES:${PN}-ap6398s-wifi = " \
	lib/firmware/brcm/fw_bcm4359c0_ag.bin \
	lib/firmware/brcm/fw_bcm4359c0_ag_p2p.bin \
	lib/firmware/brcm/nvram_ap6398s.txt \
"

FILES:${PN}-ap6398s-bt = " \
	lib/firmware/brcm/BCM4359C0.hcd \
"

FILES:${PN}-rtl8723ds-bt = " \
	${base_libdir}/firmware/rtlbt/rtl8723d_config \
	${base_libdir}/firmware/rtlbt/rtl8723d_fw \
"

FILES:${PN}-rtl8723du-bt = " \
	${base_libdir}/firmware/rtl8723du_config \
	${base_libdir}/firmware/rtl8723du_fw \
"

FILES:${PN}-rtl8821cu-bt = " \
	${base_libdir}/firmware/rtl8821cu_config \
	${base_libdir}/firmware/rtl8821cu_fw \
"

FILES:${PN}-rtl8852bs-bt = " \
	${base_libdir}/firmware/rtlbt/rtl8852bs_config \
	${base_libdir}/firmware/rtlbt/rtl8852bs_fw \
"

FILES:${PN}-rtl8822cs-bt = " \
	lib/firmware/rtlbt/rtl8822cs_config \
	lib/firmware/rtlbt/rtl8822cs_fw \
"

FILES:${PN} = "*"

# Make it depend on all of the split-out packages.
python () {
    pn = d.getVar('PN')
    firmware_pkgs = oe.utils.packages_filter_out_system(d)
    d.appendVar('RDEPENDS_' + pn, ' ' + ' '.join(firmware_pkgs))
}

INSANE_SKIP_${PN} += "arch"
