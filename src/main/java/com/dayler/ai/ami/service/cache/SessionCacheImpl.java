/**
 *
 */
package com.dayler.ai.ami.service.cache;

import com.dayler.ai.ami.service.context.SessionCtx;
import com.google.common.cache.LoadingCache;
import com.dayler.common.exception.OperationException;
import com.dayler.common.util.StringUtils;

import org.apache.log4j.Logger;

import java.util.concurrent.ExecutionException;

/**
 * Session cache for AMI sessions.
 *
 * @author asalazar
 */
class SessionCacheImpl implements SessionCache {

    /**
     * Application logger.
     */
    private static Logger logger = Logger.getLogger(SessionCacheImpl.class);

    private LoadingCache<String, SessionCtx> cache;

    public SessionCacheImpl() {
        // Used to prevent external instantiation.
    }

    void setCache(LoadingCache<String, SessionCtx> cache) {
        this.cache = cache;
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.service.cache.SessionCache#getSession(java.lang.String)
     */
    @Override
    public SessionCtx getSession(String sessionId) throws OperationException {
        if (StringUtils.isBlank(sessionId)) {
            return null;
        }

        try {
            // Use guava to get value.
            return cache.get(sessionId);
        } catch (ExecutionException ex) {
            String msg = String.format("Failed to load Session: %s", sessionId);
            logger.error(msg, ex);
            throw new OperationException(msg, ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidateSession(String sessionId) {
        if (StringUtils.isEmptyOrNull(sessionId)) {
            return;
        }

        cache.invalidate(sessionId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long size() {
        return cache.size();
    }
}
