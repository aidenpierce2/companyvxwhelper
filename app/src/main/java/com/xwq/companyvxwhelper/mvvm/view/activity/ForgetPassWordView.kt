package com.xwq.companyvxwhelper.mvvm.view.activity

import com.xwq.companyvxwhelper.base.IBaseView

interface ForgetPassWordView : IBaseView {

    fun getSmsSucc()
    fun getSmsFail()

    fun subForgetPassInfoSucc()
    fun subForgetPassInfoFail()
}