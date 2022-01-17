package com.xwq.companyvxwhelper.mvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.bean.dataBindingBean.HistoryBaseBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.HistoryItemBean
import com.xwq.companyvxwhelper.databinding.AdapterHistoryHeaderBinding
import com.xwq.companyvxwhelper.databinding.AdapterHistoryItemBinding
import com.xwq.companyvxwhelper.databinding.AdapterHistorySecHeaderBinding

class HistoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    val TAG = HistoryAdapter::class.java.simpleName

    var mContext : Context? = null
    var data : List<HistoryItemBean> = arrayListOf()

    constructor( mContext : Context) {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var curViewDataBinding : ViewDataBinding? = null
        when (viewType) {
            0 -> {
                curViewDataBinding = DataBindingUtil.inflate<AdapterHistoryHeaderBinding>(LayoutInflater.from(mContext), R.layout.adapter_history_header ,parent, false)
                return HeaderViewHolder(curViewDataBinding.root)
            }
            1 -> {
                curViewDataBinding = DataBindingUtil.inflate<AdapterHistorySecHeaderBinding>(LayoutInflater.from(mContext), R.layout.adapter_history_sec_header ,parent, false)
                return TitleViewHolder(curViewDataBinding.root)
            }
            2 -> {
                curViewDataBinding = DataBindingUtil.inflate<AdapterHistoryItemBinding>(LayoutInflater.from(mContext), R.layout.adapter_history_item ,parent, false)
                return ContentViewHolder(curViewDataBinding.root)
            }
            else -> {
                curViewDataBinding = DataBindingUtil.inflate<AdapterHistoryHeaderBinding>(LayoutInflater.from(mContext), R.layout.adapter_history_header ,parent, false)
                return HeaderViewHolder(curViewDataBinding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var curViewDataBinding : ViewDataBinding? = null
        var curHistoryBaseBean : HistoryBaseBean = data.get(position)
        if (holder is HeaderViewHolder) {
            curViewDataBinding = DataBindingUtil.getBinding<AdapterHistoryHeaderBinding>(holder.itemView)
            curViewDataBinding?.setVariable(BR.HistoryItemBean, curHistoryBaseBean)
        } else if (holder is TitleViewHolder){
            curViewDataBinding = DataBindingUtil.getBinding<AdapterHistorySecHeaderBinding>(holder.itemView)
            curViewDataBinding?.setVariable(BR.HistoryItemBean, curHistoryBaseBean)
        } else if (holder is ContentViewHolder) {
            curViewDataBinding = DataBindingUtil.getBinding<AdapterHistoryItemBinding>(holder.itemView)
            curViewDataBinding?.setVariable(BR.HistoryItemBean, curHistoryBaseBean)
        } else {
            return
        }
    }

    override fun getItemCount(): Int {
        if (data.isNullOrEmpty()) {
            return 0
        } else {
            return data.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        var curData : HistoryBaseBean = data.get(position)
        when (curData.itemType) {
            1,2-> return curData.itemType
            else -> return super.getItemViewType(position)
        }
    }

    class HeaderViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class ContentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }
}