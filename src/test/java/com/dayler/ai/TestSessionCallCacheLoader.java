package com.dayler.ai;

import com.google.common.cache.CacheLoader;

public class TestSessionCallCacheLoader extends CacheLoader<String, Object> {

    @Override
    public Object load(String key) throws Exception {
        return new Object();
    }

}
