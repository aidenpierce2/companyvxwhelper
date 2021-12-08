package com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment

import android.app.Dialog
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseDialog
import com.xwq.companyvxwhelper.bean.dataBindingBean.DialogHistoryMenuBean
import com.xwq.companyvxwhelper.databinding.DialogHistoryMenuBinding
import com.xwq.companyvxwhelper.listener.NoDoubleClickListener
import com.xwq.companyvxwhelper.utils.HistoryChooseTimeUtils

class HistoryMenuDialog : BaseDialog<DialogHistoryMenuBinding>() {

    var dialogHistoryMenuBean : DialogHistoryMenuBean? = null
    private var allowShow : Boolean = true
    lateinit var cancelACBT : Button
    lateinit var ensureACBT : Button
    var viewClickListener : ViewClickListener? = null

    companion object {
        var historyMenuDialog : HistoryMenuDialog? = null
        @JvmStatic
        fun getSingleton() : HistoryMenuDialog{
            if (historyMenuDialog == null) {
                historyMenuDialog = HistoryMenuDialog()
            }
            return historyMenuDialog!!
        }
    }

    override fun setAnimation(dialog: Dialog) {

    }

    override fun getContentViewId(): Int {
        return R.layout.dialog_history_menu
    }

    override fun setBind() {

    }

    override fun initView() {
        cancelACBT = getBinding().dialogHistoryMenuAcbtCancel
        ensureACBT = getBinding().dialogHistoryMenuAcbtSure
    }

    override fun initData() {
        dialogHistoryMenuBean = DialogHistoryMenuBean();
        dialogHistoryMenuBean?.titleStr = resources.getString(R.string.historysearch)
        dialogHistoryMenuBean?.timeIntever = resources.getString(R.string.startAndEndTime)
        dialogHistoryMenuBean?.startTime = resources.getString(R.string.startTime)
        dialogHistoryMenuBean?.startTimeValue = HistoryChooseTimeUtils.getStartDay()
        dialogHistoryMenuBean?.endTime = resources.getString(R.string.endTime)
        dialogHistoryMenuBean?.endTimeValue = HistoryChooseTimeUtils.getEndDay()
        dialogHistoryMenuBean?.statusChoose = resources.getString(R.string.statusChoose)
        dialogHistoryMenuBean?.allStatus = resources.getString(R.string.allStatus)
        dialogHistoryMenuBean?.errStatus = resources.getString(R.string.failStatus)
        dialogHistoryMenuBean?.ensureStr = resources.getString(R.string.succStatus)
        dialogHistoryMenuBean?.cancelStr = resources.getString(R.string.cancel)
        dialogHistoryMenuBean?.ensureStr = resources.getString(R.string.sure)
        getBinding().setVariable(BR.DialogHistoryMenuBean, dialogHistoryMenuBean)
    }

    override fun initListener() {
        cancelACBT.setOnClickListener(object : NoDoubleClickListener {
            override fun onClick(v: View?) {
                super.onClick(v)
                if (viewClickListener != null) {
                    viewClickListener?.onCancel(this@HistoryMenuDialog)
                } else {
                    HistoryMenuDialog.getSingleton().disMiss()
                }
            }
        })
        ensureACBT.setOnClickListener(object : NoDoubleClickListener {
            override fun onClick(v: View?) {
                super.onClick(v)
                if (viewClickListener != null) {
                    viewClickListener?.onEnsure(this@HistoryMenuDialog)
                } else {
                    HistoryMenuDialog.getSingleton().disMiss()
                }
            }
        })
    }

    override fun destroy() {
        if (historyMenuDialog != null) {
            getSingleton().allowShow = true
        }
    }

    override fun dialogCancelAble(): Boolean {
        return false
    }

    fun build(fragmentManager : FragmentManager, allowDismiss : Boolean) {
        if (historyMenuDialog == null) {
            return
        }

        if (getSingleton().allowShow) {
            getSingleton().allowShow = false
        } else {
            return
        }

        if (allowDismiss) {
            getSingleton().showAllowStateLoss(fragmentManager)
        } else {
            getSingleton().showNotAllowStateLoss(fragmentManager)
        }
    }

    fun selectTimeSchedule(startTime : Boolean) {

    }

    interface ViewClickListener {
        fun onCancel(historyMenuDialog: HistoryMenuDialog)
        fun onEnsure(historyMenuDialog: HistoryMenuDialog)
    }
}