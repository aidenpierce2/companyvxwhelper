package com.xwq.companyvxwhelper.mvvm.model.activity

import com.xwq.companyvxwhelper.api.Transform.RxTransformer
import com.xwq.companyvxwhelper.api.Urls
import com.xwq.companyvxwhelper.api.Urls.Companion.SUB_USER_REGISTER
import com.xwq.companyvxwhelper.base.BaseModel
import com.xwq.companyvxwhelper.base.BaseObserver
import com.xwq.companyvxwhelper.bean.RequestBean.RegisterReqBean
import com.xwq.companyvxwhelper.bean.RequestBean.SendSmsReqBean
import com.xwq.companyvxwhelper.bean.ResponseBean.RegisterResBean
import com.xwq.companyvxwhelper.bean.ResponseBean.SendSmsResBean
import com.xwq.companyvxwhelper.databinding.ActivityRegisterBinding
import com.xwq.companyvxwhelper.mvvm.view.activity.RegisterView

class RegisterModel(baseView : RegisterView) : BaseModel<ActivityRegisterBinding, RegisterView>(baseView) {
    var url = ""

    fun getVerifyCode(sendSmsReqBean: SendSmsReqBean) {
        url = Urls.GET_ATTESTATION_MSG
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

    fun submitRegister(registerReqBean : RegisterReqBean) {
        url = SUB_USER_REGISTER
        apiService().register(url, registerReqBean.toMap())
            .compose(RxTransformer.switchSchedulers(this))
            .subscribe(object : BaseObserver<RegisterResBean>(curContext) {
                override fun onFailure(e: Throwable?) {
                    curView?.hideLoading()
                    curView?.registFail()
                }

                override fun onRequestStart() {
                    curView?.showLoading()
                }

                override fun onSuccess(msg: String?, data: RegisterResBean?) {
                    curView?.hideLoading()
                    curView?.registSucc(data!!)
                }

                override fun onFinally() {
                    curView?.hideLoading()
                }

            })
    }
}