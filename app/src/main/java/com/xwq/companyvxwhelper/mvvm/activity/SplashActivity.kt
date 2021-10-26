package com.xwq.companyvxwhelper.mvvm.activity

import android.content.Intent
import android.os.Handler
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.bean.RequestBean.SendSmsReqBean
import com.xwq.companyvxwhelper.mvvm.model.activity.SplashModel
import com.xwq.companyvxwhelper.mvvm.view.activity.SplashView


class SplashActivity : BaseActivity<SplashView, SplashModel>() {

    override fun setContentViewId(): Int {
        return R.layout.activity_splash
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
        Handler().postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            this@SplashActivity.finish()
        }, 1000)

        SendSmsReqBean("dsad", "dsa")
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
}