package com.hsm.cache;

public class CacheConstant {

    public static final int LOGIC_CACHE_TIMEOUT_MINUTE = 30;
    public static final int LOGIC_BINDING_TIMEOUT_MINUTE = LOGIC_CACHE_TIMEOUT_MINUTE + 10;

    public static final int LOGIC_MAX_BINDING_COUNT = 2000;
    public static final int LOGIC_CACHE_SIZE = LOGIC_MAX_BINDING_COUNT + 100;

    // Login最大缓存的uid->logic绑定
    public static final int MAX_CACHED_LOGIC_BINDING_COUNT = 100000;
    public static final int MAX_CACHED_USERNAME_UID_COUNT = 500000;

    public static final int MAX_TEAM_CACHE_COUNT = 30000;

    /** 最大缓存的入队申请记录10w */
    public static final int MAX_TEAM_JOIN_APPLY_COUNT = 10000;
    /** 入队申请缓存过期时间1天 */
    public static final int MAX_TEAM_JOIN_APPLY_MINUTE = 24 * 60;
}
