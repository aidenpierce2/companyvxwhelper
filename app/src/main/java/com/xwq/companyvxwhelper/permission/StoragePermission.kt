package com.xwq.companyvxwhelper.permission

import android.Manifest
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.xwq.companyvxwhelper.base.Enum.PermissionArray
import com.xwq.companyvxwhelper.permission.Base.BasePermission

class StoragePermission : BasePermission() {

    val permissionArray : Array<String> = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE)
    companion object {
        val requestCode : Int = 0xFF shl 1
    }

    override fun requestPermission(activity : Activity) {
        ActivityCompat.requestPermissions(activity, permissionArray, requestCode)
    }


}