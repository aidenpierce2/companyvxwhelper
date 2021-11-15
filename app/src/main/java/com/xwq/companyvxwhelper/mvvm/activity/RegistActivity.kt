package com.xwq.companyvxwhelper.mvvm.activity

import android.content.Intent
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.SparseArray
import android.view.View
import androidx.appcompat.widget.AppCompatCheckBox
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.bean.Enum.PassWordErrEnum
import com.xwq.companyvxwhelper.bean.RequestBean.RegisterReqBean
import com.xwq.companyvxwhelper.bean.RequestBean.SendSmsReqBean
import com.xwq.companyvxwhelper.bean.ResponseBean.RegisterResBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.RegisteActivityBean
import com.xwq.companyvxwhelper.const.Const.USER_ENUSRE_AUTH
import com.xwq.companyvxwhelper.const.Const.USER_POLICY_CHECKED
import com.xwq.companyvxwhelper.mvvm.model.activity.RegisterModel
import com.xwq.companyvxwhelper.mvvm.view.activity.RegisterView
import com.xwq.companyvxwhelper.service.TimeCutDownService
import com.xwq.companyvxwhelper.utils.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegistActivity  : BaseActivity<RegisterView, RegisterModel>(),RegisterView {

    val RESULTCODE = 0x03
    var registeActivityBean : RegisteActivityBean? = null
    lateinit var ensureAuth : AppCompatCheckBox

    override fun setContentViewId(): Int {
        return R.layout.activity_register
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
        ensureAuth = activity_register_accb_ensure_auth
    }

    override fun initData() {
        if (registeActivityBean == null) {
            registeActivityBean = RegisteActivityBean()
        }
        var preSpannableString = resources.getString(R.string.user_policy)
        var indexArray = indexChooseRange(preSpannableString)
        var spannableString = SpannableString(preSpannableString)
        for (i in 0..indexArray.size() - 1) {
            spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#FF03DAC5")), indexArray.keyAt(i), indexArray.valueAt(i)+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            when (i) {
                0 -> {spannableString.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {

                    }
                },indexArray.keyAt(i),indexArray.valueAt(i), Spanned.SPAN_INCLUSIVE_INCLUSIVE)}
                1 -> {
                    spannableString.setSpan(object : ClickableSpan() {
                        override fun onClick(widget: View) {

                        }
                    },indexArray.keyAt(i),indexArray.valueAt(i), Spanned.SPAN_INCLUSIVE_INCLUSIVE)
                }
            }
        }
        registeActivityBean.let {
            it?.backDrawable = resources.getDrawable(R.mipmap.back)
            it?.iconDrawable = resources.getDrawable(R.mipmap.head_icon)
            it?.userTelBean?.hintText = resources.getString(R.string.input_phonenum)
            it?.userTelBean?.inputText = ""
            it?.userTelBean?.showClear = false
            it?.userVerifyCodeBean?.hintText = resources.getString(R.string.input_verify_code)
            it?.userVerifyCodeBean?.inputText = ""
            it?.userVerifyCodeBean?.clickAble = true
            it?.userPassBean?.hintText = resources.getString(R.string.input_password)
            it?.userPassBean?.inputText = ""
            it?.userPassBean?.showClear = false
            it?.userRePassBean?.hintText = resources.getString(R.string.re_input_password)
            it?.userRePassBean?.inputText = ""
            it?.userRePassBean?.showClear = false
            it?.hasEnsureAuth = SharePreferenceUtil.instance.getData(USER_POLICY_CHECKED, false)
            it?.userPolicy = spannableString
            it?.loginStr = resources.getString(R.string.login)
            it?.versionStr = resources.getString(R.string.version_name) + PackageInfoUtils.getVersionName()
        }

        getBinding().setVariable(BR.RegisteActivityBean, registeActivityBean)
        getBinding().setVariable(BR.RegistActivity, this)
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

    override fun registSucc(data: RegisterResBean) {
        var data : Array<String?> = arrayOfNulls<String>(3)
        data[0] = "true"
        data[1] = registeActivityBean?.userTelBean?.inputText.toString()
        data[2] = registeActivityBean?.userPassBean?.inputText.toString()
        setResult(RESULTCODE, Intent().putExtra("DATA", data))
        this@RegistActivity.finish()
    }

    override fun registFail() {
        ToastUtil.showToast(R.string.web_err)
    }

    override fun getSmsSucc() {

    }

    override fun getSmsFail() {

    }

    override fun showToast(value: String) {

    }

    override fun showLoading() {
        super.showLoading()
    }

    override fun hideLoading() {
        super.hideLoading()
    }

    fun backToPreActivity() {
        var data : Array<String?> = arrayOfNulls<String>(3)
        data[0] = "false"
        data[1] = registeActivityBean?.userTelBean?.inputText.toString()
        data[2] = registeActivityBean?.userPassBean?.inputText.toString()
        setResult(RESULTCODE, Intent().putExtra("DATA", data))
        this@RegistActivity.finish()
    }

    fun ensureAuth() {
        registeActivityBean?.hasEnsureAuth = !registeActivityBean?.hasEnsureAuth!!
        SharePreferenceUtil.instance.setData(USER_POLICY_CHECKED,
            registeActivityBean?.hasEnsureAuth!!
        )
        ensureAuth.isChecked = registeActivityBean?.hasEnsureAuth!!
        if (ensureAuth.isChecked ) {
            ensureAuth.buttonDrawable = resources.getDrawable(R.mipmap.login_checked)
        } else {
            ensureAuth.buttonDrawable = resources.getDrawable(R.mipmap.login_uncheck)
        }
        getBinding().setVariable(BR.RegisteActivityBean, registeActivityBean)
    }

    override fun getSelfModel(): RegisterModel {
        return RegisterModel(this@RegistActivity)
    }

    fun subRegisterUser() {
        var userTelePhone : String? = registeActivityBean?.userTelBean?.inputText
        var verifyCode : String? = registeActivityBean?.userVerifyCodeBean?.inputText
        var userPassWord : String? = registeActivityBean?.userPassBean?.inputText
        var userRePassWord : String? = registeActivityBean?.userRePassBean?.inputText
        if (!PhoneNumMatcherUtils.checkIsVaildPhoneNum(userTelePhone)) {
            ToastUtil.showToast(R.string.input_correct_phonenum)
            return
        }
        if (!VerifyCodeUtils.checkVerifyCode(verifyCode)) {
            ToastUtil.showToast(R.string.input_correct_verify_code)
            return
        }
        if (userPassWord.isNullOrEmpty() || userRePassWord.isNullOrEmpty()) {
            ToastUtil.showToast(R.string.input_password)
            return
        }
        if (!userPassWord.equals(userRePassWord)) {
            ToastUtil.showToast(R.string.re_ensure_password)
            return
        }
        var checkPassWordValid = PassWordUtils.checkPassWordValid(userPassWord)
        when (checkPassWordValid) {
            PassWordErrEnum.EMPTYERR -> {
                ToastUtil.showToast(R.string.input_password)
                return
            }
            PassWordErrEnum.LENGTHERR -> {
                ToastUtil.showToast(R.string.password_length_err)
                return
            }
            PassWordErrEnum.NOCHARACTERERR -> {
                ToastUtil.showToast(R.string.password_no_character_err)
                return
            }
            PassWordErrEnum.NONUMBERERR -> {
                ToastUtil.showToast(R.string.password_no_number_err)
                return
            }
            PassWordErrEnum.NOERR -> {}
        }
        getModel().submitRegister(RegisterReqBean(userTelePhone!!, verifyCode!!, userPassWord, Md5Util.makeSignKey(), DevieTypeUtils.getDeviceTypeValue(),true))
    }

    fun indexChooseRange(preSpannableString : String) : SparseArray<Int>{
        var indexArray  : SparseArray<Int> = SparseArray()
        val starter = "《"
        val ender = "》"
        val authStr = preSpannableString
        var starterMatcher : Matcher = Pattern.compile(starter).matcher(authStr)
        var enderMatcher = Pattern.compile(ender).matcher(authStr)
        while (starterMatcher.find()) {
            var startIndex = starterMatcher.start()
            if (enderMatcher.find()) {
                var endIndex = enderMatcher.start()
                indexArray.put(startIndex, endIndex)
            }
        }
        return indexArray
    }

    fun getSmsVerifyCode() : Boolean{
        var telephoneNumStr : String = registeActivityBean?.userTelBean?.inputText!!
        var timeStamp : String = System.currentTimeMillis().toString()
        if (!PhoneNumMatcherUtils.checkIsVaildPhoneNum(telephoneNumStr)) {
            ToastUtil.showToast(R.string.input_correct_phonenum)
            return false
        }
        if (ServiceUtils.isTargetServiceRunning(TimeCutDownService::class.java.toString())) {
            return false
        }
        getModel().getVerifyCode(SendSmsReqBean(telephoneNumStr, timeStamp))
        return true
    }
}