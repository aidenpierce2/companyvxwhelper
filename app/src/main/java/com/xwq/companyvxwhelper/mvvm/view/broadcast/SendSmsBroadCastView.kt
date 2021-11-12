package com.xwq.companyvxwhelper.mvvm.view.broadcast

import com.xwq.companyvxwhelper.base.IBaseView
import com.xwq.companyvxwhelper.base.IBroadCastBaseView
import com.xwq.companyvxwhelper.bean.ResponseBean.SendSmsResBean

interface  SendSmsBroadCastView : IBroadCastBaseView {

    fun senSmsSuccess(msg: String?, data: SendSmsResBean?)
    fun sendSmsFail()
}