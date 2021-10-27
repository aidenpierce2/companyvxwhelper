package com.xwq.companyvxwhelper.mvvm.view.broadcast

import com.xwq.companyvxwhelper.base.IBaseView
import com.xwq.companyvxwhelper.bean.ResponseBean.SendSmsResBean

interface  SendSmsBroadCastView : IBaseView {

    fun senSmsSuccess(msg: String?, data: SendSmsResBean?)
    fun sendSmsFail()
}