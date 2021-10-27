package com.xwq.companyvxwhelper.mvvm.activity

import android.view.View
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.bean.LoginActivityBean
import com.xwq.companyvxwhelper.mvvm.model.activity.MainModel
import com.xwq.companyvxwhelper.mvvm.view.activity.MainView

class LoginActivity  : BaseActivity<MainView, MainModel>(),MainView {

    lateinit var loginActivityBean : com.xwq.companyvxwhelper.bean.LoginActivityBean

    override fun setContentViewId(): Int {
        return R.layout.activity_login
    }

    override fun fullScreenEnable(): Boolean {
        return  true
    }

    override fun needSetStatusColor(): Boolean {
        return true
    }

    override fun isStatusBlack(): Boolean {
        return true
    }

    override fun initView() {

    }

    override fun initData() {
        if (!this@LoginActivity::loginActivityBean.isInitialized) {
            loginActivityBean = LoginActivityBean()
        }
        getBinding().setVariable(BR.LoginActivityBean, loginActivityBean)

        loginActivityBean.iconDrawable = resources.getDrawable(R.mipmap.head_icon)
    }

    override fun initListener() {

    }

    override fun startRequest() {

    }

    override fun onClick(p0: View?) {

    }

    override fun needLocation(): Boolean {
        return false
    }

    override fun needEventBus(): Boolean {
        return false
    }

    override fun getSelfModel(): MainModel {
        return MainModel(this@LoginActivity)
    }

    override fun showToast(value: String) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}