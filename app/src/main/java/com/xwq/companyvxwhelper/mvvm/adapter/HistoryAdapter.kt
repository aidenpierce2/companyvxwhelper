package com.xwq.companyvxwhelper.mvvm.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xwq.companyvxwhelper.bean.ResponseBean.HistoryResBean

class HistoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val TAG = HistoryAdapter::class.java.simpleName

    var data : List<HistoryResBean.HistoryResDataBean> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        if (data.isNullOrEmpty()) {
            return 0
        } else {
            return data.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        var curData : HistoryResBean = data.get(position)
    }
}