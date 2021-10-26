package com.xwq.companyvxwhelper.mvvm.adapter

import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.bean.Enum.MultiAdapterEnum
import com.xwq.companyvxwhelper.bean.InputPhoneNumItemBean
import com.xwq.companyvxwhelper.bean.SettingAdapterBean

class InputPhoneAdapter(mContext : AppCompatActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var TAG = this@InputPhoneAdapter::class.java.simpleName

    lateinit var mContext : AppCompatActivity
    var dataList : ArrayList<SettingAdapterBean> = arrayListOf()
    var viewDataBinding : ViewDataBinding? = null
    var inputPhoneListener : InputPhoneListener? = null

    init {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> {
                var separatorView : View = LayoutInflater.from(mContext).inflate(R.layout.adapter_separator, parent, false)
                return SeparatorHolder(separatorView)
            }
            1 -> {
                viewDataBinding = DataBindingUtil.inflate(mContext.layoutInflater, R.layout.adapter_input_phonenumorpassword, parent, false)
                viewDataBinding?.setVariable(BR.InputPhoneAdapter, this@InputPhoneAdapter)
                return InputPhoneHolder(viewDataBinding!!.root)
            }else -> {
                viewDataBinding = DataBindingUtil.inflate(mContext.layoutInflater, R.layout.adapter_separator, parent, false)
                return SeparatorHolder(viewDataBinding!!.root)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is InputPhoneHolder) {
            var curData = dataList.get(position)
            var curViewDataBing : ViewDataBinding? = DataBindingUtil.getBinding(holder.itemView)
            curViewDataBing?.setVariable(BR.SettingAdapterBean, curData)
            (holder as InputPhoneHolder).inputACET?.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    inputPhoneListener.let {
                        it?.inputTextValue(s.toString())
                    }
                }
            })

            (holder as InputPhoneHolder).examineACCB?.let {
                it.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                        examinePassword(it, isChecked)
                    }
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        var curData = dataList?.get(position)
        when (curData.multiAdapterEnum) {
            MultiAdapterEnum.SEPARATOR -> {return 0}
            MultiAdapterEnum.INPUTPASSWORD -> {return 1}
        }
    }

    class SeparatorHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class InputPhoneHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var inputACET : AppCompatEditText? = null
        var examineACCB : AppCompatCheckBox? = null
        var deleteACIV : AppCompatImageView? = null

        init {
            this.inputACET = itemView.findViewById(R.id.adapter_input_phonenum_acet_input)
            this.examineACCB = itemView.findViewById(R.id.adapter_input_phonenum_aciv_examine)
            this.deleteACIV = itemView.findViewById(R.id.adapter_input_phonenum_aciv_delete)
        }
    }

    fun examinePassword(examineACCB : CheckBox?, isChecked: Boolean) {
        //查看密码
        if (isChecked) {
            examineACCB?.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            examineACCB?.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    fun deleteInputPhone() {
        //通知清除数据
        inputPhoneListener.let {
            it?.deleteInputPhone()
        }
    }

    interface InputPhoneListener{
        fun inputTextValue(inputValue : String)
        fun deleteInputPhone()
    }
}