package com.xwq.companyvxwhelper.permission

import android.app.Activity
import com.xwq.companyvxwhelper.base.Enum.PermissionArray
import com.xwq.companyvxwhelper.base.Enum.PermissionMode

class PermissionManager {

    companion object {
        var instance : PermissionManager = PermissionManager()
    }

    var binder : MutableMap<PermissionArray, PermissionMode> = mutableMapOf()

    /**
     * 第一个参数 参数组
     * 第二个参数 权限
     */
    fun request(activity: Activity, permissionName: PermissionArray, permissionMode: PermissionMode){
        PermissionFactory.instance.getTargetPermissionGroup(permissionName).requestPermission(
            activity
        )

        binder!!.put(permissionName, permissionMode)
    }

    fun getPermissionName(requestCode: Int) : PermissionArray {
        var permissionName = PermissionFactory.instance.getPermissionName(requestCode)
        return permissionName
    }

    // 判断权限是否必须
    fun checkIsNeeded(requestCode : Int) : PermissionMode{
        var permissionName = PermissionFactory.instance.getPermissionName(requestCode)
        return binder.get(permissionName)!!
    }

    fun notify(activity: Activity,requestCode: Int,isSuccess : Boolean) {
        if (binder == null || binder.isEmpty()) {
            return
        }

        var permissionName = PermissionFactory.instance.getPermissionName(requestCode)
        var get = binder.get(permissionName)
        if (isSuccess) {
            return
        } else {
            when (get) {
                PermissionMode.REQUIRED -> {
                    request(activity, permissionName, get)
                }
                PermissionMode.OPTIONAL -> {
                    //啥也不干
                }
            }
        }
    }

}