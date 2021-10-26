package com.xwq.companyvxwhelper.permission

import android.Manifest
import android.app.Activity
import androidx.core.app.ActivityCompat
import com.xwq.companyvxwhelper.permission.Base.BasePermission

class LocationPermission : BasePermission() {

    val locationPermission : Array<String> = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE)

    companion object {
        val requestCode : Int = 0xFFFF shl 1
    }
    override fun requestPermission(activity: Activity) {
        ActivityCompat.requestPermissions(activity, locationPermission, requestCode)
    }


}