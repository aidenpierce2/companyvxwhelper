package com.xwq.companyvxwhelper.service

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.os.Parcelable
import com.xwq.companyvxwhelper.bean.dataBindingBean.EventBusMessageTypeBean
import com.xwq.companyvxwhelper.const.Const.GET_VERIFYCODE_START_TIME
import com.xwq.companyvxwhelper.utils.LogUtil
import com.xwq.companyvxwhelper.utils.SharePreferenceUtil
import com.xwq.companyvxwhelper.widget.UserInputVerifyCodeEditView
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class TimeCutDownService : Service() {

    var TAG = TimeCutDownService::class.java.simpleName
    var timer : Timer? = null
    var observer : UserInputVerifyCodeEditView.ParcelabelObserver? = null
    var observable : Observable<String>? = null
    var emitter: ObservableEmitter<String>? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return Binder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var preCutDownTime : Int = 0
        var isInit : Boolean = intent?.getBooleanExtra("ISINIT", false)!!
        preCutDownTime = intent?.getIntExtra("PRECUTDOWNTIME", 1)!!
        observer = intent?.getParcelableExtra("OBSERVER")!!
        initObservable()
        var preStartTime : Long = SharePreferenceUtil.instance.getData(GET_VERIFYCODE_START_TIME, 0L)
        var curTime : Long = System.currentTimeMillis()
        if (preStartTime <= 0 && !isInit) {
            SharePreferenceUtil.instance.setData(GET_VERIFYCODE_START_TIME, curTime)
            cutDownTime(60, preCutDownTime)
        } else {
            if (isInit) {
                if (curTime - preCutDownTime < 60) {
                    cutDownTime(getLeftOverTime(curTime, preStartTime), preCutDownTime)
                } else {
                    stopSelf()
                    this@TimeCutDownService.emitter?.onNext("0")
                }
            } else {
                if (curTime < preStartTime) {

                } else  if (curTime - preCutDownTime < 60) {
                    cutDownTime(getLeftOverTime(curTime, preStartTime), preCutDownTime)
                } else {
                    SharePreferenceUtil.instance.setData(GET_VERIFYCODE_START_TIME, curTime)
                    cutDownTime(60, preCutDownTime)
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun initObservable() {
        observable = Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                this@TimeCutDownService.emitter = emitter
            }
        })
        observable?.observeOn(Schedulers.io());
        observable?.subscribeOn(AndroidSchedulers.mainThread());
        observer?.let { observable?.subscribe(it)}
    }

    fun getLeftOverTime(curTime: Long, startTime : Long) : Long {
        return (curTime - startTime) / 1000
    }

    @Synchronized fun cutDownTime(startTime : Long, preCutDownTime : Int) {
        if (timer == null ) {
            timer = Timer()
        }

        var leftOverTime : Long = startTime
        Thread().run {
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    LogUtil.log(TAG, "run execute!")
                    leftOverTime -= preCutDownTime
                    if (leftOverTime > 0) {
                        this@TimeCutDownService.emitter?.onNext(leftOverTime.toString())
                    } else {
                        timer?.cancel()
                        timer = null
                        stopSelf()
                        SharePreferenceUtil.instance.setData(GET_VERIFYCODE_START_TIME, System.currentTimeMillis())
                        this@TimeCutDownService.emitter?.onNext(leftOverTime.toString())
                    }
                }
            }, 0, 1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.log(TAG, "onDestroy execute!")
        timer?.cancel()
    }

}