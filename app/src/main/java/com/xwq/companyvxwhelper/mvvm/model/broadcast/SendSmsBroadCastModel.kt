package com.xwq.companyvxwhelper.mvvm.model.broadcast

import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.api.Transform.RxTransformer
import com.xwq.companyvxwhelper.api.Urls
import com.xwq.companyvxwhelper.base.BaseModel
import com.xwq.companyvxwhelper.base.BaseObserver
import com.xwq.companyvxwhelper.base.BroadCastBaseModel
import com.xwq.companyvxwhelper.bean.RequestBean.SendSmsReqBean
import com.xwq.companyvxwhelper.bean.ResponseBean.SendSmsResBean
import com.xwq.companyvxwhelper.mvvm.view.broadcast.SendSmsBroadCastView

class SendSmsBroadCastModel(curView : SendSmsBroadCastView) : BroadCastBaseModel<SendSmsBroadCastView>(curView) {
    override var curView : SendSmsBroadCastView? = null
    init {
        this@SendSmsBroadCastModel.curView = curView
    }

    fun sendSms(bean : SendSmsReqBean) {
        var url = Urls.SEND_SMS

        apiService().sendSms(url, bean.toMap())
            .compose(RxTransformer.switchSchedulers(this))
            .subscribe(object : BaseObserver<SendSmsResBean>(MyApplication.app) {
                override fun onFailure(e: Throwable?) {
                    curView!!.sendSmsFail()
                }

                override fun onRequestStart() {

                }

                override fun onSuccess(msg: String?, data: SendSmsResBean?) {
                    curView!!.senSmsSuccess(msg, data)
                }

                override fun onFinally() {

                }
            })
    }
}