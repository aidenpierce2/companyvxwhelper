package com.xwq.companyvxwhelper.mvvm.view.activity

import com.xwq.companyvxwhelper.base.IBaseView
import com.xwq.companyvxwhelper.bean.ResponseBean.LoginResBean

interface LoginView : IBaseView {

    fun loginSucc(loginResBean: LoginResBean?)
    fun loginFail()
}