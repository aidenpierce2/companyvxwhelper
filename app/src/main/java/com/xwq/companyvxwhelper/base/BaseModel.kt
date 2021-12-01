package com.xwq.companyvxwhelper.base

import androidx.viewbinding.ViewBinding
import com.xwq.companyvxwhelper.api.Api
import com.xwq.companyvxwhelper.api.interfaceRequest.RequestService
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import java.lang.RuntimeException

abstract class BaseModel<VB : ViewBinding, T : IBaseView>(value : IBaseView?) {
    open var curView : T? = null
    open var curContext : BaseActivity<VB, IBaseView, BaseModel<VB, IBaseView>>? = null

    init {
        curView = value as T?
        curContext = value as BaseActivity<VB, IBaseView, BaseModel<VB, IBaseView>>
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

    open fun <T> handleResult(): ObservableTransformer<BaseNetworkResponse<T>?, T>? {
        return ObservableTransformer { upstream -> upstream.map(BaseNetworkResponse<T>().data as Function<BaseNetworkResponse<T>?, T>) }
    }


}