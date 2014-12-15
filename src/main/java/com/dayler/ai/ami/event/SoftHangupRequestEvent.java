package com.dayler.ai.ami.event;

import java.util.Map;


public class SoftHangupRequestEvent extends AmiEvent {

    public static final String RESPONSE_NAME = "SoftHangupRequest";

    private Integer cause;
    private String causeTxt;

    public SoftHangupRequestEvent() {
        setName(RESPONSE_NAME);
    }

    public SoftHangupRequestEvent(Object source) {
        setName(RESPONSE_NAME);
    }

    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);
    }
}