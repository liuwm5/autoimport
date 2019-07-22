package com.liuweimin.adper.autoimport.config;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;

/**
 * Created by cyy on 2017/9/18.
 */
@Component
public class CacheManager {
    private static Map<String, CacheData> CACHE_DATA = new ConcurrentHashMap<>();

    public static <T> T getData(String key, Load<T> load, int expire) {
        T data = getData(key);
        if (data == null && load != null) {
            data = load.load();
            if (data != null) {
                setData(key, data, expire);
            }
        }
        return data;
    }

    public static <T> T getData(String key) {
        CacheData<T> data = CACHE_DATA.get(key);
        if (data != null && (data.getExpire() <= 0 || data.getSaveTime() >= System.currentTimeMillis())) {
            return data.getData();
        }
        return null;
    }

    public static String getExpire(String key) {
        CacheData data = CACHE_DATA.get(key);
        if (data != null && (data.getExpire() <= 0 || data.getSaveTime() >= System.currentTimeMillis())) {
            return DateUtil.format(new Date(data.getStarttime()), NORM_DATETIME_PATTERN) + "-" + DateUtil.format(new Date(data.getSaveTime()), NORM_DATETIME_PATTERN);
        }
        return null;
    }

    public static <T> void setData(String key, T data, int expire) {
        CACHE_DATA.put(key, new CacheData(data, expire));
    }

    public static void clear(String key) {
        CACHE_DATA.remove(key);
    }

    public static void clearAll() {
        CACHE_DATA.clear();
    }

    public interface Load<T> {
        T load();
    }

    @Data
    private static class CacheData<T> {
        CacheData(T t, int expire) {
            this.data = t;
            this.expire = expire <= 0 ? 0 : expire * 1000;
            this.saveTime = System.currentTimeMillis() + this.expire;
            this.starttime = System.currentTimeMillis();
        }

        private T data;
        // 存活时间
        private long saveTime;
        // 过期时间 小于等于0标识永久存活
        private long expire;
        private long starttime;
    }
}