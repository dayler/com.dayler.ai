/**
 *
 */
package com.dayler.ai.ami.service.cache;

import com.dayler.ai.ami.service.context.SessionCtx;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

/**
 * Builds a {@link SessionCache} instance for AMI Service.
 *
 * @author asalazar
 */
public class SessionCacheFactory extends AbstractCacheFactory<SessionCache, SessionCtx>{

    public SessionCache getCache() {
        SessionCacheImpl sessionCache = new SessionCacheImpl();
        CacheBuilder<Object, Object> cacheBuilder = getCacheBuilder();
        LoadingCache<String, SessionCtx> cache = cacheBuilder.build(cacheLoader);
        sessionCache.setCache(cache);

        return sessionCache;
    }
}
