package com.xwq.companyvxwhelper.permission

import com.xwq.companyvxwhelper.base.Enum.PermissionArray
import com.xwq.companyvxwhelper.permission.Base.BasePermission

class PermissionFactory {

    companion object {
        var instance : PermissionFactory = PermissionFactory()
    }

    fun getTargetPermissionGroup(permissionName : PermissionArray) : BasePermission {
        when (permissionName) {
            PermissionArray.SDCARD -> {
                return StoragePermission()
            }
            PermissionArray.MESSAGE -> {
                return SmsPermission()
            }
            PermissionArray.LOCATION -> {
                return LocationPermission()
            }else -> {
                return DefaultPermission()
            }
        }
    }

    fun getPermissionName (requestCode : Int) : PermissionArray {
        when (requestCode) {
            StoragePermission.requestCode -> {
                return PermissionArray.SDCARD
            }
            SmsPermission.requestCode -> {
                return PermissionArray.MESSAGE
            }
            LocationPermission.requestCode -> {
                return PermissionArray.LOCATION
            }else -> {
                return PermissionArray.DEFAULT
            }
        }
    }

}