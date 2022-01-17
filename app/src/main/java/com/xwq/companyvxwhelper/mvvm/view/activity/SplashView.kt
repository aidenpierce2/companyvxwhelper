package com.xwq.companyvxwhelper.mvvm.view.activity

import com.xwq.companyvxwhelper.base.IBaseView
import com.xwq.companyvxwhelper.bean.ResponseBean.PublicRsaResponse

interface SplashView : IBaseView {

    fun getPublicRsaSucc(data : PublicRsaResponse?)
    fun getPubcliRsaFail()

    fun getTokenValidSucc()
    fun getTokenValidFail()
}