SUMMARY = "Romulus VRM Overrides"
DESCRIPTION = "Sets Rolumus VRMs to custom voltages"
PR = "r1"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${IBMBASE}/COPYING.apache-2.0;md5=34400b68072d710fecd0a2940a0d1658"

inherit obmc-phosphor-systemd

RDEPENDS_${PN} += "i2c-tools bash"

S = "${WORKDIR}"
SRC_URI += "file://vrm-control.sh \
            file://vrm.sh"

do_install() {
        install -d ${D}${bindir}
        install -m 0755 ${WORKDIR}/vrm.sh ${D}${bindir}/vrm.sh
        install -m 0755 ${WORKDIR}/vrm-control.sh ${D}${bindir}/vrm-control.sh
}

TMPL = "vrm-control@.service"
INSTFMT = "vrm-control@{0}.service"
TGTFMT = "obmc-chassis-poweron@{0}.target"
FMT = "../${TMPL}:${TGTFMT}.requires/${INSTFMT}"

SYSTEMD_SERVICE_${PN} += "${TMPL}"
SYSTEMD_LINK_${PN} += "${@compose_list(d, 'FMT', 'OBMC_CHASSIS_INSTANCES')}"
