package com.xwq.companyvxwhelper.mvvm.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseFragment
import com.xwq.companyvxwhelper.databinding.FragmentHistoryBinding
import com.xwq.companyvxwhelper.mvvm.model.fragment.HistoryModel
import com.xwq.companyvxwhelper.mvvm.view.fragment.HistoryView
import com.xwq.companyvxwhelper.utils.LogUtil
import com.xwq.companyvxwhelper.bean.dataBindingBean.DialogHistoryMenuBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.HistoryDataBean
import com.xwq.companyvxwhelper.listener.NoDoubleClickListener
import com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment.HistoryMenuDialog
import com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment.LoadingDialog

class HistoryFragment : BaseFragment<FragmentHistoryBinding, HistoryView, HistoryModel>(){

    lateinit var mainSRFL : SmartRefreshLayout
    lateinit var mainRCY : RecyclerView
    lateinit var menuACIV : AppCompatImageView

    override fun onClick(p0: View?) {

    }

    override fun retryRequest() {

    }

    override fun onResume() {
        super.onResume()
        LogUtil.log(TAG, "HistoryFragment onResume()")
    }

    override fun initView(savedInstanceState: Bundle?) {
        mainSRFL = getBinding().fragmentHistorySrfl
        mainRCY = getBinding().fragmentHistoryRcyMain
        menuACIV = getBinding().fragmentHistoryAcivMenu
    }

    override fun init() {
        getBinding().setVariable(BR.HistoryFragment, this)
        getBinding().setVariable(BR.HistoryDataBean, HistoryDataBean(resources.getDrawable(R.mipmap.menu)))
    }

    override fun needLocation(): Boolean {
        return false
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_history
    }

    fun clickMenu() {
        HistoryMenuDialog.getSingleton().build(mContext.supportFragmentManager, true)
        registerDialogMenuListener()
    }

    fun registerDialogMenuListener() {
        HistoryMenuDialog.getSingleton().viewClickListener = object : HistoryMenuDialog.ViewClickListener {
            override fun onCancel(historyMenuDialog: HistoryMenuDialog) {
                historyMenuDialog.disMiss()
            }

            override fun onEnsure(historyMenuDialog: HistoryMenuDialog) {
                historyMenuDialog.disMiss()
            }
        }
    }

    //请求数据
    fun requestHistoryDataInfo() {

    }

}