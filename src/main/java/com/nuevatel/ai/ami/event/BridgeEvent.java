package com.nuevatel.ai.ami.event;

import java.util.Map;

public class BridgeEvent extends AmiEvent {

    public static final String RESPONSE_NAME = "Bridge";

    public static final String BRIDGE_STATE_LINK = "Link";
    public static final String BRIDGE_STATE_UNLINK = "Unlink";
    public static final String BRIDGE_TYPE_CORE = "core";
    public static final String BRIDGE_TYPE_RTP_NATIVE = "rtp-native";
    public static final String BRIDGE_TYPE_RTP_DIRECT = "rtp-direct";
    public static final String BRIDGE_TYPE_RTP_REMOTE = "rtp-remote";

    private String bridgeState;
    private String bridgeType;
    private String channel1;
    private String channel2;
    private String uniqueId1;
    private String uniqueId2;
    private String callerId1;
    private String callerId2;

    public BridgeEvent() {
        setName(RESPONSE_NAME);
    }


    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);
        setBridgeState(metadata.get(AmiResponseField.BridgeState.name()));
        setBridgeType(metadata.get(AmiResponseField.BridgeType.name()));
        setChannel1(metadata.get(AmiResponseField.Channel1.name()));
        setChannel2(metadata.get(AmiResponseField.Channel2.name()));
        setUniqueId1(metadata.get(AmiResponseField.UniqueId1.name()));
        setUniqueId2(metadata.get(AmiResponseField.UniqueId2.name()));
        setCallerId1(metadata.get(AmiResponseField.CallerId1.name()));
        setCallerId2(metadata.get(AmiResponseField.CallerId2.name()));
    }

    public String getBridgeState() {
        return bridgeState;
    }

    public void setBridgeState(String bridgeState) {
        this.bridgeState = bridgeState;
    }

    public String getBridgeType() {
        return bridgeType;
    }

    public void setBridgeType(String bridgeType) {
        this.bridgeType = bridgeType;
    }

    public String getChannel1() {
        return channel1;
    }

    public void setChannel1(String channel1) {
        this.channel1 = channel1;
    }

    public String getChannel2() {
        return channel2;
    }

    public void setChannel2(String channel2) {
        this.channel2 = channel2;
    }

    public String getUniqueId1() {
        return uniqueId1;
    }

    public void setUniqueId1(String uniqueId1) {
        this.uniqueId1 = uniqueId1;
    }

    public String getUniqueId2() {
        return uniqueId2;
    }

    public void setUniqueId2(String uniqueId2) {
        this.uniqueId2 = uniqueId2;
    }

    public String getCallerId1() {
        return callerId1;
    }

    public void setCallerId1(String callerId1) {
        this.callerId1 = callerId1;
    }

    public String getCallerId2() {
        return callerId2;
    }

    public void setCallerId2(String callerId2) {
        this.callerId2 = callerId2;
    }

    @Override
    public String toString() {
        return toString(String.format("bridgeState=%s, bridgeType=%s, channel1=%s, channel2=%s, "
                + "uniqueId1=%s, uniqueId2=%s, callerId1=%s, callerId2=%s",
                bridgeState,
                bridgeType,
                channel1,
                channel2,
                uniqueId1,
                uniqueId2,
                callerId1,
                callerId2));
    }
}