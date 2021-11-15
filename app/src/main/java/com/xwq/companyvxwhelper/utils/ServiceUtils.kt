package com.xwq.companyvxwhelper.utils

import android.app.ActivityManager
import android.content.Context
import com.xwq.companyvxwhelper.MyApplication

class ServiceUtils {

    companion object {
        fun isTargetServiceRunning(serviceName : String) : Boolean {
            if (serviceName.isNullOrEmpty()) {
                return false
            }
            var serviceManager : ActivityManager = MyApplication.app.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            var runningServicesList : List<ActivityManager.RunningServiceInfo> = serviceManager.getRunningServices(10)
            if (runningServicesList.size <= 0 ) {
                return false
            } else {
                for (element in runningServicesList) {
                    var curServiceName = element.service.className
                    if (serviceName.contains(curServiceName)) {
                        return true
                    }
                }
            }
            return false
        }
    }
}