package com.nuevatel.ai.ami.service.cache;

import com.google.common.cache.CacheLoader;
import com.nuevatel.ai.ami.event.AmiCreateSessionEvent;
import com.nuevatel.ai.ami.event.AmiEventArgs;
import com.nuevatel.ai.ami.event.AmiResponseField;
import com.nuevatel.ai.ami.service.AmiService;
import com.nuevatel.ai.ami.service.context.SessionCtx;
import com.nuevatel.ai.ami.service.context.SessionCtxImpl;
import com.nuevatel.common.exception.OperationException;

import java.util.ArrayDeque;

/**
 * Cache loader delegate.
 *
 * @author asalazar
 */
public class SessionCacheLoader extends CacheLoader<String, SessionCtx> {

    private AmiService amiService;

    public SessionCacheLoader(AmiService amiService) {
        this.amiService = amiService;
    }

    @Override
    public SessionCtx load(String key) throws OperationException {
        SessionCtxImpl sessionCtx = new SessionCtxImpl(key);
        ArrayDeque<String> args = new ArrayDeque<String>();
        args.add(String.format("%s:%s", AmiResponseField.Event.name(),
                AmiCreateSessionEvent.RESPONSE_NAME));
        AmiEventArgs eventArgs = new AmiEventArgs(args);
        eventArgs.setCtx(sessionCtx);
        amiService.offerEvent(eventArgs);

        return sessionCtx;
    }
}
