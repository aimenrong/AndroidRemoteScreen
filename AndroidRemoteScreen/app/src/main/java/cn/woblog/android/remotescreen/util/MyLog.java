package cn.woblog.android.remotescreen.util;

import android.util.Log;


public class MyLog {

    private static final boolean IS_DEBUG = true;
    private static final String DEFAULT_TAG = "TAG";

    public static void d(String tag, String msg) {
        if (IS_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        if (IS_DEBUG) {
            Log.d(DEFAULT_TAG, msg);
        }
    }

    public static void d(String tag, String msg, Throwable e) {
        if (IS_DEBUG) {
            Log.d(tag, msg, e);
        }
    }

    public static void i(String tag, String msg) {
        if (IS_DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (IS_DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (IS_DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (IS_DEBUG) {
            Log.e(tag, msg, e);
        }
    }

    public static void w(String tag, String msg) {
        if (IS_DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable e) {
        if (IS_DEBUG) {
            Log.w(tag, msg, e);
        }
    }

}
