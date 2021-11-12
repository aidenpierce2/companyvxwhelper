package com.xwq.companyvxwhelper.mvvm.activity

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.widget.AppCompatTextView
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.bean.Enum.EncryOrDecryEnum
import com.xwq.companyvxwhelper.bean.Enum.PassWordErrEnum
import com.xwq.companyvxwhelper.mvvm.model.activity.LoginModel
import com.xwq.companyvxwhelper.mvvm.view.activity.LoginView
import com.xwq.companyvxwhelper.widget.UserTelOrPassInputEditView
import kotlinx.android.synthetic.main.activity_login.*
import com.xwq.companyvxwhelper.bean.dataBindingBean.LoginActivityBean
import com.xwq.companyvxwhelper.bean.RequestBean.LoginReqBean
import com.xwq.companyvxwhelper.bean.ResponseBean.LoginResBean
import com.xwq.companyvxwhelper.const.Const
import com.xwq.companyvxwhelper.const.Const.REMEMBER_PASS
import com.xwq.companyvxwhelper.utils.*
import com.xwq.companyvxwhelper.utils.DevieTypeUtils.Companion.getDeviceTypeValue
import com.xwq.companyvxwhelper.utils.PackageInfoUtils.Companion.getVerisonCode
import com.xwq.companyvxwhelper.utils.PackageInfoUtils.Companion.getVersionName
import com.xwq.companyvxwhelper.utils.SignKeyUtils.Companion.encryOrDecryValue
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class LoginActivity : BaseActivity<LoginView, LoginModel>(),LoginView {

    lateinit var userPhoneNumUTOIE : UserTelOrPassInputEditView
    lateinit var userPasswordUTOIE : UserTelOrPassInputEditView
    lateinit var forgetPasswordACTV : AppCompatTextView
    lateinit var versionCodeACTV : AppCompatTextView
    var loginActivityBean : LoginActivityBean?  =null
    var checkStatus : Boolean = false
    var registLauncher : ActivityResultLauncher<String>? = null
    var forgetLauncher : ActivityResultLauncher<String>? = null

    override fun setContentViewId(): Int {
        return R.layout.activity_login
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
        userPhoneNumUTOIE = activity_login_utpvev_phonenum
        userPasswordUTOIE = activity_login_utvcev_phonenum
        forgetPasswordACTV = activity_login_actv_forpassword
        versionCodeACTV = activity_login_actv_versioncode
    }

    override fun initData() {
        registLauncher = registerForActivityResult(RegistContract(), ActivityResultCallback {
            if (it == null || it.size != 3) {
                return@ActivityResultCallback;
            } else {
                var result = it[0]
                loginActivityBean?.userTelBean?.inputText = it[1]!!
                loginActivityBean?.userPassBean?.inputText = it[2]!!
                if (result.equals("true")) {
                    getSelfModel().login(createLoginReqBean(it[1]!!, it[2]!!))
                }
            }
        })
        forgetLauncher = registerForActivityResult(ForgetContract(), ActivityResultCallback {
            if (it == null || it.size != 3) {
                return@ActivityResultCallback;
            } else {
                var result = it[0]
                loginActivityBean?.userTelBean?.inputText = it[1]!!
                loginActivityBean?.userPassBean?.inputText = it[2]!!
                if (result.equals("true")) {
                    getSelfModel().login(createLoginReqBean(it[1]!!, it[2]!!))
                }
            }
        })

        checkStatus = SharePreferenceUtil.instance.getData(REMEMBER_PASS, false)
        loginActivityBean = LoginActivityBean()
        var userPhoneNum = SharePreferenceUtil.instance.getData(Const.USER_PHONENUM)
        loginActivityBean.let {
            it?.iconDrawable = resources.getDrawable(R.mipmap.head_icon)
            it?.userTelBean?.hintText = resources.getString(R.string.input_phonenum)
            it?.userTelBean?.inputText = userPhoneNum
            it?.userTelBean?.showClear = false
            it?.userPassBean?.hintText = resources.getString(R.string.input_password)
            it?.userPassBean?.inputText = ""
            it?.userPassBean?.showClear = false
            if (checkStatus) {
                it?.checkDrawable = resources.getDrawable(R.mipmap.login_checked)
            } else {
                it?.checkDrawable = resources.getDrawable(R.mipmap.login_uncheck)
            }
            it?.checkStatusStr = resources.getString(R.string.remember_password)
            it?.loginStr = resources.getString(R.string.login)
            it?.forgetStr = resources.getString(R.string.forget_password)
            it?.registStr = resources.getString(R.string.register_now)
            it?.versionStr = resources.getString(R.string.version_name) + getVersionName()
        }
        getBinding().setVariable(BR.LoginActivityBean, loginActivityBean)
        getBinding().setVariable(BR.LoginActivity, this)

    }

    override fun initListener() {

    }

    override fun startRequest() {

    }

    override fun needLocation(): Boolean {
        return false
    }

    override fun getSelfModel(): LoginModel {
        return LoginModel(this@LoginActivity)
    }

    fun switchStatus() {
        checkStatus = !checkStatus
        SharePreferenceUtil.instance.setData(REMEMBER_PASS, checkStatus)
        if (checkStatus) {
            loginActivityBean?.checkDrawable = resources.getDrawable(R.mipmap.login_checked)
        } else {
            loginActivityBean?.checkDrawable = resources.getDrawable(R.mipmap.login_uncheck)
        }
        getBinding().setVariable(BR.LoginActivityBean, loginActivityBean)
    }

    fun registNow() {
        registLauncher?.launch("")
    }

    fun forgetPassword() {
        forgetLauncher?.launch("")
    }

    fun createLoginReqBean(preTelePhone : String, prePassWord : String) : LoginReqBean{
        var loginReqBean : LoginReqBean = LoginReqBean()
        val tempSignKey = Md5Util.makeSignKey()
        loginReqBean.encryptUserPhone = encryOrDecryValue(preTelePhone, EncryOrDecryEnum.ENCRYPTION, tempSignKey)
        loginReqBean.encrypyUserPassWord = encryOrDecryValue(prePassWord, EncryOrDecryEnum.ENCRYPTION, tempSignKey)
        loginReqBean.signKey = tempSignKey
        loginReqBean.loginType = getDeviceTypeValue()
        loginReqBean.userDefaltSignKey = true
        return loginReqBean
    }

    fun requestLogin() {
        var preTelePhone = loginActivityBean?.userTelBean?.inputText
        var prePassWord = loginActivityBean?.userPassBean?.inputText
        if (!PhoneNumMatcherUtils.checkIsVaildPhoneNum(preTelePhone)) {
            ToastUtil.showToast(R.string.input_correct_phonenum)
            return
        }
        var checkPassWordValid = PassWordUtils.checkPassWordValid(prePassWord)
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
        getSelfModel().login(createLoginReqBean(preTelePhone!!, prePassWord!!))
    }

    inner class ForgetContract : ActivityResultContract<String, Array<String>>() {
        override fun createIntent(context: Context, input: String?): Intent {
            return Intent().setClass(this@LoginActivity, ForgetPassWordActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Array<String>? {
            return intent?.getStringArrayExtra("DATA")
        }

    }

    inner class RegistContract : ActivityResultContract<String, Array<String>>() {
        override fun createIntent(context: Context, input: String?): Intent {
            return Intent().setClass(this@LoginActivity, RegistActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Array<String>? {
            return intent?.getStringArrayExtra("DATA")
        }
    }

    override fun loginSucc(loginResBean: LoginResBean?) {

    }

    override fun loginFail() {

    }

}