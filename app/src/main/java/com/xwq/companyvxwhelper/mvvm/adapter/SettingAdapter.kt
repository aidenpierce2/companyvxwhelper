package com.xwq.companyvxwhelper.mvvm.adapter

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.bean.dataBindingBean.SettingDBBean

class SettingAdapter(context: AppCompatActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG : String = SettingDBBean::class.java.simpleName.toString()
    lateinit private var context : AppCompatActivity
    var dataList : SparseArray<SettingDBBean> = SparseArray<SettingDBBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewDataBinding : ViewDataBinding? = null
        when(viewType) {
            0 -> {
                viewDataBinding = DataBindingUtil.inflate(context.layoutInflater, R.layout.adapter_setting_base_layout, parent, false)
                return BaseSettingViewHolder(viewDataBinding?.root!!)
            }
            1 -> {
                viewDataBinding = DataBindingUtil.inflate(context.layoutInflater, R.layout.adapter_header, parent, false)
                return HeaderViewHolder(viewDataBinding?.root!!)
            }
            else -> {
                viewDataBinding = DataBindingUtil.inflate(context.layoutInflater, R.layout.adapter_empty, parent, false)
                return HeaderViewHolder(viewDataBinding?.root!!)
            }
        }
    }

    init {
        this.context = context
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewDataBinding : ViewDataBinding? = null
        if (dataList == null) {
            return
        }
        if (position > -1 && position < dataList.size()) {
            viewDataBinding = DataBindingUtil.getBinding<ViewDataBinding>(holder.itemView)
            var curSettingDBBean : SettingDBBean = dataList[position]
            viewDataBinding?.setVariable(BR.SettingDBBean, curSettingDBBean)
        }
    }

    override fun getItemCount(): Int {
        if (dataList == null) {
            return  0
        } else {
            return dataList.size()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (dataList == null) {
            return -1
        }
        if (position > -1 && position < dataList.size()) {
            var curSettingDBBean : SettingDBBean = dataList[position]
            when (curSettingDBBean.showStr) {
                true -> {
                    return 0
                }
                false -> {
                    return 1
                }
            }
        }
        return -1
    }

    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class HeaderViewHolder(itemViewTemp: View) : RecyclerView.ViewHolder(itemViewTemp) {

    }

    class BaseSettingViewHolder(itemViewTemp: View) : RecyclerView.ViewHolder(itemViewTemp) {

    }
}