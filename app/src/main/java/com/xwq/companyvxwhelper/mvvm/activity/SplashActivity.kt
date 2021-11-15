package com.xwq.companyvxwhelper.mvvm.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.text.TextUtils
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.bean.RequestBean.SendSmsReqBean
import com.xwq.companyvxwhelper.const.Const
import com.xwq.companyvxwhelper.mvvm.model.activity.SplashModel
import com.xwq.companyvxwhelper.mvvm.view.activity.SplashView
import com.xwq.companyvxwhelper.utils.SharePreferenceUtil
import com.xwq.companyvxwhelper.utils.ToastUtil


class SplashActivity : BaseActivity<SplashView, SplashModel>(), SplashView{

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
        var token = SharePreferenceUtil.instance.getData(Const.USER_TOKEN)
        if (token.isNullOrEmpty()) {
            startActivity(Intent().setClass(this@SplashActivity, LoginActivity::class.java))
        } else {
            getModel().checkTokenValid(token)
        }

    }

    override fun initListener() {

    }

    override fun startRequest() {

    }

    override fun needLocation(): Boolean {
        return false
    }

    override fun getTokenValidSucc() {
        startActivity(Intent().setClass(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    override fun getTokenValidFail() {
        ToastUtil.showToast(resources.getString(R.string.web_err))
        startActivity(Intent().setClass(this@SplashActivity, LoginActivity::class.java))
        finish()
    }

    override fun showToast(value: String) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun getSelfModel(): SplashModel {
        return SplashModel(this@SplashActivity)
    }

}