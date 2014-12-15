package com.nuevatel.ai.ami.event;

import java.util.Map;

public class VarSetEvent extends AmiEvent {

    public static final String RESPONSE_NAME = "VarSet";
    public static final String DIAL_STATUS = "DIALSTATUS";
    public static final String DIAL_STATUS_ANSWER = "ANSWER";
    public static final String DIAL_STATUS_BUSY = "BUSY";
    public static final String DIAL_STATUS_NO_ANSWER = "NOANSWER";
    public static final String DIAL_STATUS_CHANUNAVAIL = "CHANUNAVAIL";
    public static final String APPCONN_SESSION_ID = "APPCONNSESSIONID";

    private String channel;
    private String variable;
    private String value;

    public VarSetEvent() {
        setName(RESPONSE_NAME);
    }

    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);
        setChannel(metadata.get(AmiResponseField.Channel.name()));
        setVariable(metadata.get(AmiResponseField.Variable.name()));
        setValue(metadata.get(AmiResponseField.Value.name()));
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return toString(String.format("channel=%s;variable=%s;value=%s", channel, variable, value));
    }
}