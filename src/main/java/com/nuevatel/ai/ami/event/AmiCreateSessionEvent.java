/**
 *
 */
package com.nuevatel.ai.ami.event;

import com.nuevatel.ai.ami.service.context.SessionCtx;

import java.util.Map;

import static com.nuevatel.common.util.ClassUtil.castAs;

/**
 * @author asalazar
 */
public class AmiCreateSessionEvent extends AmiEvent {

    public static final String RESPONSE_NAME = "AmiCreateSessionEvent";

    private SessionCtx sessionCtx;

    public AmiCreateSessionEvent() {
        setName(RESPONSE_NAME);
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.event.Response#build(java.util.Map, java.lang.Object)
     */
    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);

        if (sessionCtx == null) {
            return;
        }

        setUniqueId(sessionCtx.getSessionID());
        setSessionCtx(castAs(SessionCtx.class, ctx));
    }

    public SessionCtx getSessionCtx() {
        return sessionCtx;
    }

    public void setSessionCtx(SessionCtx sessionCtx) {
        this.sessionCtx = sessionCtx;
    }

    @Override
    public String toString() {
        return toString(String.format("sessionCtx=%s", sessionCtx));
    }
}
