package com.xwq.companyvxwhelper.mvvm.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseFragment
import com.xwq.companyvxwhelper.databinding.FragmentHistoryBinding
import com.xwq.companyvxwhelper.mvvm.model.fragment.HistoryModel
import com.xwq.companyvxwhelper.mvvm.model.fragment.LocationModel
import com.xwq.companyvxwhelper.mvvm.view.fragment.HistoryView
import com.xwq.companyvxwhelper.mvvm.view.fragment.LocationView
import com.xwq.companyvxwhelper.utils.LogUtil

class HistoryFragment : BaseFragment<FragmentHistoryBinding, HistoryView, HistoryModel>(){

    override fun onClick(p0: View?) {

    }

    override fun retryRequest() {

    }

    override fun onResume() {
        super.onResume()
        LogUtil.log(TAG, "HistoryFragment onResume()")
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun init() {
    }

    override fun needLocation(): Boolean {
        return false
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_history
    }


}