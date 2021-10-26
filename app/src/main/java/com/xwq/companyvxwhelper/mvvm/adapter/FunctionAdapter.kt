package com.xwq.companyvxwhelper.mvvm.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.bean.FunctionItemBean

class FunctionAdapter(mContext : AppCompatActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TAG : String = FunctionAdapter::class.java.simpleName.toString()
    lateinit var mContext : AppCompatActivity
    var dataList : ArrayList<FunctionItemBean> = arrayListOf()
    lateinit var viewDataBinding: ViewDataBinding
    var itemClickListener : onChooseItemListener? = null

    init{
        this@FunctionAdapter.mContext = mContext
    }

    fun setOnItemClickListener(itemClickListener : onChooseItemListener) {
        this@FunctionAdapter.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        viewDataBinding = DataBindingUtil.inflate(mContext.layoutInflater, R.layout.adapter_function, parent, false)
        return FunctionHolder(viewDataBinding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var curViewDataBinding : ViewDataBinding? = DataBindingUtil.getBinding((holder as FunctionHolder).curItemView)
        var curData : FunctionItemBean = dataList.get(position)
        curViewDataBinding?.setVariable(BR.funtionitem, curData)

        (holder as FunctionHolder).curItemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                itemClickListener?.onChooseItem(curData, position)
            }
        })
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class FunctionHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var curItemView : View
        init {
            this@FunctionHolder.curItemView = itemView
        }
    }

    // 再实现个接口 点击回调用
    interface onChooseItemListener {
        fun onChooseItem(data : FunctionItemBean, postion : Int)
    }
}