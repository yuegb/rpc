package com.core.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date: 2020-11-13 15:00
 * @Description:
 */
public class LoggerUtil {

    private static Logger trace = LoggerFactory.getLogger("trace");
    private static Logger debug = LoggerFactory.getLogger("debug");
    private static Logger info = LoggerFactory.getLogger("info");
    private static Logger warn = LoggerFactory.getLogger("warn");
    private static Logger error = LoggerFactory.getLogger("error");

    public static void trace(String msg) {
        trace.trace(msg);
    }

    public static void debug(String msg) {
        debug.debug(msg);
    }

    public static void debug(String format, Object... argArray) {
        debug.debug(format, argArray);
    }

    public static void debug(String msg, Throwable t) {
        debug.debug(msg, t);
    }

    public static void info(String msg) {
        info.info(msg);
    }

    public static void info(String format, Object... argArray) {
        info.info(format, argArray);
    }

    public static void info(String msg, Throwable t) {
        info.info(msg, t);
    }

    public static void warn(String msg) {
        warn.warn(msg);
    }

    public static void warn(String format, Object... argArray) {
        warn.warn(format, argArray);
    }

    public static void warn(String msg, Throwable t) {
        warn.warn(msg, t);
    }

    public static void error(String msg) {
        error.error(msg);
    }

    public static void error(String format, Object... argArray) {
        error.error(format, argArray);
    }

    public static void error(String msg, Throwable t) {
        error.error(msg, t);
    }

}
