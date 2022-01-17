package com.xwq.companyvxwhelper.mvvm.activity

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.bean.ResponseBean.PublicRsaResponse
import com.xwq.companyvxwhelper.const.Const
import com.xwq.companyvxwhelper.databinding.ActivityBaseSettingBinding
import com.xwq.companyvxwhelper.databinding.ActivitySplashBinding
import com.xwq.companyvxwhelper.mvvm.model.activity.SplashModel
import com.xwq.companyvxwhelper.mvvm.view.activity.SplashView
import com.xwq.companyvxwhelper.utils.LogUtil
import com.xwq.companyvxwhelper.utils.RsaAndAesUtils
import com.xwq.companyvxwhelper.utils.SharePreferenceUtil
import com.xwq.companyvxwhelper.utils.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class SplashActivity : BaseActivity<ActivitySplashBinding, SplashView, SplashModel>(), SplashView {

    lateinit var channel : Channel<Boolean>

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
        channel = Channel { }
        getModel().getPublicRsa()
        var token = SharePreferenceUtil.instance.getData(Const.USER_TOKEN)
        if (token.isNullOrEmpty()) {
            if (!channel.isClosedForReceive) {
                channel.close()
                startActivity(Intent().setClass(this@SplashActivity, LoginActivity::class.java))
            }
            finish()
        } else {
            if (!channel.isClosedForReceive) {
                getModel().checkTokenValid(token)
            }
        }

    }

    override fun initListener() {

    }

    override fun startRequest() {

    }

    override fun needLocation(): Boolean {
        return false
    }

    override fun getPublicRsaSucc(data : PublicRsaResponse?) {
        if (data != null) {
            SharePreferenceUtil.instance.setData(Const.PUBLIC_RSA, data.publicRsa)
            SharePreferenceUtil.instance.setData(Const.KEY_UUID, data.keyUUID)
        }
        CoroutineScope(Dispatchers.Main).launch {
            if (!channel.isClosedForSend) {
                channel.send(true)
            }
        }
    }

    override fun getPubcliRsaFail() {
        channel.close()
        ToastUtil.showToast(resources.getString(R.string.web_err))
        exit()
    }

    override fun getTokenValidSucc() {
        startActivity(Intent().setClass(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    override fun getTokenValidFail() {
        ToastUtil.showToast(resources.getString(R.string.web_err))
        exit()
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


    override fun getContentViewId(): Int {
        return R.layout.activity_splash
    }

}