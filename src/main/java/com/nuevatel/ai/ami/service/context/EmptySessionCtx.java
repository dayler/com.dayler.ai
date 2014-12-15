/**
 *
 */
package com.nuevatel.ai.ami.service.context;

import com.nuevatel.common.util.StringUtils;

/**
 * Empty session.
 *
 * @author asalazar
 */
public class EmptySessionCtx implements SessionCtx {

    /**
     * Immutable {@link SessionCtx} instance.
     */
    public static final SessionCtx EMPTY_SESSION = new EmptySessionCtx();

    private EmptySessionCtx() {
        // Prevent instantiation.
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.service.context.SessionCtx#getSessionID()
     */
    @Override
    public String getSessionID() {
        return StringUtils.EMPTY;
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.service.context.SessionCtx#getContextVar(java.lang.String)
     */
    @Override
    public Object getContextVar(String ctxKey) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.service.context.SessionCtx#putCtxVar(java.lang.String, java.lang.Object)
     */
    @Override
    public void putCtxVar(String ctxKey, Object ctxVar) {
        // No op
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.service.context.SessionCtx#removeCtxVar(java.lang.String)
     */
    @Override
    public void removeCtxVar(String ctxKey) {
        // No op
    }

    @Override
    public String getCallID() {
        return null;
    }

    @Override
    public String getChannel() {
        return null;
    }

    @Override
    public void setCallID(String callID) {
        // No op
    }

    @Override
    public void setChannel(String channel) {
        // No op
    }

    @Override
    public String getString(String ctxKey) {
        return null;
    }
}
