package com.xwq.companyvxwhelper.permission.Base

import android.app.Activity

abstract class BasePermission {

    //请求
    abstract fun requestPermission(activity : Activity)

}