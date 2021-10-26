package com.xwq.companyvxwhelper.mvvm.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.bean.InformationSettingItemBean

class InformationSettingAdapter(mContext : AppCompatActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var TAG : String = this@InformationSettingAdapter::class.java.simpleName.toString()
    var dataList : ArrayList<InformationSettingItemBean> = arrayListOf()
    var mContext : AppCompatActivity? = null
    var chooseItemListener : onChooseItemListener? = null
    lateinit var viewDataBinding : ViewDataBinding

    init {
        this@InformationSettingAdapter.mContext = mContext
    }

    fun setOnChooseItemListener(chooseItemListener : onChooseItemListener?) {
        this.chooseItemListener = chooseItemListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        viewDataBinding = DataBindingUtil.inflate(mContext!!.layoutInflater, R.layout.adapter_information_setting, parent,false)
        return InformationSettingHolder(viewDataBinding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var curData = dataList.get(position)
        var curViewDataBinding : ViewDataBinding? = DataBindingUtil.getBinding(holder.itemView)
        curViewDataBinding?.setVariable(BR.informationitem, curData)

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                chooseItemListener?.onChooseItem(curData, position)
            }
        })
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class InformationSettingHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var curItemView : View

        init {
            curItemView = itemView
        }
    }

    // 再实现个接口 点击回调用
    interface onChooseItemListener {
        fun onChooseItem(data : InformationSettingItemBean, postion : Int)
    }
}