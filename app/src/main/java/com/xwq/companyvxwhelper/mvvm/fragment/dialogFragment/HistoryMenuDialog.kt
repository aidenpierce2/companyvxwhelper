package com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment

import android.app.Dialog
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
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
import java.util.*


class HistoryMenuDialog : BaseDialog<DialogHistoryMenuBinding>() {

    var preDialogHistoryMenuBean : DialogHistoryMenuBean? = null
    var dialogHistoryMenuBean : DialogHistoryMenuBean? = null
    private var allowShow : Boolean = true
    lateinit var startACBT : AppCompatButton
    lateinit var endACBT : AppCompatButton
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
        startACBT = getBinding().dialogHistoryMenuAcbtStartTime
        endACBT = getBinding().dialogHistoryMenuAcbtEndTime

        startACBT.setTag(1, "start")
        endACBT.setTag(1, "end")
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

    fun translateToYMD(date: Date) : String{
        if (date == null) {
            return ""
        }
        return java.text.SimpleDateFormat("yyyy-MM-dd").format(date)
    }

    fun selectTimeSchedule(startTime: Boolean) {
        var timePickView : TimePickerView = TimePickerBuilder(context, OnTimeSelectListener { date, v -> //??????????????????
            var tag = v.getTag(1)
            when (tag) {
                "start" -> dialogHistoryMenuBean?.startTime = translateToYMD(date)
                "end" -> dialogHistoryMenuBean?.endTime = translateToYMD(date)
            }
        })
            .setType(booleanArrayOf(true, true, true, false, false, false)) // ??????????????????
            .setCancelText(resources.getString(R.string.cancel)) //??????????????????
            .setSubmitText(resources.getString(R.string.sure)) //?????????????????? //??????????????????
            .setTitleSize(20) //??????????????????
            .setTitleText(resources.getString(R.string.chooseTime)) //????????????
            .setOutSideCancelable(false) //???????????????????????????????????????????????????????????????
            .isCyclic(false) //??????????????????
            .setTitleColor(resources.getColor(R.color.titleColor)) //??????????????????
            .setSubmitColor(resources.getColor(R.color.input)) //????????????????????????
            .setCancelColor(resources.getColor(R.color.input)) //????????????????????????
            .setTitleBgColor(resources.getColor(R.color.white)) //?????????????????? Night mode
            .setBgColor(resources.getColor(R.color.white)) //?????????????????? Night mode
            .setLabel("???", "???", "???", "???", "???", "???") //?????????????????????????????????
            .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
            .isDialog(true) //???????????????????????????
            .build()
        timePickView.show()

    }

    interface ViewClickListener {
        fun onCancel()
        fun onEnsure(dialogHistoryMenuBean: DialogHistoryMenuBean, dataChanged: Boolean)
    }
}