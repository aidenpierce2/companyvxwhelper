package com.xwq.companyvxwhelper.service.request

import android.content.Context
import com.xwq.companyvxwhelper.api.Api
import com.xwq.companyvxwhelper.api.Urls
import com.xwq.companyvxwhelper.api.interfaceRequest.RequestService
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.bean.ResponseBean.AliTimeStampResponse
import com.xwq.companyvxwhelper.observer.TBObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RequestHttp {

    // 获取TB接口时间
    var mDisposable : CompositeDisposable? = CompositeDisposable()
    var responseListener : ResponseListener? = null

    fun getTimeStamp(context: Context) : RequestHttp{
        context as BaseActivity<*, *, *>
        var url = Urls.ALI_TIME_STAMP
        Api.createApi()!!.create(RequestService::class.java).getTimeStamp(url)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe{t: Disposable? ->  mDisposable!!.add(t!!)}
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { object : TBObserver(context){
                override fun onSuccess(data: AliTimeStampResponse.Data) {
                    context.hideDefaultLoading()
                    responseListener!!.onSuccess(data)
                }

                override fun onFailure(e: Throwable?) {
                    context.hideDefaultLoading()
                    responseListener!!.onFail()
                }

                override fun onRequestStart() {
                    context.showDefaultLoading()
                }

                override fun onFinally() {
                    context.hideDefaultLoading()
                }

            } }
        return this@RequestHttp
    }


    fun observer(responseListener : ResponseListener) {
        if (responseListener == null) {
            return
        }

        this@RequestHttp.responseListener = responseListener
    }

    interface ResponseListener {
        fun onSuccess(data: AliTimeStampResponse.Data)
        fun onFail()
    }
}