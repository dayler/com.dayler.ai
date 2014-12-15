package com.dayler.ai.ami.service.cache;

import com.dayler.ai.ami.event.AmiCreateSessionEvent;
import com.dayler.ai.ami.event.AmiEventArgs;
import com.dayler.ai.ami.event.AmiResponseField;
import com.dayler.ai.ami.service.AmiService;
import com.dayler.ai.ami.service.context.SessionCtx;
import com.dayler.ai.ami.service.context.SessionCtxImpl;
import com.google.common.cache.CacheLoader;
import com.dayler.common.exception.OperationException;

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
