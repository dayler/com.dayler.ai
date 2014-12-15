/**
 *
 */
package com.nuevatel.ai.ami.service.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.nuevatel.ai.ami.service.context.SessionCtx;

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
