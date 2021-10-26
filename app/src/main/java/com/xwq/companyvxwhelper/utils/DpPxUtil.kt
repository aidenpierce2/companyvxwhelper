package com.xwq.companyvxwhelper.utils

import com.xwq.companyvxwhelper.MyApplication

class DpPxUtil {

    companion object {
        fun dp2px(dpValue: Int) : Int {
            var density = MyApplication.app.resources.displayMetrics.density
            return (dpValue * density + 0.5f).toInt()
        }

        fun px2dp(pxValue : Float) : Int {
            var density = MyApplication.app.resources.displayMetrics.density
            return (pxValue / density + 0.5f).toInt()
        }
    }
}