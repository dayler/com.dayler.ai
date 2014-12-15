/**
 *
 */
package com.dayler.ai.ami.event;

import static com.dayler.common.util.Util.*;

import com.dayler.ai.ami.service.AmiService;
import com.dayler.ai.ami.service.context.SessionCtx;
import com.dayler.common.util.StringUtils;

import java.util.Map;

/**
 * Throws event when Session is removed from ami session cache. There is not an event from AMI
 * asterisk interface, it is exclusive for {@link AmiService} implementation.
 *
 * @author asalazar
 */
public class AmiDiscardSessionEvent extends AmiEvent {

    public static final String RESPONSE_NAME = "AmiRemoveSessionEvent";

    private SessionCtx sessionCtx;

    private String sessionId;

    private String channel;

    public AmiDiscardSessionEvent() {
        setName(RESPONSE_NAME);
    }

    public SessionCtx getSessionCtx() {
        return sessionCtx;
    }

    public void setSessionCtx(SessionCtx sessionCtx) {
        this.sessionCtx = sessionCtx;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);

        if ((sessionCtx = castAs(SessionCtx.class, ctx)) == null) {
            return;
        }

        setUniqueId(StringUtils.isBlank(sessionCtx.getSessionID())? StringUtils.EMPTY : sessionCtx.getSessionID());
        setSessionId(metadata.get(AmiResponseField.UniqueId.name()));
        setChannel(metadata.get(AmiResponseField.Channel.name()));
        setSessionCtx(sessionCtx);
    }

    @Override
    public String toString() {
        return toString(String.format("sessionCtx=%s, sessionId=%s, channel=%s",
                sessionCtx, sessionId, channel));
    }
}
