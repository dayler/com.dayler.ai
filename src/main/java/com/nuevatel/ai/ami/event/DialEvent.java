package com.nuevatel.ai.ami.event;

import java.util.Map;

import com.nuevatel.common.util.StringUtils;


public class DialEvent extends AmiEvent {

    public static final String REPONSE_NAME = "Dial";

    private String subEvent;
    private String channel;
    private String destination;
    private String callerIdNum;
    private String callerIdName;
    private String connectedLineNum;
    private String connectedLineName;
    private String destUniqueId;
    private String dialstring;
    private DialStatus dialStatus = DialStatus.NONE;

    public DialEvent() {
        setName(REPONSE_NAME);
    }

    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);
        // Fix misspelling in Dial event on Asterisk AMI response.
        if (StringUtils.isEmptyOrNull(getUniqueId())) {
            setUniqueId(metadata.get(AmiResponseField.UniqueID.name()));
        }

        setSubEvent(metadata.get(AmiResponseField.SubEvent.name()));
        setChannel(metadata.get(AmiResponseField.Channel.name()));
        setDestination(metadata.get(AmiResponseField.Destination.name()));
        setCallerIdNum(metadata.get(AmiResponseField.CallerIDNum.name()));
        setCallerIdName(metadata.get(AmiResponseField.CallerIDName.name()));
        setConnectedLineNum(metadata.get(AmiResponseField.ConnectedLineNum.name()));
        setConnectedLineName(metadata.get(AmiResponseField.ConnectedLineName.name()));
        setDestUniqueId(metadata.get(AmiResponseField.DestUniqueID.name()));
        setDialstring(metadata.get(AmiResponseField.Dialstring.name()));
        setDialStatus(metadata.get(AmiResponseField.DialStatus.name()));
    }

    public String getSubEvent() {
        return subEvent;
    }

    public void setSubEvent(String subEvent) {
        this.subEvent = subEvent;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCallerIdNum() {
        return callerIdNum;
    }

    public void setCallerIdNum(String callerIdNum) {
        this.callerIdNum = callerIdNum;
    }

    public String getCallerIdName() {
        return callerIdName;
    }

    public void setCallerIdName(String callerIdName) {
        this.callerIdName = callerIdName;
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

    public String getDestUniqueId() {
        return destUniqueId;
    }

    public void setDestUniqueId(String destUniqueId) {
        this.destUniqueId = destUniqueId;
    }

    public String getDialstring() {
        return dialstring;
    }

    public void setDialstring(String dialstring) {
        this.dialstring = dialstring;
    }

    public DialStatus getDialStatus() {
        return dialStatus;
    }

    public void setDialStatus(String dialStatus) {
        this.dialStatus = DialStatus.fromName(dialStatus);
    }

    @Override
    public String toString() {
        return toString( "name=" + getName() + '\'' +
                ", subEvent='" + subEvent + '\'' +
                ", channel='" + channel + '\'' +
                ", destination='" + destination + '\'' +
                ", callerIdNum='" + callerIdNum + '\'' +
                ", callerIdName='" + callerIdName + '\'' +
                ", connectedLineNum='" + connectedLineNum + '\'' +
                ", connectedLineName='" + connectedLineName + '\'' +
                ", uniqueId='" + getUniqueId() + '\'' +
                ", destUniqueId='" + destUniqueId + '\'' +
                ", dialString='" + getDialstring() + '\'' +
                ", dialStatus='" + dialStatus + '\'');
    }

    public enum DialStatus {
        CHANUNAVAIL,
        CONGESTION,
        NOANSWER,
        BUSY,
        ANSWER,
        CANCEL,
        DONTCALL,
        TORTURE,
        INVALIDARGS,
        NONE,
        ;

        /**
         * 
         * @param name Name of enum.
         * 
         * @return DialStatus to match with its name. Safe way, it returns NONE for blank string.
         */
        public static DialStatus fromName(String name) {
            if (StringUtils.isBlank(name)) {
                return NONE;
            }

            return valueOf(name);
        }
    }
}