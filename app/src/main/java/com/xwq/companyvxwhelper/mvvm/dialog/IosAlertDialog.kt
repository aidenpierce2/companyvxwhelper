package com.xwq.companyvxwhelper.mvvm.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.xwq.companyvxwhelper.R
import kotlinx.android.synthetic.main.dialog_ios_alert.*
import java.lang.reflect.Field

class IosAlertDialog: DialogFragment() {

    var contentView : View? = null
    var titleACTV : AppCompatTextView? = null
    var contentACTV : AppCompatTextView? = null
    var cancelACTV : AppCompatTextView? = null
    var sureACTV : AppCompatTextView? = null
    var bundle : Bundle? = null
    var onSelectListener : OnSelectListener? = null

    var titleData : String? = ""
    var contentData : String? = ""
    var cancelData : String? = ""
    var sureData : String? = ""
    var spannableString : SpannableString = SpannableString("")

    var cancelAble : Boolean? = false

    init {
        bundle = Bundle()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉Dialog的标题部分
        getDialog()!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));//dialog背景色变为透明
        contentView = inflater!!.inflate(R.layout.dialog_ios_alert, container)

        if (!cancelAble!!) {
            dialog!!.setCancelable(false)
            dialog!!.setCanceledOnTouchOutside(false)
        }

        return contentView;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView();
        initData()


        cancelACTV!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onSelectListener!!.onCancel(instance!!)
            }
        })

        sureACTV!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onSelectListener!!.onSure(instance!!)
            }
        })
    }

    companion object {
        var instance: IosAlertDialog = IosAlertDialog()
    }

    fun initView () {
        titleACTV = dialog_ios_alert_actv_title
        contentACTV = dialog_ios_alert_actv_content
        cancelACTV = dialog_ios_alert_actv_cancel
        sureACTV = dialog_ios_alert_actv_sure
    }

    fun initData () {
        titleACTV!!.text = titleData
        if (TextUtils.isEmpty(contentData)) {
            contentACTV!!.text = spannableString
        } else {
            contentACTV!!.text = contentData
        }
        cancelACTV!!.text = cancelData
        sureACTV!!.text = sureData
    }

    fun setTitleACTV(titleStr : String) : IosAlertDialog{
        titleData = titleStr
        return instance!!
    }

    fun setContentACTV(contentStr : String) : IosAlertDialog{
        contentData = contentStr
        return instance!!
    }

    fun setContentACTV(spannableString : SpannableString) : IosAlertDialog{
        this.spannableString = spannableString
        return instance!!
    }

    fun setCancelACTV(cancelStr : String) : IosAlertDialog{
        cancelData = cancelStr
        return instance!!
    }

    fun setSureACTV(sureStr : String) : IosAlertDialog{
        sureData = sureStr
        return instance!!
    }

    fun observer(listener : OnSelectListener) : IosAlertDialog{
        onSelectListener = listener
        return instance!!
    }

    fun setCancelAble(cancelAble : Boolean) : IosAlertDialog{
        this@IosAlertDialog.cancelAble = cancelAble
        return instance!!
    }

    fun build(context : Context) {
        context as AppCompatActivity
        var tag = context.javaClass.toString()
        if (instance != null && !instance.isAdded) {
            showAllowingStateLoss(context.supportFragmentManager, tag)
        }
    }

    private fun showAllowingStateLoss(manager: FragmentManager, tag: String?) {
        try {
            val c = Class.forName("androidx.fragment.app.DialogFragment")
            val con = c.getConstructor()
            val obj: Any = con.newInstance()
            val shownByMe: Field = c.getDeclaredField("mShownByMe")
            shownByMe.setAccessible(true)
            shownByMe.set(obj, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()

    }

    fun disMiss() {
        if (isAdded && !isRemoving) {
            dismissAllowingStateLoss()
        }
    }

    interface OnSelectListener {
        fun onCancel(iosAlertDialog: IosAlertDialog)
        fun onSure(iosAlertDialog: IosAlertDialog)
    }
}