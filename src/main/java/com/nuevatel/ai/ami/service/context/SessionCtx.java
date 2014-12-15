/**
 *
 */
package com.nuevatel.ai.ami.service.context;

/**
 * Define session context, a instance is created for each active session.
 *
 * @author asalazar
 */
public interface SessionCtx {

    /**
     * @return Unique identifier for the session.
     */
    String getSessionID();

    /**
     * <b>The responsibility to set this value is of the ami service handler implementation.</b>
     * 
     * @return Id to identify the call. Returns <b>null</b> if there is not a call associated with
     * this session.
     */
    String getCallID();

    /**
     * <b>The responsibility to set this value is of the ami service handler implementation.</b>
     * 
     *  @param callID Id to identify the call.
     */
    void setCallID(String callID);

    /**
     * @return Channel associated to this session.
     */
    String getChannel();

    /**
     * 
     * @param channel Channel associated with the session.
     */
    void setChannel(String channel);

    /**
     * @param ctxKey Context variable key;
     * @return Context variable Object associated with ctxKey.
     */
    Object getContextVar(String ctxKey);

    /**
     * 
     * @param ctxKey Context variable key;
     * @return Context variable String associated with ctxKey.
     */
    String getString(String ctxKey);

    /**
     * @param ctxKey Context variable key.
     * @param ctxVar Variable object to put in the context.
     */
    void putCtxVar(String ctxKey, Object ctxVar);

    /**
     * Remove object associated with context.
     *
     * @param ctxKey Context variable key.
     */
    void removeCtxVar(String ctxKey);
}
