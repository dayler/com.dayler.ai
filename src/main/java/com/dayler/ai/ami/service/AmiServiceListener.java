/**
 *
 */
package com.dayler.ai.ami.service;

import com.dayler.ai.ami.action.AmiAction;
import com.dayler.ai.ami.event.AmiEventArgs;
import com.dayler.ai.ami.service.cache.SimpleCacheService;
import com.dayler.ai.ami.service.context.SessionCtx;
import com.dayler.common.exception.OperationException;

import java.util.List;

/**
 * @author asalazar
 */
public interface AmiServiceListener extends Runnable {

    void service(AmiEventArgs eventArgs) throws OperationException;

    void terminate();

    void setServiceHandlers(List<AmiServiceHandler> handlers);

    void scheduleAction(AmiAction action, String internalActionId) throws OperationException;

    /**
     * 
     * @param uniqueSessionId Unique identifier for the session.
     * 
     * @return SessionCtx match with the uniqueSessionId.
     * 
     * @throws OperationException If the SessionCtx could not be retrieved.
     */
    SessionCtx getSessionCtx(String uniqueSessionId) throws OperationException;

    SimpleCacheService getCacheService();

    void invalidateSession(String sessionCtxId);
}
