package com.xwq.companyvxwhelper.service

import android.app.IntentService
import android.content.Intent
import com.xwq.companyvxwhelper.bean.EventBusMessageTypeBean
import org.greenrobot.eventbus.EventBus
import java.util.*

class TimeCutDownService : IntentService("TimeCutDown") {

    var TAG = TimeCutDownService::class.java.simpleName
    var timer : Timer? = null

    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        var startTime : Long = 0
        var preCutDownTime : Long = 0

        startTime = intent?.getLongExtra("STARTTIME", 1)!!
        preCutDownTime = intent?.getLongExtra("PRECUTDOWNTIME", 1)!!

        cutDownTime(preCutDownTime, startTime)
    }

    @Synchronized fun cutDownTime(preCutDownTime : Long, startTime : Long) {
        if (timer == null) {
            timer = Timer()
        }

        timer?.schedule(object : TimerTask() {
            override fun run() {
                var curLeftOverTime = startTime - 1
                if (curLeftOverTime > 0) {
                    EventBus.getDefault().post(EventBusMessageTypeBean(EventBusMessageTypeBean.EventBusMessageTypeBeanEnum.CUTDOWNTIME).put(TAG, curLeftOverTime))
                } else {
                    EventBus.getDefault().post(EventBusMessageTypeBean(EventBusMessageTypeBean.EventBusMessageTypeBeanEnum.CUTDOWNEND))
                }
            }
        }, 0L, preCutDownTime * 1000L)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}