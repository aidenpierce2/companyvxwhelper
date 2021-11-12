package com.xwq.companyvxwhelper.utils

import android.os.Build
import com.xwq.companyvxwhelper.MyApplication

class PackageInfoUtils {

    companion object {
        // 获取版本号
        fun getVerisonCode() : String{
            var packageManager = MyApplication.app.packageManager
            var packageInfo = packageManager.getPackageInfo(MyApplication.app.packageName, 0)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                return packageInfo.versionCode.toString()
            }
            else {
                return packageInfo.longVersionCode.toString()
            }
        }

        // 获取版本名
        fun getVersionName() : String{
            var packageManager = MyApplication.app.packageManager
            var packageInfo = packageManager.getPackageInfo(MyApplication.app.packageName, 0)
            return packageInfo.versionName
        }
    }
}