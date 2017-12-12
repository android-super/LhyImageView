package com.lhy.util;

import android.content.Context;

/**
 * 像素转换
 */
public class DensityUtil {

    /**
     * dp to px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp) {
        //获取像素密度
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }

    /**
     * px to dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(Context context, int px) {
        //获取像素密度
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

}