package com.xwq.companyvxwhelper.mvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.bean.dataBindingBean.WorkCycleItemBean
import com.xwq.companyvxwhelper.utils.WindowScreenUtil
import com.xwq.companyvxwhelper.widget.SwitchButton

class ChooseWorkCycleAdapter(mContext : Context, sparseCount : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TAG = this@ChooseWorkCycleAdapter::class.java.simpleName.toString()
    val widthHeightRate = 1.2
    lateinit var mContext : Context
    var viewDataBindingUtil : ViewDataBinding? = null
    var dataList : ArrayList<WorkCycleItemBean> = arrayListOf()
    var clickAndCheckListener : onClickAndCheckListener? = null
    var sparseCount : Int = 0

    init {
        this.mContext = mContext
        this.sparseCount = sparseCount
    }

    fun setOnClickAndCheck(clickAndCheckListener : onClickAndCheckListener) {
        this.clickAndCheckListener = clickAndCheckListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        viewDataBindingUtil = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.widget_focus_image, parent, false)
        var rootView : View = viewDataBindingUtil!!.root
        var sreenWidth : Int = WindowScreenUtil.getScreenWidth(mContext)
        var rootLayputParams : ConstraintLayout.LayoutParams = rootView.layoutParams as ConstraintLayout.LayoutParams
        rootLayputParams.width = (sreenWidth - (rootLayputParams.leftMargin + rootLayputParams.rightMargin) * sparseCount) / sparseCount
        rootLayputParams.height = (rootLayputParams.width * widthHeightRate).toInt()
        rootView.layoutParams = rootLayputParams
        return ChooseWorkCycleHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var curData = dataList.get(position)
        var curViewDataBinding : ViewDataBinding? = DataBindingUtil.getBinding(holder.itemView)
        curViewDataBinding?.setVariable(BR.WorkCycleItemBean,curData)

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                clickAndCheckListener?.onItemClick(v!!, position)
            }
        })

        (holder as ChooseWorkCycleHolder).switchButton.let {
            it.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    clickAndCheckListener?.onSwitchButtonCheck(it , isChecked)
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ChooseWorkCycleHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        lateinit var switchButton : SwitchButton

        init {
            this.switchButton = itemView.findViewById(R.id.widget_focus_image_sb_switch)
        }
    }

    interface onClickAndCheckListener {
        fun onItemClick(itemView : View, position: Int)
        fun onSwitchButtonCheck(switchButton: SwitchButton, isChecked : Boolean)
    }

}