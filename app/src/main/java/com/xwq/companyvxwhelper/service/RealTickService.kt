package com.xwq.companyvxwhelper.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class RealTickService : Service() {

    companion object {
        val TAG : String = RealTickService::class.java.simpleName.toString()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return Binder()
    }

}