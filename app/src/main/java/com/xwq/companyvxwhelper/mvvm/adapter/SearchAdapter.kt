package com.xwq.companyvxwhelper.mvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.bean.dataBindingBean.SearchDialogItemBean
import com.xwq.companyvxwhelper.listener.NoDoubleClickListener

class SearchAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var TAG : String = SearchAdapter@this::class.java.simpleName.toString()
    private var data : Array<SearchDialogItemBean> = arrayOf()
    private var listener : onChooseItemListener? = null
    lateinit var viewDataBinding : ViewDataBinding

    fun setData(data : Array<SearchDialogItemBean>) {
        this.data = data
    }

    fun setOnChooseItemListener(listener : onChooseItemListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), R.layout.adapter_search, parent, false)
        return SearchViewHolder(viewDataBinding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewDataBinding : ViewDataBinding? = DataBindingUtil.getBinding<ViewDataBinding>(holder.itemView)
        var itemData = data.get(position)
        viewDataBinding!!.setVariable(BR.item, itemData)

        var itemView : View = holder.itemView
        itemView.setOnClickListener(object : NoDoubleClickListener{
            override fun onClick(v: View?) {
                if (this@SearchAdapter.listener != null) {
                    this@SearchAdapter.listener!!.onChooseItem(itemData, position)
                }
            }
        });
    }

    override fun getItemCount(): Int {
        if (data == null || data.isEmpty()) {
            return 0
        }
        return data.size
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mItemView : View? = null
        init {
            mItemView = itemView
        }
    }

    // 再实现个接口 点击回调用
    interface onChooseItemListener {
        fun onChooseItem(data : SearchDialogItemBean, postion : Int)
    }
}