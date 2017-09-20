package com.diyiliu.common.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description: ICache
 * Author: DIYILIU
 * Update: 2016-03-17 16:36
 */
public interface ICache {
    /**
     * Empty the cache data
     */
    void clear();

    /**
     * Add an object to the cache
     *
     * @param key
     * @param value
     * @
     */
    void put(Object key, Object value);

    /**
     * @param map
     * @
     */
    void put(Map<?, ?> map);

    /**
     * @param key
     * @return
     * @
     */
    boolean containsKey(Object key);

    Object get(Object key);

    Map<String, Object> get(Collection<Object> keys);

    List<?> getAll(Collection<Object> keys);

    boolean remove(Object key);

    int size();

    Set<Object> getKeys();
}
