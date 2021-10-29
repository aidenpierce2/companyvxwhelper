package com.xwq.companyvxwhelper.mvvm.activity

import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.mvvm.model.activity.ForgetPassWordModel
import com.xwq.companyvxwhelper.mvvm.view.activity.ForgetPassWordView

class ForgetPassWordActivity : BaseActivity<ForgetPassWordView, ForgetPassWordModel>(),ForgetPassWordView {
    override fun setContentViewId(): Int {
        return R.layout.activity_forpasssword
    }

    override fun fullScreenEnable(): Boolean {
        return true
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

    }

    override fun initListener() {

    }

    override fun startRequest() {

    }

    override fun needLocation(): Boolean {
        return false
    }

    override fun needEventBus(): Boolean {
        return false
    }

    override fun getSelfModel(): ForgetPassWordModel {
        return ForgetPassWordModel()
    }
}