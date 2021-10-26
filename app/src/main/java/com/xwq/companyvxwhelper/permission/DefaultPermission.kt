package com.xwq.companyvxwhelper.permission

import android.app.Activity
import com.xwq.companyvxwhelper.permission.Base.BasePermission

class DefaultPermission : BasePermission() {

    override fun requestPermission(activity: Activity) {
        //啥活都不干
    }
}