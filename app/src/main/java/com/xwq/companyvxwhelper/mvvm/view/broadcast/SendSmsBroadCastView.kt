package com.xwq.companyvxwhelper.mvvm.view.broadcast

import com.xwq.companyvxwhelper.base.IBASBaseView
import com.xwq.companyvxwhelper.bean.ResponseBean.SendSmsResBean

interface  SendSmsBroadCastView : IBASBaseView {

    fun senSmsSuccess(msg: String?, data: SendSmsResBean?)
    fun sendSmsFail()
}