package com.xwq.companyvxwhelper.service

import android.app.IntentService
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import com.xwq.companyvxwhelper.bean.dataBindingBean.EventBusMessageTypeBean
import com.xwq.companyvxwhelper.const.Const.GET_VERIFYCODE_START_TIME
import com.xwq.companyvxwhelper.utils.SharePreferenceUtil
import com.xwq.companyvxwhelper.widget.UserInputVerifyCodeEditView
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class TimeCutDownService : IntentService("TimeCutDown") {

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

    override fun onHandleIntent(intent: Intent?) {
        initObservable()
        var preCutDownTime : Int = 0
        preCutDownTime = intent?.getIntExtra("PRECUTDOWNTIME", 1)!!
        var preStartTime : Long = SharePreferenceUtil.instance.getData(GET_VERIFYCODE_START_TIME, 0L)
        var curTime : Long = System.currentTimeMillis()
        if (preStartTime <= 0) {
            SharePreferenceUtil.instance.setData(GET_VERIFYCODE_START_TIME, curTime)
            cutDownTime(60, preCutDownTime)
        } else {
            if (curTime < preStartTime) {
                return
            } else  if (curTime - preCutDownTime < 60) {
                cutDownTime(getLeftOverTime(curTime, preStartTime), preCutDownTime)
            } else {
                SharePreferenceUtil.instance.setData(GET_VERIFYCODE_START_TIME, curTime)
                cutDownTime(60, preCutDownTime)
            }
        }
    }

    fun initObservable() {
        observable = Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                this@TimeCutDownService.emitter = emitter
            }
        })
        observable?.observeOn(Schedulers.io());
        observable?.subscribeOn(AndroidSchedulers.mainThread());
        observable?.subscribe(observer);
    }

    fun getLeftOverTime(curTime: Long, startTime : Long) : Long {
        return (curTime - startTime) / 1000
    }

    @Synchronized fun cutDownTime(startTime : Long, preCutDownTime : Int) {
        if (timer == null) {
            timer = Timer()
        }

        timer?.schedule(object : TimerTask() {
            override fun run() {
                var curLeftOverTime = startTime - preCutDownTime
                if (curLeftOverTime > 0) {
                    this@TimeCutDownService.emitter?.onNext(curLeftOverTime.toString())
                } else {
                    SharePreferenceUtil.instance.setData(GET_VERIFYCODE_START_TIME, System.currentTimeMillis())
                    this@TimeCutDownService.emitter?.onNext(curLeftOverTime.toString())
                }
            }
        }, 0L, 1000L)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}