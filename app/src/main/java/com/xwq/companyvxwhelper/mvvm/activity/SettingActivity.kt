package com.xwq.companyvxwhelper.mvvm.activity

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.bean.dataBindingBean.SettingDBBean
import com.xwq.companyvxwhelper.bean.ResponseBean.UserInfoResBean
import com.xwq.companyvxwhelper.databinding.ActivityBaseSettingBinding
import com.xwq.companyvxwhelper.mvvm.adapter.SettingAdapter
import com.xwq.companyvxwhelper.mvvm.adapter.decoration.RevenueSummaryItemDecoration
import com.xwq.companyvxwhelper.mvvm.model.activity.SettingModel
import com.xwq.companyvxwhelper.mvvm.view.activity.SettingView

class SettingActivity : BaseActivity<ActivityBaseSettingBinding, SettingView, SettingModel>(),SettingView {

    var dataList : SparseArray<SettingDBBean> = SparseArray<SettingDBBean>()
    var mainRcy : RecyclerView? = null
    var backACIV : AppCompatImageView? = null
    var settingAdapter : SettingAdapter? = null

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
        mainRcy = getBinding().activityBaseSettingRcyMain
        backACIV = getBinding().activityBaseSettingAcivBack
        fitSystemWindow()
    }

    override fun initData() {
        getModel().getUserAccountInfo()
    }

    override fun initListener() {
        backACIV?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
    }

    override fun startRequest() {

    }

    override fun needLocation(): Boolean {
        return true
    }

    override fun getSelfModel(): SettingModel {
        return SettingModel(this@SettingActivity)
    }

    override fun getUserInfoSucc(data: UserInfoResBean?) {
        var data = data
        if (data == null) {
            data = UserInfoResBean()
        }
        dataList?.setValueAt(0, SettingDBBean(resources.getString(R.string.header),"", data.headerImgUrl, false, resources.getDrawable(R.mipmap.right_enter)))
        dataList?.setValueAt(1, SettingDBBean(resources.getString(R.string.nickName),data.nickName, "", true, resources.getDrawable(R.mipmap.right_enter)))
        dataList?.setValueAt(2, SettingDBBean(resources.getString(R.string.sexual),"", data.sexual, true, resources.getDrawable(R.mipmap.right_enter)))
        dataList?.setValueAt(3, SettingDBBean(resources.getString(R.string.telephoneNum),"", data.headerImgUrl, true, resources.getDrawable(R.mipmap.right_enter)))
        dataList?.setValueAt(4, SettingDBBean(resources.getString(R.string.lonALat),"", data.longitudeStr + "/" + data.latitudeStr, true, resources.getDrawable(R.mipmap.update_location)))

        if (settingAdapter == null) {
            settingAdapter = SettingAdapter(this@SettingActivity)
        }
        settingAdapter?.dataList = dataList
        mainRcy?.adapter = settingAdapter
        mainRcy!!.addItemDecoration(RevenueSummaryItemDecoration(this, RevenueSummaryItemDecoration.VERTICAL_LIST))
    }

    override fun getUserInfoFail(data: String) {

    }

    override fun getContentViewId(): Int {
        return R.layout.activity_base_setting
    }
}