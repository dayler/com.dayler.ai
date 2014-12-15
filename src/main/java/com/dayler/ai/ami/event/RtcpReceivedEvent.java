package com.dayler.ai.ami.event;

import com.dayler.common.util.DoubleUtil;
import com.dayler.common.util.LongUtil;

import java.util.Map;


public class RtcpReceivedEvent extends AmiEvent {

    public static final String RESPONSE_NAME = "RTCPReceived";

    public static final int PT_SENDER_REPORT = 200;
    public static final int PT_RECEIVER_REPORT = 201;
    public static final int PT_H261_FUR = 192;

    private String from;
    private Long pt;
    private Long receptionReports;
    private Long senderSsrc;
    private Long packetsLost;
    private Long highestSequence;
    private Long sequenceNumberCycles;
    private Double lastSr;
    private String dlsr;
    private String rtt;

    public RtcpReceivedEvent() {
        setName(RESPONSE_NAME);
    }

    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);
        setFrom(metadata.get(AmiResponseField.From.name()));
        setPt(LongUtil.tryParse(metadata.get(AmiResponseField.Pt.name())));
        setReceptionReports(LongUtil.tryParse(metadata.get(AmiResponseField.ReceptionReports.name())));
        setSenderSsrc(LongUtil.tryParse(metadata.get(AmiResponseField.SenderSsrc.name())));
        setPacketsLost(LongUtil.tryParse(metadata.get(AmiResponseField.PacketsLost.name())));
        setHighestSequence(LongUtil.tryParse(metadata.get(AmiResponseField.HighestSequence.name())));
        setSequenceNumberCycles(LongUtil.tryParse(metadata.get(AmiResponseField.SequenceNumberCycles.name())));
        setLastSr(DoubleUtil.tryParse(metadata.get(AmiResponseField.LastSr.name())));
        setDlsr(metadata.get(AmiResponseField.Dlsr.name()));
        setRtt(metadata.get(AmiResponseField.Rtt.name()));
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Long getPt() {
        return pt;
    }

    public void setPt(Long pt) {
        this.pt = pt;
    }

    public Long getReceptionReports() {
        return receptionReports;
    }

    public void setReceptionReports(Long receptionReports) {
        this.receptionReports = receptionReports;
    }

    public Long getSenderSsrc() {
        return senderSsrc;
    }

    public void setSenderSsrc(Long senderSsrc) {
        this.senderSsrc = senderSsrc;
    }

    public Long getPacketsLost() {
        return packetsLost;
    }

    public void setPacketsLost(Long packetsLost) {
        this.packetsLost = packetsLost;
    }

    public Long getHighestSequence() {
        return highestSequence;
    }

    public void setHighestSequence(Long highestSequence) {
        this.highestSequence = highestSequence;
    }

    public Long getSequenceNumberCycles() {
        return sequenceNumberCycles;
    }

    public void setSequenceNumberCycles(Long sequenceNumberCycles) {
        this.sequenceNumberCycles = sequenceNumberCycles;
    }

    public Double getLastSr() {
        return lastSr;
    }

    public void setLastSr(Double lastSr) {
        this.lastSr = lastSr;
    }

    public String getDlsr() {
        return dlsr;
    }

    public void setDlsr(String dlsr) {
        this.dlsr = dlsr;
    }

    public String getRtt() {
        return rtt;
    }

    public void setRtt(String rtt) {
        this.rtt = rtt;
    }

    @Override
    public String toString() {
        return toString(String.format("from=%s, pt=%s, receptionReports=%s, senderSsrc=%s, packetsLost=%s,"
                + " highestSequence=%s, sequenceNumberCycles=%s, lastSr=%s, dlsr=%s, rtt=%s",
                from,
                pt,
                receptionReports,
                senderSsrc,
                packetsLost,
                highestSequence,
                sequenceNumberCycles,
                lastSr,
                dlsr,
                rtt));
    }
}