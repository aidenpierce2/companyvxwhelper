package com.xwq.companyvxwhelper.permission

import android.Manifest
import android.app.Activity
import androidx.core.app.ActivityCompat
import com.xwq.companyvxwhelper.base.Enum.PermissionArray
import com.xwq.companyvxwhelper.permission.Base.BasePermission

class SmsPermission : BasePermission() {

    val permissionArray : Array<String> = arrayOf(Manifest.permission.SEND_SMS,
    Manifest.permission.RECEIVE_SMS,
    Manifest.permission.READ_SMS)

    companion object {
        val requestCode : Int = 0xFFF shl 1
    }

    override fun requestPermission(activity: Activity) {
        ActivityCompat.requestPermissions(activity, permissionArray, requestCode)
    }
}