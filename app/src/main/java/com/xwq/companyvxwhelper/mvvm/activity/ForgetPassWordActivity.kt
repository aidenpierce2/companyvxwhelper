package com.xwq.companyvxwhelper.mvvm.activity

import android.content.Intent
import android.util.SparseArray
import android.view.LayoutInflater
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.bean.RequestBean.ForgetPassWordReqBean
import com.xwq.companyvxwhelper.bean.RequestBean.SendSmsReqBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.ForgetPasswordBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.NoticeGetVerifyCodeBean
import com.xwq.companyvxwhelper.databinding.ActivityForgetpasswordBinding
import com.xwq.companyvxwhelper.mvvm.model.activity.ForgetPassWordModel
import com.xwq.companyvxwhelper.mvvm.view.activity.ForgetPassWordView
import com.xwq.companyvxwhelper.utils.PackageInfoUtils
import com.xwq.companyvxwhelper.utils.PhoneNumMatcherUtils
import com.xwq.companyvxwhelper.utils.ToastUtil
import com.xwq.companyvxwhelper.utils.VerifyCodeUtils

class ForgetPassWordActivity : BaseActivity<ActivityForgetpasswordBinding, ForgetPassWordView, ForgetPassWordModel>(), ForgetPassWordView {

    val RESULTCODE = 0x02

    var forgetPasswordBean : ForgetPasswordBean? = null

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
        getBinding().setVariable(BR.ForgetPassWordActivity, this@ForgetPassWordActivity)
    }

    override fun initData() {
        if (forgetPasswordBean == null) {
            forgetPasswordBean = ForgetPasswordBean()
        }
        forgetPasswordBean.let {
            it?.backDrawable = resources.getDrawable(R.mipmap.back)
            it?.iconDrawable = resources.getDrawable(R.mipmap.head_icon)
            it?.userTelBean?.hintText = resources.getString(R.string.input_phonenum)
            it?.userTelBean?.inputText = ""
            it?.userTelBean?.showClear = false
            it?.userVerifyCodeBean?.hintText = resources.getString(R.string.input_verify_code)
            it?.userVerifyCodeBean?.inputText = ""
            it?.userVerifyCodeBean?.clickAble = false
            it?.userPassBean?.hintText = resources.getString(R.string.input_password)
            it?.userPassBean?.inputText = ""
            it?.userPassBean?.showClear = false
            it?.userRePassBean?.hintText = resources.getString(R.string.re_input_password)
            it?.userRePassBean?.inputText = ""
            it?.userRePassBean?.showClear = false
            it?.resetPasswordStr = resources.getString(R.string.reset_password)
            it?.versionCodeStr = resources.getString(R.string.version_name) + PackageInfoUtils.getVersionName()

            getBinding().setVariable(BR.ForgetPasswordBean, it)
        }
    }

    override fun initListener() {

    }

    override fun startRequest() {

    }

    override fun needLocation(): Boolean {
        return false
    }

    override fun getSelfModel(): ForgetPassWordModel {
        return ForgetPassWordModel(this@ForgetPassWordActivity)
    }

    fun backToPreActivity() {
        var intent = Intent()
        intent.putExtra("DATA", arrayOf<String?>("false", forgetPasswordBean?.userTelBean?.inputText, ""))
        setResult(RESULTCODE, intent)
        finish()
    }

    private fun noticeGetVertifyCode(noticeGetVerifyCodeBean : NoticeGetVerifyCodeBean) {
        var inputTelePhoneNumber : String? = forgetPasswordBean?.userTelBean?.inputText
        var verifyCodeStr : String? = forgetPasswordBean?.userVerifyCodeBean?.inputText
        var userPasssWord : String? = forgetPasswordBean?.userPassBean?.inputText
        var userRePassWord : String? = forgetPasswordBean?.userRePassBean?.inputText
        if (PhoneNumMatcherUtils.checkIsVaildPhoneNum(inputTelePhoneNumber)) {
            ToastUtil.showToast(R.string.input_correct_phonenum)
            return
        }
        if (VerifyCodeUtils.checkVerifyCode(verifyCodeStr)) {
            ToastUtil.showToast(R.string.input_correct_verify_code)
            return
        }
        if (userPasssWord.isNullOrEmpty() or !userRePassWord.isNullOrEmpty() or (!userPasssWord.equals(userRePassWord))) {
            ToastUtil.showToast(R.string.re_ensure_password)
            return
        }
        getModel().getVerifyCode(SendSmsReqBean(inputTelePhoneNumber!!, System.currentTimeMillis().toString()))
    }

    fun submitNewUserInfo() {
        var inputTelePhoneNumber : String? = forgetPasswordBean?.userTelBean?.inputText
        var verifyCodeStr : String? = forgetPasswordBean?.userVerifyCodeBean?.inputText
        var userPasssWord : String? = forgetPasswordBean?.userPassBean?.inputText
        var userRePassWord : String? = forgetPasswordBean?.userRePassBean?.inputText
        if (PhoneNumMatcherUtils.checkIsVaildPhoneNum(inputTelePhoneNumber)) {
            ToastUtil.showToast(R.string.input_correct_phonenum)
            return
        }
        if (VerifyCodeUtils.checkVerifyCode(verifyCodeStr)) {
            ToastUtil.showToast(R.string.input_correct_verify_code)
            return
        }
        if (userPasssWord.isNullOrEmpty() or !userRePassWord.isNullOrEmpty() or (!userPasssWord.equals(userRePassWord))) {
            ToastUtil.showToast(R.string.re_ensure_password)
            return
        }
        getModel().subForgetPassInfo(ForgetPassWordReqBean(inputTelePhoneNumber!!, verifyCodeStr, userPasssWord))
    }

    override fun getSmsSucc() {
        ToastUtil.showToast(R.string.get_sms_succ)
    }

    override fun getSmsFail() {
        ToastUtil.showToast(R.string.web_err)
    }

    override fun subForgetPassInfoSucc() {
        var intent = Intent()
        intent.putExtra("DATA", arrayOf<String?>("true", forgetPasswordBean?.userTelBean?.inputText, forgetPasswordBean?.userPassBean?.inputText))
        setResult(RESULTCODE, intent)
        finish()
    }

    override fun subForgetPassInfoFail() {
        ToastUtil.showToast(R.string.web_err)
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_forgetpassword
    }
}