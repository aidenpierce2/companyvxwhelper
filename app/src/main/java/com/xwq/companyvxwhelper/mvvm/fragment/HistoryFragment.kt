package com.xwq.companyvxwhelper.mvvm.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseFragment
import com.xwq.companyvxwhelper.databinding.FragmentHistoryBinding
import com.xwq.companyvxwhelper.mvvm.model.fragment.HistoryModel
import com.xwq.companyvxwhelper.mvvm.model.fragment.LocationModel
import com.xwq.companyvxwhelper.mvvm.view.fragment.HistoryView
import com.xwq.companyvxwhelper.mvvm.view.fragment.LocationView
import com.xwq.companyvxwhelper.utils.LogUtil
import com.xwq.companyvxwhelper.utils.WindowScreenUtil
import com.xwq.companyvxwhelper.bean.dataBindingBean.HistoryFragmentBaseBean

class HistoryFragment : BaseFragment<FragmentHistoryBinding, HistoryView, HistoryModel>(){

    val navigateRate : Float = 0.7F

    override fun onClick(p0: View?) {

    }

    override fun retryRequest() {

    }

    override fun onResume() {
        super.onResume()
        LogUtil.log(TAG, "HistoryFragment onResume()")
    }

    override fun initView(savedInstanceState: Bundle?) {
        var layoutParams : DrawerLayout.LayoutParams = getBinding().fragmentHistoryNagvMain.layoutParams as DrawerLayout.LayoutParams
        layoutParams.width = (WindowScreenUtil.getScreenWidth(mContext) * navigateRate).toInt()
        getBinding().fragmentHistoryNagvMain.layoutParams = layoutParams
    }

    override fun init() {
        getBinding().setVariable(BR.HistoryFragment, this)
        var historyFragmentBaseBean : HistoryFragmentBaseBean? = null
        historyFragmentBaseBean?.timeIntever = mContext.resources.getString(R.string.startAndEndTime)
        historyFragmentBaseBean?.startTime = mContext.resources.getString(R.string.startTime)
        historyFragmentBaseBean?.endTime = mContext.resources.getString(R.string.endTime)
        historyFragmentBaseBean?.statusChoose = mContext.resources.getString(R.string.statusChoose)
        historyFragmentBaseBean?.allStatus = mContext.resources.getString(R.string.allStatus)
        historyFragmentBaseBean?.cancelStr = mContext.resources.getString(R.string.failStatus)
        historyFragmentBaseBean?.ensureStr = mContext.resources.getString(R.string.succStatus)
        getBinding().setVariable(BR.HistoryFragmentBaseBean, historyFragmentBaseBean)
    }

    override fun needLocation(): Boolean {
        return false
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_history
    }

    //选择开始时间
    fun chooseStartTime() {

    }

    //选择结束时间
    fun chooseEndTime() {

    }

    //取消
    fun cancelDrawer() {

    }

    //确定
    fun ensureDrawable() {

    }
}