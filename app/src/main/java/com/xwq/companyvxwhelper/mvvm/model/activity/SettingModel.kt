package com.xwq.companyvxwhelper.mvvm.model.activity

import com.xwq.companyvxwhelper.api.Transform.RxTransformer
import com.xwq.companyvxwhelper.api.Urls.Companion.GET_USER_INFO
import com.xwq.companyvxwhelper.base.BaseModel
import com.xwq.companyvxwhelper.base.BaseObserver
import com.xwq.companyvxwhelper.bean.ResponseBean.UserInfoResBean
import com.xwq.companyvxwhelper.mvvm.view.activity.SettingView
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class SettingModel(setingView: SettingView) : BaseModel<SettingView>(setingView) {
    var url : String = ""

    fun getUserAccountInfo() {
        url = GET_USER_INFO
        apiService().getUserInfo(url).
            compose(RxTransformer.switchSchedulers(this))
            .subscribe(object : BaseObserver<UserInfoResBean>(curContext) {
                override fun onFailure(e: Throwable?) {
                    curView?.hideLoading()
                }

                override fun onRequestStart() {
                    curView?.showLoading()
                }

                override fun onSuccess(msg: String?, data: UserInfoResBean?) {
                    curView?.hideLoading()
                    curView?.getUserInfoSucc(data)
                }

                override fun onFinally() {
                    curView?.hideLoading()
                }

            })
    }

}