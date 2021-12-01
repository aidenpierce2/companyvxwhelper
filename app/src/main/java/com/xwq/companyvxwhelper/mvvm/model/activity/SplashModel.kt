package com.xwq.companyvxwhelper.mvvm.model.activity

import com.xwq.companyvxwhelper.api.Api
import com.xwq.companyvxwhelper.api.Transform.RxTransformer
import com.xwq.companyvxwhelper.api.Urls
import com.xwq.companyvxwhelper.base.BaseModel
import com.xwq.companyvxwhelper.base.BaseNetworkResponse
import com.xwq.companyvxwhelper.base.BaseObserver
import com.xwq.companyvxwhelper.bean.ResponseBean.CheckTokenResBean
import com.xwq.companyvxwhelper.bean.ResponseBean.LoginResBean
import com.xwq.companyvxwhelper.databinding.ActivitySplashBinding
import com.xwq.companyvxwhelper.mvvm.view.activity.SplashView

class SplashModel(splashView: SplashView) : BaseModel<ActivitySplashBinding, SplashView>(splashView) {
    var url : String = ""

    //检测token的有效性
    fun checkTokenValid(userToken : String) {
        url = Urls.CHECK_TOKEN_VALID
        apiService().checkTokenValid(url, Api.createRequestBody(userToken))
            .compose(RxTransformer.switchSchedulers(this))
            .subscribe(object : BaseObserver<CheckTokenResBean>(curContext) {
                override fun onFailure(e: Throwable?) {
                    curView?.hideLoading()
                    curView?.getTokenValidFail()
                }

                override fun onRequestStart() {
                    curView?.showLoading()

                }

                override fun onSuccess(msg: String?, data: CheckTokenResBean?) {
                    curView?.hideLoading()
                    curView?.getTokenValidSucc()
                }

                override fun onFinally() {
                    curView?.hideLoading()
                }

            })
    }
}