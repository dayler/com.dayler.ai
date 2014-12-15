/**
 *
 */
package com.nuevatel.ai.ami.service.context;

import com.nuevatel.common.util.Parameters;

import java.util.HashMap;
import java.util.Map;

/**
 * @author asalazar
 */
public class SessionCtxImpl implements SessionCtx {

    private String sessionId;

    private String callId = null;

    private String channel = null;

    private Map<String, Object> ctxMap = new HashMap<String, Object>();

    public SessionCtxImpl(String sessionId) {
        Parameters.checkBlankString(sessionId, "sessionId");
        this.sessionId = sessionId;
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.service.context.SessionCtx#getSessionID()
     */
    @Override
    public String getSessionID() {
        return sessionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCallID() {
        return callId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCallID(String callID) {
        callId = callID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getChannel() {
        return channel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.service.context.SessionCtx#getContextVar(java.lang.String)
     */
    @Override
    public Object getContextVar(String ctxKey) {
        return ctxMap.get(ctxKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(String ctxKey) {
        Object value = ctxMap.get(ctxKey);

        if (value == null) {
            return null;
        }

        return value.toString();
    }

    /* (non-Javadoc)s
     * @see com.nuevatel.ai.ami.service.context.SessionCtx#putCtxVar(java.lang.String, java.lang.Object)
     */
    @Override
    public void putCtxVar(String ctxKey, Object ctxVar) {
        ctxMap.put(ctxKey, ctxVar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCtxVar(String ctxKey) {
        if (ctxMap.containsKey(ctxKey)) {
            ctxMap.remove(ctxKey);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("SessionCtxImpl{sessionId=%s; callId=%s; channel=%s ctxMap=%s}",
                            sessionId, callId, channel, ctxMap);
    }
}
