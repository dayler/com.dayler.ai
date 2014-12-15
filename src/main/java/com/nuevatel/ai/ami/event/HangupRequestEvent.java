package com.nuevatel.ai.ami.event;

import com.nuevatel.common.util.IntegerUtil;

import java.util.Map;

/**
 * @author asalazar
 *
 */
public class HangupRequestEvent extends AmiEvent {

    public static final String RESPONSE_NAME = "HangupRequest";

    private Integer cause;

    private String channel;

    public HangupRequestEvent() {
        setName(RESPONSE_NAME);
    }

    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);
        setCause(IntegerUtil.tryParse(metadata.get(AmiResponseField.Cause.name())));
        setChannel(metadata.get(AmiResponseField.Channel.name()));
    }

    public Integer getCause() {
        return cause;
    }

    public void setCause(Integer cause) {
        this.cause = cause;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return toString(String.format("cause=%s channel=%s", cause, channel));
    }
}