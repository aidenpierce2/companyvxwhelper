package com.xwq.companyvxwhelper.utils

import android.content.Intent
import android.content.pm.ResolveInfo
import com.xwq.companyvxwhelper.MyApplication

class PackageUtils {

    companion object {
        fun getAllInstallPackage() : List<ResolveInfo>{
            var packageManager = MyApplication.app.packageManager
            var intent : Intent = Intent(Intent.ACTION_MAIN, null)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)

            return packageManager.queryIntentActivities(intent, 0)
        }
    }
}