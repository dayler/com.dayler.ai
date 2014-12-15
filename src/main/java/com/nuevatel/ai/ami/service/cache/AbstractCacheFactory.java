/**
 * 
 */
package com.nuevatel.ai.ami.service.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.RemovalListener;

/**
 * T -> Cache king impl.
 * S -> Type for obj provided by the cache
 * 
 * @author asalazar
 *
 */
public abstract class AbstractCacheFactory<T, S> {

    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;

    private static final int DEFAULT_EXPIRE_AFTER_ACCESS = 300000;

    private static final int DEFAULT_EXPIRE_AFTER_WRITE = 3600000;

    /**
     * Time to expire session after creation. Expressed in milliseconds.
     */
    protected long expireAfterWrite = DEFAULT_EXPIRE_AFTER_WRITE;

    /**
     * Inactivity session time expire for the sessions. Expressed in milliseconds.
     */
    protected long expireAfterAccess = DEFAULT_EXPIRE_AFTER_ACCESS;

    /**
     * Number of concurrent threads to access to the resource.
     */
    protected int concurrencyLevel = DEFAULT_CONCURRENCY_LEVEL;

    protected RemovalListener<String, S> removalListener;

    protected CacheLoader<String, S> cacheLoader;

    public abstract T getCache();

    public AbstractCacheFactory<T, S> setCacheLoader(CacheLoader<String, S> cacheLoader) {
        this.cacheLoader = cacheLoader;
        return this;
    }

    /**
     * Sets removal listener for session objects
     *
     * @param removalListener the removal listener.
     * @return Factory
     */
    public AbstractCacheFactory<T, S> setRemovalListener(RemovalListener<String, S> removalListener) {
        this.removalListener = removalListener;
        return this;
    }

    /**
     * @param expireAfterWrite Time to expire session after creation. Expressed in milliseconds.
     * @return Factory
     */
    public AbstractCacheFactory<T, S> setExpireAfterWrite(long expireAfterWrite) {
        this.expireAfterWrite = expireAfterWrite;
        return this;
    }

    /**
     * @param expireAfterAccess Inactivity session time expire for the sessions. Expressed in
     *                          milliseconds.
     * @return Factory
     */
    public AbstractCacheFactory<T, S> setExpireAfterAccess(long expireAfterAccess) {
        this.expireAfterAccess = expireAfterAccess;
        return this;
    }

    /**
     * @param concurrencyLevel Number of concurrent threads to access to the resource.
     * @return Factory
     */
    public AbstractCacheFactory<T, S> setConcurrencyLevel(int concurrencyLevel) {
        this.concurrencyLevel = concurrencyLevel;
        return this;
    }

    /**
     * @return
     */
    protected final CacheBuilder<Object, Object> getCacheBuilder() {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
                .expireAfterWrite(expireAfterWrite, TimeUnit.MILLISECONDS)
                .expireAfterAccess(expireAfterAccess, TimeUnit.MILLISECONDS)
                .concurrencyLevel(concurrencyLevel);

        // Set removal listener.
        if (removalListener != null) {
            cacheBuilder.removalListener(removalListener);
        }
        return cacheBuilder;
    }
}
