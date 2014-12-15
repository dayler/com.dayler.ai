/**
 * 
 */
package com.dayler.ai.ami.service.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

/**
 * AbstractCacheFactory<SessionCache, SessionCtx>{
 * 
 * @author asalazar
 *
 */
public class CacheServiceFactory extends AbstractCacheFactory<SimpleCacheService, Object>{

    @Override
    public SimpleCacheService getCache() {
        SimpleCacheService cacheService = new SimpleCacheService();
        CacheBuilder<Object, Object> cacheBuilder = getCacheBuilder();
        LoadingCache<String, Object> cache = cacheBuilder.build(cacheLoader);
        cacheService.setCache(cache);

        return cacheService;
    }

}
