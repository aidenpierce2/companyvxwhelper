package com.xwq.companyvxwhelper.mvvm.model.activity

import com.xwq.companyvxwhelper.api.Transform.RxTransformer
import com.xwq.companyvxwhelper.api.Urls
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.base.BaseModel
import com.xwq.companyvxwhelper.base.BaseObserver
import com.xwq.companyvxwhelper.base.IBaseView
import com.xwq.companyvxwhelper.bean.RequestBean.LoginReqBean
import com.xwq.companyvxwhelper.bean.ResponseBean.LoginResBean
import com.xwq.companyvxwhelper.mvvm.view.activity.ForgetPassWordView
import com.xwq.companyvxwhelper.mvvm.view.activity.LoginView

class LoginModel(baseView :  LoginView) : BaseModel<LoginView>(baseView) {
    var url : String = ""

    fun login(loginReqBean : LoginReqBean) {
        url = Urls.SUB_USER_LOGIN
        apiService().login(url, loginReqBean.toMap())
            .compose(RxTransformer.switchSchedulers(this))
            .subscribe(object : BaseObserver<LoginResBean>(curContext) {
                override fun onFailure(e: Throwable?) {
                    curView?.hideLoading()
                }

                override fun onRequestStart() {
                    curView?.showLoading()
                }

                override fun onSuccess(msg: String?, data: LoginResBean?) {
                    curView?.hideLoading()
                    curView?.loginSucc(data)
                }

                override fun onFinally() {
                    curView?.hideLoading()
                }

            })
    }
}