package com.xwq.companyvxwhelper.utils

import android.os.Build
import com.xwq.companyvxwhelper.MyApplication

class PermissionUtil {

    companion object {
        fun shouldCheckVerison() : Boolean{
            if (Build.VERSION.SDK_INT >= 23 && MyApplication.app!!.applicationInfo.targetSdkVersion >= 23) {
                return true
            } else {
                return false
            }
        }
    }
}