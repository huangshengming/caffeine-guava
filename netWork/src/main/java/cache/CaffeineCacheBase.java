package com.hsm.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.hsm.statistic.CacheStatistics;
import com.hsm.statistic.IStatisticsPrinter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class CaffeineCacheBase<K, V> implements IStatisticsPrinter {
    protected Logger logger = LoggerFactory.getLogger(CaffeineCacheBase.class);

    @Override
    public void printStatistics(PrintWriter printer) {

    }

    public static enum ExpireBy {
        ByReadTime, ByWriteTime
    }

    public class ValueRemover implements RemovalListener<K, V> {

        @Override
        public void onRemoval(@Nullable K key, @Nullable V value, @NonNull RemovalCause cause) {
            logger.debug("removing key {},value:{}, cause: {}", key, value, cause);
            try {
                saveValue(key, value);
            } catch (Exception e) {
                logger.error("removing cache key {}, value {}", key, value, e);
            }
        }
    }


    private LoadingCache<K, V> cache;
    final private CaffeineCacheBase.ExpireBy expireBy;
    final private int maxSize;
    final private String printerName;
    final private int timeoutInMinutes;

    public CaffeineCacheBase(CaffeineCacheBase.ExpireBy expireBy) {
        this("", expireBy, CacheConstant.LOGIC_CACHE_TIMEOUT_MINUTE, CacheConstant.LOGIC_CACHE_SIZE);
    }
    public CaffeineCacheBase() {
        this("", ExpireBy.ByReadTime, CacheConstant.LOGIC_CACHE_TIMEOUT_MINUTE, CacheConstant.LOGIC_CACHE_SIZE);
    }

    public CaffeineCacheBase(String printerName, CaffeineCacheBase.ExpireBy expireBy, int timeoutInMinutes, int maxSize){
        this.printerName = printerName;
        this.expireBy = expireBy;
        this.timeoutInMinutes = timeoutInMinutes;
        this.maxSize = maxSize;
        logger.info("cache -{} getTimeoutInMinutes:{}Mins getMaxSize:{}", printerName, timeoutInMinutes, maxSize);
        if (expireBy == CaffeineCacheBase.ExpireBy.ByWriteTime) {
            cache = Caffeine.
                    newBuilder().
                    expireAfterWrite(timeoutInMinutes, TimeUnit.SECONDS).
                    maximumSize(maxSize).
                    removalListener(new ValueRemover()).
                    recordStats().
                    build(k->loadValue(k));
        } else {
            cache = Caffeine.newBuilder().
                    expireAfterAccess(timeoutInMinutes, TimeUnit.SECONDS).
                    maximumSize(maxSize).
                    removalListener(new ValueRemover()).
                    recordStats().
                    build(k -> loadValue(k));
        }
        CacheStatistics.INSTANCE.addPrinter(this);
    }

    public V get(K key){
        return cache.get(key);
    }


    public V loadValue(K key){
        return null;
    }

    public void saveValue(K key, V value){

    }

}
