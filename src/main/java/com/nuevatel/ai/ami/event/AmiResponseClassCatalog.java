/**
 *
 */
package com.nuevatel.ai.ami.event;

/**
 * 
 * 
 * @author asalazar
 */
public enum AmiResponseClassCatalog {
    BridgeEventClass(BridgeEvent.RESPONSE_NAME, BridgeEvent.class),
    DialEventClass(DialEvent.REPONSE_NAME, DialEvent.class),
    HangupEventClass(HangupEvent.RESPONSE_NAME, HangupEvent.class),
    HangupRequestEventClass(HangupRequestEvent.RESPONSE_NAME, HangupRequestEvent.class),
    NewAccountCodeEventClass(NewAccountCodeEvent.RESPONSE_NAME, NewAccountCodeEvent.class),
    NewchannelEventClass(NewChannelEvent.RESPONSE_NAME, NewChannelEvent.class),
    NewExtenEventClass(NewExtenEvent.RESPONSE_NAME, NewExtenEvent.class),
    NewStateEventClass(NewStateEvent.RESPONSE_NAME, NewStateEvent.class),
    PeerStatusEventClass(PeerStatusEvent.RESPONSE_NAME, PeerStatusEvent.class),
    RtcpReceivedEventClass(RtcpReceivedEvent.RESPONSE_NAME, RtcpReceivedEvent.class),
    RtcpSentEventClass(RtcpSentEvent.RESPONSE_NAME, RtcpSentEvent.class),
    SoftHangupRequestEventClass(SoftHangupRequestEvent.RESPONSE_NAME, SoftHangupRequestEvent.class),
    VarSetEventClass(VarSetEvent.RESPONSE_NAME, VarSetEvent.class),
    AmiRemoveSessionEventClass(AmiDiscardSessionEvent.RESPONSE_NAME, AmiDiscardSessionEvent.class),
    AmiStartSessionEventClass(AmiCreateSessionEvent.RESPONSE_NAME, AmiCreateSessionEvent.class),
    CDREventClass(CDREvent.RESPONSE_NAME, CDREvent.class),
    ;

    private String eventName;

    private Class<? extends Response> clazz;

    private AmiResponseClassCatalog(String eventName, Class<? extends Response> clazz) {
        this.eventName = eventName;
        this.clazz = clazz;
    }

    public Class<? extends Response> getClazz() {
        return clazz;
    }

    public static Class<? extends Response> classFromEventName(String eventName) {
        for (AmiResponseClassCatalog responseCat : values()) {
            if (responseCat.eventName.equalsIgnoreCase(eventName)) {
                return responseCat.getClazz();
            }
        }

        return null;
    }
}
