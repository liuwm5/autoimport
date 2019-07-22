package com.liuweimin.adper.autoimport.utils;

import java.util.HashMap;
import java.util.UUID;

/**
 * @Author liuwm
 * @Description
 * @Date 8:49 PM 7/8/2019
 **/
public class TraceUtil {
    private static final ThreadLocal<HashMap<String, Object>> threadLocal = new ThreadLocal<HashMap<String, Object>>() {
        protected HashMap<String, Object> initialValue() {
            return new HashMap();
        }
    };

    public TraceUtil() {
    }

    public static void setCorrelationID(String correlationID) {
        if (threadLocal.get() == null) {
            threadLocal.set(new HashMap());
        }

        ((HashMap)threadLocal.get()).put("correlationID", correlationID);
    }

    public static String getCorrelationID() {
        if (threadLocal.get() == null) {
            threadLocal.set(new HashMap());
        }

        Object object = ((HashMap)threadLocal.get()).get("correlationID");
        if (object == null) {
            object = UUID.randomUUID();
            ((HashMap)threadLocal.get()).put("correlationID", object.toString());
        }

        return object.toString();
    }

    public static void setClientIP(String correlationID) {
        if (threadLocal.get() == null) {
            threadLocal.set(new HashMap());
        }

        ((HashMap)threadLocal.get()).put("clientIP", correlationID);
    }

    public static String getClientIP() {
        if (threadLocal.get() != null) {
            Object object = ((HashMap)threadLocal.get()).get("clientIP");
            if (object != null) {
                return object.toString();
            }
        }

        return null;
    }

    public static void clear() {
        if (threadLocal.get() != null) {
            ((HashMap)threadLocal.get()).clear();
        }

    }
}