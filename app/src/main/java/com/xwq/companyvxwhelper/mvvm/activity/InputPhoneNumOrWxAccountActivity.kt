package com.xwq.companyvxwhelper.mvvm.activity

import android.content.Intent
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.bean.DataBaseBean.UserLoginAccountDBBean
import com.xwq.companyvxwhelper.bean.DataBaseBean.UserLoginAccountDBBeanDao
import com.xwq.companyvxwhelper.bean.Enum.InputInformationTypeEnum
import com.xwq.companyvxwhelper.bean.Enum.InputSettingEnum
import com.xwq.companyvxwhelper.bean.Enum.MultiAdapterEnum
import com.xwq.companyvxwhelper.bean.SettingAdapterBean
import com.xwq.companyvxwhelper.const.Const
import com.xwq.companyvxwhelper.databaseCenter.DatabaseManager
import com.xwq.companyvxwhelper.mvvm.adapter.InputPhoneAdapter
import com.xwq.companyvxwhelper.mvvm.dialog.IosAlertDialog
import com.xwq.companyvxwhelper.mvvm.model.activity.InputPhoneNumModel
import com.xwq.companyvxwhelper.mvvm.view.activity.InputPhoneNumView
import com.xwq.companyvxwhelper.utils.PhoneNumMatcherUtils
import com.xwq.companyvxwhelper.utils.SharePreferenceUtil
import com.xwq.companyvxwhelper.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_base_setting.*
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class InputPhoneNumOrWxAccountActivity : BaseActivity<InputPhoneNumView, InputPhoneNumModel>(),InputPhoneNumView {

    lateinit var backACIV : AppCompatImageView
    lateinit var titleACTV : AppCompatTextView
    lateinit var rightACIV : AppCompatTextView
    lateinit var mainRCY : RecyclerView
    var inputPhoneAdapter : InputPhoneAdapter? = null
    var dataList : ArrayList<SettingAdapterBean> = arrayListOf()
    var inputSettingEnum : InputSettingEnum? = null

    override fun setContentViewId(): Int {
        return R.layout.activity_base_setting
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
        backACIV = activity_base_setting_aciv_back
        titleACTV = activity_base_setting_actv_title
        rightACIV = activity_base_setting_actv_right
        mainRCY = activity_base_setting_rcy_main
    }

    override fun initData() {

        fitSystemWindow()
        titleACTV.setText(resources.getText(R.string.set_login_phonenum))

        inputSettingEnum = intent.getSerializableExtra("TYPE") as InputSettingEnum?

        inputPhoneAdapter = InputPhoneAdapter(this@InputPhoneNumOrWxAccountActivity)
        mainRCY.layoutManager = LinearLayoutManager(
            this@InputPhoneNumOrWxAccountActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        when (inputSettingEnum) {
            InputSettingEnum.PHONENUM -> {
                dataList.clear()
                dataList.add(
                    SettingAdapterBean(
                        MultiAdapterEnum.SEPARATOR,
                        "",
                        "",
                        false,
                        InputInformationTypeEnum.OTHER,
                        ""
                    )
                )
                dataList.add(
                    SettingAdapterBean(
                        MultiAdapterEnum.INPUTPASSWORD, resources.getString(
                            R.string.set_def_phonenum
                        ), "", false, InputInformationTypeEnum.INPUTPHONENUM, ""
                    )
                )
            }
            InputSettingEnum.WXACCOUNT -> {
                dataList.clear()
                dataList.add(
                    SettingAdapterBean(
                        MultiAdapterEnum.SEPARATOR,
                        "",
                        "",
                        false,
                        InputInformationTypeEnum.OTHER,
                        ""
                    )
                )
                dataList.add(
                    SettingAdapterBean(
                        MultiAdapterEnum.INPUTPASSWORD, resources.getString(
                            R.string.set_def_wxaccount
                        ), "", false, InputInformationTypeEnum.INPUTWXACCOUNT, ""
                    )
                )
                dataList.add(
                    SettingAdapterBean(
                        MultiAdapterEnum.INPUTPASSWORD, resources.getString(
                            R.string.set_def_wxpassword
                        ), "", true, InputInformationTypeEnum.INPUTWXPASSWORD, ""
                    )
                )
            }
            else -> {return}
        }

        inputPhoneAdapter?.dataList = dataList
        mainRCY.adapter = inputPhoneAdapter
    }

    override fun initListener() {
        backACIV.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                this@InputPhoneNumOrWxAccountActivity.finish()
            }
        })
        rightACIV.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                when (inputSettingEnum) {
                    InputSettingEnum.PHONENUM -> {
                        savaPhoneNum()
                    }
                    InputSettingEnum.WXACCOUNT -> {
                        saveWxAccount()
                    }
                }

            }
        })
    }

    override fun startRequest() {

    }

    override fun needLocation(): Boolean {
        return false
    }

    fun savaPhoneNum() {
        var phoneData = findSelectData(InputInformationTypeEnum.INPUTPHONENUM)
        if (phoneData == null) {
            return
        }
        if (PhoneNumMatcherUtils.checkIsVaildPhoneNum(phoneData.contentStr)) {
            var readyString : String = resources.getString(R.string.ensure_setting_phonenum) + phoneData.contentStr + resources.getString(
                R.string.as_your_account
            )
            var spannableString : SpannableString = SpannableString(readyString)
            spannableString.setSpan(
                RelativeSizeSpan(1.3f),
                getFirstIndex(readyString),
                getEndIndex(readyString),
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            IosAlertDialog.instance
                .setTitleACTV(resources.getString(R.string.alert_title))
                .setContentACTV(spannableString)
                .setCancelAble(false)
                .setCancelACTV(resources.getString(R.string.cancel))
                .setSureACTV(resources.getString(R.string.sure))
                .observer(object : IosAlertDialog.OnSelectListener {
                    override fun onCancel(iosAlertDialog: IosAlertDialog) {
                        iosAlertDialog.disMiss()
                        return
                    }

                    override fun onSure(iosAlertDialog: IosAlertDialog) {
                        //保存逻辑
                        iosAlertDialog.disMiss()
                        //本地保存一套 服务器保存一套
                        saveUserPhoneInformationToLocal(phoneData.contentStr)
                        return
                    }
                })
                .build(this)
        } else {
            ToastUtil.showToast(resources.getString(R.string.input_right_phonenum))
        }
    }

    fun saveWxAccount() {

    }

    fun findSelectData(inputInformationTypeEnum: InputInformationTypeEnum) : SettingAdapterBean?{
        var dataList = inputPhoneAdapter?.dataList
        if (dataList == null) {
            return null
        }
        for (element in dataList) {
            if (inputInformationTypeEnum == element.inputInformationSettingItemBean) {
                return element
            }
        }
        return null
    }

    private fun getFirstIndex(textStr: String): Int {
        var myIndex = 0
        val p: Pattern = Pattern.compile("\\d")
        val m: Matcher = p.matcher(textStr)
        if (m.find()) {
            myIndex = m.start()
        }
        return myIndex
    }

    private fun getEndIndex(textStr: String): Int {
        var myIndex = 0
        val allNumericMatches: MutableList<String> = ArrayList()
        val p: Pattern = Pattern.compile("\\d+")
        val m: Matcher = p.matcher(textStr)
        while (m.find()) {
            allNumericMatches.add(m.group())
        }
        myIndex = allNumericMatches[allNumericMatches.size - 1].length + getFirstIndex(textStr)
        return myIndex
    }

    fun saveUserPhoneInformationToLocal(phoneData : String) {
        var userPhoneNum = SharePreferenceUtil.instance.getData(Const.USER_PHONENUM)
        var userClockInByPhoneNumBean = DatabaseManager.getUserInfoInstance().getUserClockInByPhoneNum(userPhoneNum)
        if (userClockInByPhoneNumBean == null) {
            //tologin
            startActivity(Intent(this@InputPhoneNumOrWxAccountActivity, LoginActivity::class.java))
            this@InputPhoneNumOrWxAccountActivity.finish()
            return
        }
        var userLoginAccountBean : UserLoginAccountDBBean? = DatabaseManager.getUserLoginAccountInstance().getUserLoginAccountByUUid(userClockInByPhoneNumBean.userUUid)
        if (userLoginAccountBean != null) {
            userLoginAccountBean.userPhoneNum = phoneData
            DatabaseManager.getUserLoginAccountInstance().addOrReplace(userLoginAccountBean)
        }
    }

    override fun needEventBus(): Boolean {
        return false
    }

    override fun getSelfModel(): InputPhoneNumModel {
        return InputPhoneNumModel(this@InputPhoneNumOrWxAccountActivity)
    }

    override fun showToast(value: String) {
        super.showToast(value)
    }

    override fun showLoading() {
        super.showLoading()
    }

    override fun hideLoading() {
        super.hideLoading()
    }

}