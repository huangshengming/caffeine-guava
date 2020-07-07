package com.hsm.cache;

import com.google.common.cache.*;
import com.hsm.statistic.CacheStatistics;
import com.hsm.statistic.IStatisticsPrinter;
import com.hsm.statistic.StatisticsUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 基于guava实现的缓存服务模块,当前缓存模块附带统计指标
 * @param <K>
 * @param <V>
 */
public class CacheBase<K, V> implements IStatisticsPrinter {

    protected Logger logger = LoggerFactory.getLogger(CacheBase.class);

    public static enum ExpireBy {
        ByReadTime, ByWriteTime
    }

    class ValueLoader implements RemovalListener<K, V> {
        @Override
        public void onRemoval(RemovalNotification<K, V> info) {
            logger.debug("removing key {}", info.getKey());
            try {
                saveValue(info.getKey(), info.getValue());
            } catch (Exception e) {
                logger.error("removing cache key {}, value {}", info.getKey(), info.getValue(), e);
            }
        }
    }

    class ValueSaver extends CacheLoader<K, V> {
        @Override
        public V load(K key) {
            try {
                return loadValue(key);
            } catch (Exception e) {
                logger.error("loading value for key {} error {}", key+e.getMessage(), e);
                return null;
            }
        }
    }

    private LoadingCache<K, V> cache;
    final private ExpireBy expireBy;
    final private int maxSize;
    final private String printerName;
    final private int timeoutInMinutes;

    public CacheBase(CacheBase.ExpireBy expireBy) {
        this("", expireBy, CacheConstant.LOGIC_CACHE_TIMEOUT_MINUTE, CacheConstant.LOGIC_CACHE_SIZE);
    }
    public CacheBase() {
        this("", ExpireBy.ByReadTime, CacheConstant.LOGIC_CACHE_TIMEOUT_MINUTE, CacheConstant.LOGIC_CACHE_SIZE);
    }
    public CacheBase(String printerName, ExpireBy expireBy, int timeoutInMinutes, int maxSize) {
        super();
        this.printerName = printerName;
        this.expireBy = expireBy;
        this.timeoutInMinutes = timeoutInMinutes;
        this.maxSize = maxSize;
        logger = LoggerFactory.getLogger(this.getClass().getName());
        logger.info("cache -{} getTimeoutInMinutes:{}Mins getMaxSize:{}", printerName, timeoutInMinutes, maxSize);
        if (expireBy == ExpireBy.ByWriteTime) {
            cache = CacheBuilder.
                    newBuilder().
                    expireAfterWrite(timeoutInMinutes, TimeUnit.MINUTES).
                    maximumSize(maxSize).
                    removalListener(new ValueLoader()).
                    recordStats().
                    build(new ValueSaver());
        } else {
            cache = CacheBuilder.
                    newBuilder().
                    expireAfterAccess(timeoutInMinutes, TimeUnit.MINUTES).
                    maximumSize(maxSize).
                    removalListener(new ValueLoader()).
                    recordStats().
                    build(new ValueSaver());
        }

        CacheStatistics.INSTANCE.addPrinter(this);
    }


    public Collection<V> entrySet() {
        ConcurrentMap<K, V> map = cache.asMap();
        return map.values();
    }

    public V get(K key) {
        try {
            return cache.get(key);
        } catch (CacheLoader.InvalidCacheLoadException e) {
            onLoadNull(key, e);
            return null;
        } catch (Exception e) {
            logger.error("load key {} error", key, e);
            onLoadNull(key, e);
            return null;
        }
    }

    public V get(K key, Callable<? extends V> valueLoader) throws ExecutionException {
        return cache.get(key, valueLoader);
    }

    public ExpireBy getExpireBy() {
        return expireBy;
    }

    public V getIfPresent(K key) {
        try {
            return cache.getIfPresent(key);
        } catch (Exception e) {
            logger.error("get value of key {} failed", key, e);
            return null;
        }
    }

    public String getPrinterName() {
        if (StringUtils.isBlank(printerName))
            return this.getClass().getSimpleName();
        else
            return printerName;
    }


    public void invalidate(K key) {
        cache.invalidate(key);
    }

    public void invalidateAll() {
        cache.invalidateAll();
    }

    @Override
    public void printStatistics(PrintWriter writer) {
        StatisticsUtil.printCacheStats(writer, this.getPrinterName(), cache);
    }

    public void put(K uid, V userInfo) {
        cache.put(uid, userInfo);
    }

    public void saveValueImmediately(K key, V value) {
        this.saveValue(key, value);
    }

    public void shutdown() {
        try {
            cache.invalidateAll();
            cache.cleanUp();
        } catch (Exception e) {
            logger.error("invalidate failed", e);
        }

    }

    public long size() {
        return cache.size();
    }
    protected int getMaxSize() {
        return this.maxSize;
    }
    protected int getTimeoutInMinutes() {
        return this.timeoutInMinutes;
    }

    /**
     * 缓存中无数据时回调方法
     * @param key
     * @return
     * @throws Exception
     */
    protected V loadValue(K key) throws Exception {
        return null;
    }

    protected void onLoadNull(K key, Exception e) {
        logger.info("get value of key {} failed", key, e);
    }

    /**
     * 清除缓存时调用
     * @param key
     * @param value
     */
    protected void saveValue(K key, V value) {

    }

}
