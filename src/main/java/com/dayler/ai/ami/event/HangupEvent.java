package com.dayler.ai.ami.event;

import com.dayler.common.util.IntegerUtil;

import java.util.Map;

public class HangupEvent extends AmiEvent {

    public static final String RESPONSE_NAME = "Hangup";

    private static final String CAUSE_TXT = "Cause-txt";

    private String channel;

    private String callerIDName;

    private String callerIDNum;

    private String connectedLineNum;

    private String connectedLineName;

    private String accountCode;

    private Integer cause;

    private String causeTxt;

    public HangupEvent() {
        setName(RESPONSE_NAME);
    }

    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);
        setChannel(metadata.get(AmiResponseField.Channel.name()));
        setCallerIDName(metadata.get(AmiResponseField.CallerIDName.name()));
        setCallerIDNum(metadata.get(AmiResponseField.CallerIDNum.name()));
        setConnectedLineNum(metadata.get(AmiResponseField.ConnectedLineNum.name()));
        setConnectedLineName(metadata.get(AmiResponseField.ConnectedLineName.name()));
        setAccountCode(metadata.get(AmiResponseField.AccountCode.name()));
        setCause(IntegerUtil.tryParse(metadata.get(AmiResponseField.Cause.name())));
        setCauseTxt(metadata.get(CAUSE_TXT));
    }

    public Integer getCause() {
        return cause;
    }

    public void setCause(Integer cause) {
        this.cause = cause;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCallerIDName() {
        return callerIDName;
    }

    public void setCallerIDName(String callerIDName) {
        this.callerIDName = callerIDName;
    }

    public String getCallerIDNum() {
        return callerIDNum;
    }

    public void setCallerIDNum(String callerIDNum) {
        this.callerIDNum = callerIDNum;
    }

    public String getConnectedLineNum() {
        return connectedLineNum;
    }

    public void setConnectedLineNum(String connectedLineNum) {
        this.connectedLineNum = connectedLineNum;
    }

    public String getConnectedLineName() {
        return connectedLineName;
    }

    public void setConnectedLineName(String connectedLineName) {
        this.connectedLineName = connectedLineName;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getCauseTxt() {
        return causeTxt;
    }

    public void setCauseTxt(String causeTxt) {
        this.causeTxt = causeTxt;
    }

    @Override
    public String toString() {
        return toString(String.format("channel=%s, callerIDName=%s, callerIDNum=%s, connectedLineNum=%s, "
                + "connectedLineName=%s, accountCode=%s, cause=%s, causeTxt=%s",
                channel,
                callerIDName,
                callerIDNum,
                connectedLineNum,
                connectedLineName,
                accountCode,
                cause,
                causeTxt));
    }
}