package com.dayler.ai.ami.event;

import com.dayler.common.util.DoubleUtil;
import com.dayler.common.util.LongUtil;

import java.util.Map;


public class RtcpSentEvent extends AmiEvent {

    public static final String RESPONSE_NAME = "RTCPSent";

    private String to;
    private Long ourSsrc;
    private Double sentNtp;
    private Long sentRtp;
    private Long sentPackets;
    private Long sentOctets;
    private String reportBlock;
    private String fractionLost;
    private Long cumulativeLoss;
    private Long iaJitter;
    private Long theirLastSr;
    private String dlsr;

    public RtcpSentEvent() {
        setName(RESPONSE_NAME);
    }


    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);
        setTo(metadata.get(AmiResponseField.To.name()));
        setOurSsrc(LongUtil.tryParse(metadata.get(AmiResponseField.OurSsrc.name())));
        setSentNtp(DoubleUtil.tryParse(metadata.get(AmiResponseField.SentNtp.name())));
        setSentRtp(LongUtil.tryParse(metadata.get(AmiResponseField.SentRtp.name())));
        setSentPackets(LongUtil.tryParse(metadata.get(AmiResponseField.SentPackets.name())));
        setSentOctets(LongUtil.tryParse(metadata.get(AmiResponseField.SentOctets.name())));
        setReportBlock(metadata.get(AmiResponseField.ReportBlock.name()));
        setFractionLost(metadata.get(AmiResponseField.FractionLost.name()));
        setCumulativeLoss(LongUtil.tryParse(metadata.get(AmiResponseField.CumulativeLoss.name())));
        setIaJitter(LongUtil.tryParse(metadata.get(AmiResponseField.IaJitter.name())));
        setTheirLastSr(LongUtil.tryParse(metadata.get(AmiResponseField.TheirLastSr.name())));
        setDlsr(metadata.get(AmiResponseField.Dlsr.name()));
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Long getOurSsrc() {
        return ourSsrc;
    }

    public void setOurSsrc(Long ourSsrc) {
        this.ourSsrc = ourSsrc;
    }

    public Double getSentNtp() {
        return sentNtp;
    }

    public void setSentNtp(Double sentNtp) {
        this.sentNtp = sentNtp;
    }

    public Long getSentRtp() {
        return sentRtp;
    }

    public void setSentRtp(Long sentRtp) {
        this.sentRtp = sentRtp;
    }

    public Long getSentPackets() {
        return sentPackets;
    }

    public void setSentPackets(Long sentPackets) {
        this.sentPackets = sentPackets;
    }

    public Long getSentOctets() {
        return sentOctets;
    }

    public void setSentOctets(Long sentOctets) {
        this.sentOctets = sentOctets;
    }

    public String getReportBlock() {
        return reportBlock;
    }

    public void setReportBlock(String reportBlock) {
        this.reportBlock = reportBlock;
    }

    public String getFractionLost() {
        return fractionLost;
    }

    public void setFractionLost(String fractionLost) {
        this.fractionLost = fractionLost;
    }

    public Long getCumulativeLoss() {
        return cumulativeLoss;
    }

    public void setCumulativeLoss(Long cumulativeLoss) {
        this.cumulativeLoss = cumulativeLoss;
    }

    public Long getIaJitter() {
        return iaJitter;
    }

    public void setIaJitter(Long iaJitter) {
        this.iaJitter = iaJitter;
    }

    public Long getTheirLastSr() {
        return theirLastSr;
    }

    public void setTheirLastSr(Long theirLastSr) {
        this.theirLastSr = theirLastSr;
    }

    public String getDlsr() {
        return dlsr;
    }

    public void setDlsr(String dlsr) {
        this.dlsr = dlsr;
    }

    @Override
    public String toString() {
        return toString(String.format("to=%s, ourSsrc=%s, sentNtp=%s, sentRtp=%s, sentPackets=%s, "
                + "sentOctets=%s, reportBlock=%s, fractionLost=%s, cumulativeLoss=%s, iaJitter=%s,"
                + " theirLastSr=%s, dlsr=%s",
                to,
                ourSsrc,
                sentNtp,
                sentRtp,
                sentPackets,
                sentOctets,
                reportBlock,
                fractionLost,
                cumulativeLoss,
                iaJitter,
                theirLastSr,
                dlsr));
    }
}