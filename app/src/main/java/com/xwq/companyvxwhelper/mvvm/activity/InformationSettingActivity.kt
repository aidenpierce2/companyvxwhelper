package com.xwq.companyvxwhelper.mvvm.activity

import android.content.Intent
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.bean.Enum.InputSettingEnum
import com.xwq.companyvxwhelper.bean.dataBindingBean.InformationSettingItemBean
import com.xwq.companyvxwhelper.mvvm.adapter.InformationSettingAdapter
import com.xwq.companyvxwhelper.mvvm.model.activity.InfomationSettingModel
import com.xwq.companyvxwhelper.mvvm.view.activity.InformationSettingView
import kotlinx.android.synthetic.main.activity_base_setting.*

class InformationSettingActivity : BaseActivity<InformationSettingView, InfomationSettingModel>(),InformationSettingView {

    lateinit var backACIV : AppCompatImageView
    lateinit var titleACTV : AppCompatTextView
    lateinit var mainRCY : RecyclerView

    var dataList : ArrayList<InformationSettingItemBean> = arrayListOf()
    var informationSettingAdapter : InformationSettingAdapter? = null

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
        mainRCY = activity_base_setting_rcy_main
    }

    override fun initData() {

        fitSystemWindow()
        titleACTV.setText(this@InformationSettingActivity.resources.getString(R.string.login_setting))

        var phoneLoginStr = resources.getText(R.string.phonenum_login)
        var spannableString : SpannableString = SpannableString(phoneLoginStr)
        spannableString.setSpan(ForegroundColorSpan(resources.getColor(R.color.recommendColor)), phoneLoginStr.indexOf("("), phoneLoginStr.indexOf(")") + 1, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)

        dataList.add(InformationSettingItemBean(resources.getDrawable(R.mipmap.userphone), spannableString))
        dataList.add(InformationSettingItemBean(resources.getDrawable(R.mipmap.wxicon), resources.getString(R.string.wx_login)))

        informationSettingAdapter = InformationSettingAdapter(this@InformationSettingActivity)
        informationSettingAdapter?.dataList = dataList

        mainRCY.layoutManager = LinearLayoutManager(this@InformationSettingActivity, LinearLayoutManager.VERTICAL, false)
        mainRCY.adapter = informationSettingAdapter
    }

    override fun initListener() {
        backACIV.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                this@InformationSettingActivity.finish()
                return
            }
        })

        informationSettingAdapter?.setOnChooseItemListener(object : InformationSettingAdapter.onChooseItemListener {
            override fun onChooseItem(data: InformationSettingItemBean, postion: Int) {
                when (postion) {
                    0 -> {startActivity(Intent().setClass(this@InformationSettingActivity, InputPhoneNumOrWxAccountActivity::class.java).putExtra("TYPE", InputSettingEnum.PHONENUM))}
                    1 -> {startActivity(Intent().setClass(this@InformationSettingActivity, InputPhoneNumOrWxAccountActivity::class.java).putExtra("TYPE", InputSettingEnum.WXACCOUNT))}
                    else -> {return}
                }
            }
        })
    }

    override fun startRequest() {

    }

    override fun needLocation(): Boolean {
        return false
    }

    override fun getSelfModel(): InfomationSettingModel {
        return InfomationSettingModel(this@InformationSettingActivity)
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