package com.liuweimin.adper.autoimport.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 *
 * @author wuliang
 * @version $Id: LogUtil.java, v 0.1 2018年11月4日 下午5:05:26 wuliang Exp $
 */
public class LogUtil {


    /**
     * 获取最原始被调用的堆栈信息
     *
     * @return
     * @author yzChen
     * @date 2016年10月13日 下午11:50:59
     */
    public static StackTraceElement findCaller() {
        // 获取堆栈信息
        StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
        if (null == callStack) return null;

        // 最原始被调用的堆栈信息
        StackTraceElement caller = null;
        // 日志类名称
        String logClassName = LogUtil.class.getName();
        // 循环遍历到日志类标识
        boolean isEachLogClass = false;

        // 遍历堆栈信息，获取出最原始被调用的方法信息
        for (StackTraceElement s : callStack) {
            // 遍历到日志类
            if (logClassName.equals(s.getClassName())) {
                isEachLogClass = true;
            }
            // 下一个非日志类的堆栈，就是最原始被调用的方法
            if (isEachLogClass) {
                if (!logClassName.equals(s.getClassName())) {
                    isEachLogClass = false;
                    caller = s;
                    break;
                }
            }
        }

        return caller;
    }

    /**
     * 自动匹配请求类名，生成logger对象，此处 logger name 值为 [className].[methodName]() Line: [fileLine]
     *
     * @return
     * @author yzChen
     * @date 2016年10月13日 下午11:50:59
     */
    private static Logger logger() {
        // 最原始被调用的堆栈对象
        StackTraceElement caller = findCaller();
        if (null == caller) return LoggerFactory.getLogger(LogUtil.class);

        Logger log = LoggerFactory.getLogger(caller.getClassName() + "." + caller.getMethodName() + "() Line: " + caller.getLineNumber());

        return log;
    }

    public static void debug(String message) {
        if (logger().isDebugEnabled()) {
            logger().debug("{},{}", TraceUtil.getCorrelationID(), message);
        }
    }

    public static void debug(String message, String... strings) {
        if (logger().isDebugEnabled()) {
            logger().debug("{},{}", TraceUtil.getCorrelationID(), message, strings);
        }
    }

    public static void debug(Logger logger, String message) {
        if (logger.isDebugEnabled()) {
            logger.debug("{},{}", TraceUtil.getCorrelationID(), message);
        }
    }

    public static void debug(Logger logger, String message, String... strings) {
        if (logger.isDebugEnabled()) {
            logger.debug("{},{},{}", TraceUtil.getCorrelationID(), message, strings);
        }
    }

    public static void info(String message) {
        logger().info("{},{}", TraceUtil.getCorrelationID(), message);
    }

    public static void info(String message, String... strings) {
        logger().info("{},{},{}", TraceUtil.getCorrelationID(), message, strings);
    }

    public static void info(Logger logger, String message) {
        logger.info("{},{}", TraceUtil.getCorrelationID(), message);
    }

    public static void info(Logger logger, String message, String... strings) {
        logger.info("{},{},{}", TraceUtil.getCorrelationID(), message, strings);
    }

    public static void warn(String message) {
        warn("{},{}", TraceUtil.getCorrelationID(), message);
    }

    public static void warn(String message, String... strings) {
        logger().warn("{},{},{}", TraceUtil.getCorrelationID(), message, strings);
    }

    public static void warn(Logger logger, String message) {
        logger.warn("{},{}", TraceUtil.getCorrelationID(), message);
    }

    public static void warn(Logger logger, String message, String... strings) {
        logger.warn("{},{},{}", TraceUtil.getCorrelationID(), message, strings);
    }

    public static void error(String message) {
        error("{},{}", TraceUtil.getCorrelationID(), message);
    }

    public static void error(String message, Exception exception) {
        logger().error("{},{}", TraceUtil.getCorrelationID(), message, exception);
    }

    public static void error(String message, Throwable exception) {
        logger().error("{},{}", TraceUtil.getCorrelationID(), message, exception);
    }

    public static void error(String message, Object... arguments) {
        logger().error("{},{}", TraceUtil.getCorrelationID(), message, arguments);
    }

    public static void error(String message, String... strings) {
        logger().error("{},{},{}", TraceUtil.getCorrelationID(), message, strings);
    }

    public static void error(Logger logger, String message) {
        logger.error("{},{}", TraceUtil.getCorrelationID(), message);
    }

    public static void error(Logger logger, String message, Exception exception) {
        logger.error("{},{}", TraceUtil.getCorrelationID(), message, exception);
    }

    public static void error(Logger logger, String message, String... strings) {
        logger.error("{},{},{}", TraceUtil.getCorrelationID(), message, strings);
    }

}
