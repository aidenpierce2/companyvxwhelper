package com.xwq.companyvxwhelper.mvvm.model.activity

import com.xwq.companyvxwhelper.api.Api
import com.xwq.companyvxwhelper.api.Transform.RxTransformer
import com.xwq.companyvxwhelper.api.Urls
import com.xwq.companyvxwhelper.base.BaseModel
import com.xwq.companyvxwhelper.base.BaseNetworkResponse
import com.xwq.companyvxwhelper.base.BaseObserver
import com.xwq.companyvxwhelper.bean.ResponseBean.CheckTokenResBean
import com.xwq.companyvxwhelper.bean.ResponseBean.LoginResBean
import com.xwq.companyvxwhelper.bean.ResponseBean.PublicRsaResponse
import com.xwq.companyvxwhelper.databinding.ActivitySplashBinding
import com.xwq.companyvxwhelper.mvvm.view.activity.SplashView
import retrofit2.http.Url

class SplashModel(splashView: SplashView) : BaseModel<ActivitySplashBinding, SplashView>(splashView) {
    var url : String = ""

    //获取rsa公钥
    fun getPublicRsa() {
        url = Urls.GET_PUBLIC_RSA
        apiService().getPublicRsa(url)
            .compose(RxTransformer.switchSchedulers(this))
            .subscribe(object : BaseObserver<PublicRsaResponse>(curContext) {
                override fun onFailure(e: Throwable?) {
                    curView?.getPubcliRsaFail()
                }

                override fun onRequestStart() {

                }

                override fun onSuccess(msg: String?, data: PublicRsaResponse?) {
                    curView?.getPublicRsaSucc(data)
                }

                override fun onFinally() {

                }

            })
    }

    //检测token的有效性
    fun checkTokenValid(userToken : String) {
        url = Urls.CHECK_TOKEN_VALID
        apiService().checkTokenValid(url, Api.createRequestBody(userToken))
            .compose(RxTransformer.switchSchedulers(this))
            .subscribe(object : BaseObserver<CheckTokenResBean>(curContext) {
                override fun onFailure(e: Throwable?) {
                    curView?.getTokenValidFail()
                }

                override fun onRequestStart() {

                }

                override fun onSuccess(msg: String?, data: CheckTokenResBean?) {
                    curView?.getTokenValidSucc()
                }

                override fun onFinally() {

                }

            })
    }
}