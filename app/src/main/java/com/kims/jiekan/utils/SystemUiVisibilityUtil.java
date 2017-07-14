package com.kims.jiekan.utils;

import android.view.View;

/**
 * Created by zhangjian on 2017/5/24.
 */

class SystemUiVisibilityUtil {

    public static void addFlags(View view, int flags) {
        view.setSystemUiVisibility(view.getSystemUiVisibility() | flags);
    }

    public static void clearFlags(View view, int flags) {
        view.setSystemUiVisibility(view.getSystemUiVisibility() & ~flags);
    }

    public static boolean hasFlags(View view, int flags) {
        return (view.getSystemUiVisibility() & flags) == flags;
    }

}
