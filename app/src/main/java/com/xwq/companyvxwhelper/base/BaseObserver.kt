package com.xwq.companyvxwhelper.base

import android.accounts.NetworkErrorException
import android.content.Context
import com.google.gson.JsonSyntaxException
import com.xwq.companyvxwhelper.BuildConfig
import com.xwq.companyvxwhelper.event.LoginInvalidEvent
import com.xwq.companyvxwhelper.utils.LogUtil
import com.xwq.companyvxwhelper.utils.ToastUtil.showToast
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Created by xwq on 2021 06 03
 */
abstract class BaseObserver<T>(private val mContext: Context?) : Observer<BaseNetworkResponse<T>> {
    override fun onSubscribe(d: Disposable) {
        onRequestStart()
    }

    override fun onNext(baseNetworkResponse: BaseNetworkResponse<T>) {
        if (!baseNetworkResponse.isError) {
            try {
                onSuccess(
                    baseNetworkResponse.msg,
                    baseNetworkResponse.data
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            try {
                if (baseNetworkResponse.code == -2) {
                    EventBus.getDefault().post(LoginInvalidEvent())
                }
                onCodeError(baseNetworkResponse)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onError(e: Throwable) {
        if (null != e.message) {
            LogUtil.log("error", "onError: " + e.message)
        }
        try {
            if (e is ConnectException
                || e is TimeoutException
                || e is NetworkErrorException
                || e is UnknownHostException
            ) {
                if (null != mContext) {
                    showToast("网络异常，请稍后重试")
                }
            }
            if (e is JsonSyntaxException) {
                if (BuildConfig.DEBUG) {
                    showToast("Gson解析出错")
                }
            }
            onFailure(e)
        } catch (e1: Exception) {
            e1.printStackTrace()
        } finally {
            onFinally()
        }
    }

    override fun onComplete() {
        onFinally()
    }

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    @Throws(Exception::class)
    protected fun onCodeError(t: BaseNetworkResponse<T>?) {
        // xwq add 自定义显示 不要提示给用户
        //ToastUtil.INSTANCE.showToast(t.getMsg());
    }

    /**
     * 返回失败
     *
     * @param e
     * @throws Exception
     */
    @Throws(Exception::class)
    protected abstract fun onFailure(e: Throwable?)
    protected abstract fun onRequestStart()
    protected abstract fun onSuccess(msg : String?, data : T?)
    protected abstract fun onFinally()
}