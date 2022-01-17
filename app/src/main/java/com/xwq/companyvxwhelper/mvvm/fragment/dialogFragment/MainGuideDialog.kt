package com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseDialog
import com.xwq.companyvxwhelper.bean.dataBindingBean.DialogMainGuideBean
import com.xwq.companyvxwhelper.databinding.DialogMainGuideBinding
import com.xwq.companyvxwhelper.utils.LogUtil
import com.xwq.companyvxwhelper.utils.WindowScreenUtil

class MainGuideDialog : BaseDialog<DialogMainGuideBinding>() {

    private val SCREEN_WIDTH_RATE : Float = 0.8F

    lateinit private var mainCSTL : ConstraintLayout
    lateinit private var mainContentACTV : AppCompatTextView
    lateinit private var leftChooseACTV : AppCompatTextView
    lateinit private var rightChooseACTV : AppCompatTextView

    private var bean : DialogMainGuideBean = DialogMainGuideBean()
    lateinit var iClickInterface : IClickInterface
    var cancelAble: Boolean = true
    var allowShow : Boolean = true

    companion object {
        private var instance : MainGuideDialog? = null

        @JvmStatic
        @Synchronized fun getSingleInstance() : MainGuideDialog {
            if (instance == null) {
                instance = MainGuideDialog()
            }
            return instance!!
        }

    }

    override fun setAnimation(dialog: Dialog) {

    }

    override fun setBind() {

    }

    override fun initView() {

        getBinding().setVariable(BR.MainGuideDialog, this@MainGuideDialog)

        getDialogWindow().setWindowAnimations(R.style.main_guide_animation)

        mainCSTL = getBinding().dialogMainGuideCstlMain
        mainContentACTV = getBinding().dialogMainGuideActvContent
        leftChooseACTV = getBinding().dialogMainGuideActvLeftChoose
        rightChooseACTV = getBinding().dialogMainGuideActvRightChoose

        var layoutParams = mainCSTL.layoutParams
        layoutParams.width = (WindowScreenUtil.getScreenWidth(context) * SCREEN_WIDTH_RATE).toInt()
        mainCSTL.layoutParams = layoutParams

        if (!cancelAble) {
            this@MainGuideDialog.dialog!!.setCancelable(false)
            this@MainGuideDialog.dialog!!.setCanceledOnTouchOutside(false)
        }

    }

    override fun initData() {
        getBinding().setVariable(BR.DialogMainGuideBean, bean)
    }

    override fun initListener() {

    }

    override fun destroy() {
        instance?.allowShow = true
    }

    override fun dialogCancelAble(): Boolean {
        return false
    }


    fun onViewClick(view : View) {
        if (!this@MainGuideDialog::iClickInterface.isInitialized) {
            return
        }
        when(view) {
            leftChooseACTV -> {
                iClickInterface.onLeftChoosed(this@MainGuideDialog)
            }
            rightChooseACTV -> {
                iClickInterface.onRightChoosed(this@MainGuideDialog)
            }
        }
    }

    fun initContent(bean : DialogMainGuideBean) : MainGuideDialog{
        this.bean = bean
        return instance!!
    }

    fun addClickCallBack(iClickInterface : IClickInterface) : MainGuideDialog{
        this.iClickInterface = iClickInterface
        return instance!!
    }

    fun allowCancelAble(cancelAble : Boolean) : MainGuideDialog{
        this.cancelAble = cancelAble
        return instance!!
    }

    @Synchronized fun show(manager: FragmentManager, allowStateLose : Boolean) {
        if (instance == null || manager == null) {
            LogUtil.log(TAG, "show function not execute!")
            return
        }
        if (!instance!!.allowShow) {
            return
        }
        instance?.allowShow = false
        if (allowStateLose) {
            showAllowStateLoss(manager)
        } else {
            showNotAllowStateLoss(manager)
        }
        LogUtil.log(TAG, "show function execute!")
    }

    interface IClickInterface{
        fun onLeftChoosed(dialog : MainGuideDialog)
        fun onRightChoosed(dialog : MainGuideDialog)
    }

    override fun getContentViewId(): Int {
        return R.layout.dialog_main_guide
    }

}