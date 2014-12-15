/**
 * 
 */
package com.dayler.ai.ami.service.cache;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.LoadingCache;
import com.dayler.common.exception.OperationException;
import com.dayler.common.util.Parameters;
import com.dayler.common.util.StringUtils;

/**
 * @author asalazar
 *
 */
public class SimpleCacheService {

    private LoadingCache<String, Object>cache;

    void setCache(LoadingCache<String, Object>cache) {
        this.cache = cache;
    }

    public Object get(String key) throws OperationException {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        try {
            return cache.get(key);
        } catch (ExecutionException ex) {
            String msg = String.format("Failed to load call:%s", key);
            throw new OperationException(msg, ex);
        }
    }

    public void invalidateCall(String key) {
        if (StringUtils.isEmptyOrNull(key)) {
            return;
        }

        cache.invalidate(key);
    }

    public void put(String key, Object value) {
        Parameters.checkNull(key, "key");
        Parameters.checkNull(value, "obj");

        cache.put(key, value);
    }

    public long size() {
        return cache.size();
    }
}
