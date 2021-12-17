package com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment

import android.app.Dialog
import android.graphics.Color
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseDialog
import com.xwq.companyvxwhelper.bean.dataBindingBean.DialogHistoryMenuBean
import com.xwq.companyvxwhelper.databinding.DialogHistoryMenuBinding
import com.xwq.companyvxwhelper.listener.NoDoubleClickListener
import com.xwq.companyvxwhelper.utils.HistoryChooseTimeUtils


class HistoryMenuDialog : BaseDialog<DialogHistoryMenuBinding>() {

    var preDialogHistoryMenuBean : DialogHistoryMenuBean? = null
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
        dialogHistoryMenuBean?.chooseAll = true
        dialogHistoryMenuBean?.errStatus = resources.getString(R.string.failStatus)
        dialogHistoryMenuBean?.chooseErr = false
        dialogHistoryMenuBean?.ensureStr = resources.getString(R.string.succStatus)
        dialogHistoryMenuBean?.chooseSucc = false
        dialogHistoryMenuBean?.cancelStr = resources.getString(R.string.cancel)
        dialogHistoryMenuBean?.ensureStr = resources.getString(R.string.sure)
        preDialogHistoryMenuBean = dialogHistoryMenuBean
        getBinding().setVariable(BR.DialogHistoryMenuBean, dialogHistoryMenuBean)
        getBinding().setVariable(BR.HistoryMenuDialog, this)
    }

    override fun initListener() {
        cancelACBT.setOnClickListener(object : NoDoubleClickListener {
            override fun onClick(v: View?) {
                super.onClick(v)
                if (viewClickListener != null) {
                    viewClickListener?.onCancel()
                }
                HistoryMenuDialog.getSingleton().disMiss()
            }
        })
        ensureACBT.setOnClickListener(object : NoDoubleClickListener {
            override fun onClick(v: View?) {
                super.onClick(v)
                if (viewClickListener != null) {
                    viewClickListener?.onEnsure(
                        dialogHistoryMenuBean!!, preDialogHistoryMenuBean!!.checkIsDifferent(
                            dialogHistoryMenuBean!!
                        )
                    )
                }
                HistoryMenuDialog.getSingleton().disMiss()
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

    fun build(fragmentManager: FragmentManager, allowDismiss: Boolean) {
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

    fun selectTimeSchedule(startTime: Boolean) {
        var timePickView : TimePickerView = TimePickerBuilder(context, OnTimeSelectListener { date, v -> //选中事件回调

        })
            .setType(booleanArrayOf(true, true, true, false, false, false)) // 默认全部显示
            .setCancelText("Cancel") //取消按钮文字
            .setSubmitText("Sure") //确认按钮文字 //滚轮文字大小
            .setTitleSize(20) //标题文字大小
            .setTitleText("请选择时间") //标题文字
            .setOutSideCancelable(false) //点击屏幕，点在控件外部范围时，是否取消显示
            .isCyclic(true) //是否循环滚动
            .setBgColor(Color.WHITE)
            .setTitleColor(Color.BLACK) //标题文字颜色
            .setSubmitColor(Color.BLUE) //确定按钮文字颜色
            .setCancelColor(Color.parseColor("#FFF5F5F5")) //取消按钮文字颜色
            .setTitleBgColor(-0x99999a) //标题背景颜色 Night mode
            .setBgColor(-0xcccccd) //滚轮背景颜色 Night mode
            .setLabel("年", "月", "日", "时", "分", "秒") //默认设置为年月日时分秒
            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .isDialog(true) //是否显示为对话框样式
            .build()
        timePickView.show()
    }

    interface ViewClickListener {
        fun onCancel()
        fun onEnsure(dialogHistoryMenuBean: DialogHistoryMenuBean, dataChanged: Boolean)
    }
}