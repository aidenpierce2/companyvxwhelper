package com.xwq.companyvxwhelper.mvvm.view.activity

import com.xwq.companyvxwhelper.base.IBaseView
import com.xwq.companyvxwhelper.bean.ResponseBean.UserInfoResBean

interface SettingView : IBaseView {

    fun getUserInfoSucc(data : UserInfoResBean?)
    fun getUserInfoFail(data : String)
}