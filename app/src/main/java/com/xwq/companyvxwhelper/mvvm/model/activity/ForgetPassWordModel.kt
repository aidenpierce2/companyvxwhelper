package com.xwq.companyvxwhelper.mvvm.model.activity

import com.xwq.companyvxwhelper.api.Api
import com.xwq.companyvxwhelper.api.Transform.RxTransformer
import com.xwq.companyvxwhelper.api.Urls.Companion.FORGET_PASSWORD
import com.xwq.companyvxwhelper.api.Urls.Companion.GET_ATTESTATION_MSG
import com.xwq.companyvxwhelper.base.BaseModel
import com.xwq.companyvxwhelper.base.BaseObserver
import com.xwq.companyvxwhelper.bean.RequestBean.ForgetPassWordReqBean
import com.xwq.companyvxwhelper.bean.RequestBean.SendSmsReqBean
import com.xwq.companyvxwhelper.bean.ResponseBean.ForgetPassWordResBean
import com.xwq.companyvxwhelper.bean.ResponseBean.SendSmsResBean
import com.xwq.companyvxwhelper.mvvm.view.activity.ForgetPassWordView
import io.reactivex.schedulers.Schedulers

class ForgetPassWordModel(baseView :  ForgetPassWordView) : BaseModel<ForgetPassWordView>(baseView) {
    var  url = ""

    fun getVerifyCode(sendSmsReqBean: SendSmsReqBean) {
        url = GET_ATTESTATION_MSG
        apiService().sendSms(url, sendSmsReqBean.toMap())
            .compose(RxTransformer.switchSchedulers(this))
            .subscribe(object : BaseObserver<SendSmsResBean>(curContext) {
                override fun onFailure(e: Throwable?) {
                    curView?.hideLoading()
                    curView?.getSmsFail()
                }

                override fun onRequestStart() {
                    curView?.showLoading()
                }

                override fun onSuccess(msg: String?, data: SendSmsResBean?) {
                    curView?.hideLoading()
                    curView?.getSmsSucc()
                }

                override fun onFinally() {
                    curView?.hideLoading()
                }
            })
    }

    fun subForgetPassInfo(forgetPassWordReqBean : ForgetPassWordReqBean) {
        url = FORGET_PASSWORD
        apiService().forgetPassWord(url, forgetPassWordReqBean.toMap())
            .compose(RxTransformer.switchSchedulers(this))
            .subscribe(object : BaseObserver<ForgetPassWordResBean>(curContext) {
                override fun onFailure(e: Throwable?) {
                    curView?.hideLoading()
                    curView?.subForgetPassInfoFail()
                }

                override fun onRequestStart() {
                    curView?.showLoading()
                }

                override fun onSuccess(msg: String?, data: ForgetPassWordResBean?) {
                    curView?.hideLoading()
                    curView?.subForgetPassInfoSucc()
                }

                override fun onFinally() {
                    curView?.hideLoading()
                }

            })
    }
}