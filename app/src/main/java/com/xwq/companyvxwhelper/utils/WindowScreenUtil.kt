package com.xwq.companyvxwhelper.utils

import android.content.Context

class WindowScreenUtil {

    companion object {
        fun getScreenWidth(context: Context?) : Int{
            return context!!.resources.displayMetrics.widthPixels
        }

        fun getScreenHeight(context: Context?) : Int {
            return context!!.resources.displayMetrics.heightPixels
        }

        // 获取状态栏高度
        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }
    }
}