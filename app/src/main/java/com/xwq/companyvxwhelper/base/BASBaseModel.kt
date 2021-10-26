package com.xwq.companyvxwhelper.base

import com.xwq.companyvxwhelper.api.Api
import com.xwq.companyvxwhelper.api.interfaceRequest.RequestService
import io.reactivex.disposables.CompositeDisposable
import java.lang.RuntimeException

abstract class BASBaseModel<T : IBASBaseView>(value : IBASBaseView?) {
    open var curView : T? = null

    init {
        curView = value as T?
    }

    var mDisposable : CompositeDisposable = CompositeDisposable()
    /**
     * 初始化网络请求
     */
    fun apiService() : RequestService {
        return Api.createApi()!!.create(RequestService::class.java)
    }

    /**
     * 取消网络请求
     */
    fun onDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose()
            mDisposable.clear()
        }
    }


}