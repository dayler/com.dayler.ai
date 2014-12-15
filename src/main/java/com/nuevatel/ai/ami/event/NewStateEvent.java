package com.nuevatel.ai.ami.event;

import com.nuevatel.ai.ami.ChannelState;
import com.nuevatel.common.util.IntegerUtil;

import java.util.Map;

public class NewStateEvent extends AmiEvent {

    public static final String RESPONSE_NAME = "Newstate";

    private String channel;
    private ChannelState channelState;
    private String channelStateDesc;
    private String callerIdNum;
    private String callerIdName;
    private String connectedLineNum;
    private String connectedLineName;

    public NewStateEvent() {
        setName(RESPONSE_NAME);
    }

    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);
        setChannel(metadata.get(AmiResponseField.Channel.name()));
        setChannelState(metadata.get(AmiResponseField.ChannelState.name()));
        setCallerIdNum(metadata.get(AmiResponseField.CallerIDNum.name()));
        setCallerIdName(metadata.get(AmiResponseField.CallerIDName.name()));
        setConnectedLineNum(metadata.get(AmiResponseField.ConnectedLineNum.name()));
        setConnectedLineName(metadata.get(AmiResponseField.ConnectedLineName.name()));
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public ChannelState getChannelState() {
        return channelState;
    }

    public void setChannelState(ChannelState channelState) {
        this.channelState = channelState;
    }

    protected void setChannelState(String channelState) {
        Integer state = IntegerUtil.tryParse(channelState);
        this.channelState = ChannelState.valueOf(state == null ? ChannelState.HUNGUP.getStatus() : state);
    }

    public String getChannelStateDesc() {
        return channelStateDesc;
    }

    public void setChannelStateDesc(String channelStateDesc) {
        this.channelStateDesc = channelStateDesc;
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

    @Override
    public String toString() {
        return toString(String.format("name=%s, uniqueId=%s, channel=%s, channelState=%s, channelStateDesc=%s,"
                + "callerIdNum=%s, callerIdName=%s, connectedLineNum=%s, connectedLineName=%s",
                getName(), getUniqueId(), channel, channelState, channelStateDesc, callerIdNum,
                callerIdName, connectedLineNum, connectedLineName));
    }
}