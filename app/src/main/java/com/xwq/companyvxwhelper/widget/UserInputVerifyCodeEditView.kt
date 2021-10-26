package com.xwq.companyvxwhelper.widget

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.*
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.bean.EventBusMessageTypeBean
import com.xwq.companyvxwhelper.listener.NoDoubleClickListener
import com.xwq.companyvxwhelper.service.TimeCutDownService
import kotlinx.android.synthetic.main.widget_user_input_verifycode.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

@InverseBindingMethods(
    InverseBindingMethod(
        type = UserInputVerifyCodeEditView::class,
        attribute = "iHintText",
        event = "iHintTextChanged"
    ),
    InverseBindingMethod(
        type = UserInputVerifyCodeEditView::class,
        attribute = "iInputText",
        event = "iInputTextChanged"
    ),
    InverseBindingMethod(
        type = UserInputVerifyCodeEditView::class,
        attribute = "iClickText",
        event = "iClickTextChanged"
    ),
    InverseBindingMethod(
        type = UserInputVerifyCodeEditView::class,
        attribute = "iClickAble",
        event = "iClickAbleChanged"
    )
)
class UserInputVerifyCodeEditView : ConstraintLayout {

    val TIME_INTERVAL = 1
    val MAX_TIME = 60
    var hintText : String = ""
    var inputtext : String = ""
    var clicktext : String = ""
    var clickAble : Boolean = false

    lateinit var editInput : AppCompatEditText
    lateinit var showACTV : AppCompatTextView

    lateinit var iHintTextChangedListener : InverseBindingListener
    lateinit var iInputTextChangedListener : InverseBindingListener
    lateinit var iClickTextChangedListener : InverseBindingListener
    lateinit var iClickAbleChangedListener : InverseBindingListener

    var eventBus : EventBus? = null

    constructor(context : Context) : this(context, null) {
    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr : Int) : super(context, attributeSet, defStyleAttr) {
        init(attributeSet)
    }

    fun setIHintText(hintText : String) {
        if(!this.hintText.equals(hintText)) {
            this.hintText = hintText
        }
    }

    fun getIHintText() : String{
        return this.hintText
    }

    fun setIHintTextChanged(iHintTextChangedListener : InverseBindingListener) {
        if (iHintTextChangedListener != null) {
            this.iHintTextChangedListener = iHintTextChangedListener
        }
    }

    fun setIInputText(inputtext : String) {
        if(!this.inputtext.equals(inputtext)) {
            this.inputtext = inputtext
        }
    }

    fun getIInputText() : String{
        return this.inputtext
    }

    fun setIInputTextChanged(iInputTextChangedListener : InverseBindingListener) {
        if (iInputTextChangedListener != null) {
            this.iInputTextChangedListener = iInputTextChangedListener
        }
    }

    fun setIClickText(clicktext : String) {
        if(!this.clicktext.equals(clicktext)) {
            this.clicktext = clicktext
        }
    }

    fun getIClickText() : String{
        return this.clicktext
    }

    fun setIClickTextChanged(iClickTextChangedListener : InverseBindingListener) {
        if (iClickTextChangedListener != null) {
            this.iClickTextChangedListener = iClickTextChangedListener
        }
    }

    fun setIClickAble(clickAble : Boolean) {
        if(this.clickAble != clickAble) {
            this.clickAble = clickAble
        }
    }

    fun getIClickAble() : Boolean{
        return this.clickAble
    }

    fun setIClickAbleChanged(iClickAbleChangedListener : InverseBindingListener) {
        if (iClickAbleChangedListener != null) {
            this.iClickAbleChangedListener = iClickAbleChangedListener
        }
    }


    private fun init(attributeSet: AttributeSet?) {
        var array : TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.UserInputVertifyView)
        hintText = array.getString(R.styleable.UserInputVertifyView_iHintText)
        inputtext = array.getString(R.styleable.UserInputVertifyView_iInputText)
        clicktext = array.getString(R.styleable.UserInputVertifyView_iClickText)
        clickAble = array.getBoolean(R.styleable.UserInputVertifyView_iClickAble, false)
        array.recycle()

        LayoutInflater.from(context).inflate(R.layout.widget_user_input_verifycode, this@UserInputVerifyCodeEditView)
        initView()
    }

    private fun initView() {
        editInput = widget_input_verify_acet_input
        showACTV = widget_user_telorpass_actv_input

        initListener()
    }

    private fun initListener() {
        editInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                setIInputText(s.toString())
            }
        })

        if (getIClickAble()) {
            showACTV.setOnClickListener(object : NoDoubleClickListener{
                override fun onClick(v: View?) {
                    super.onClick(v)
                    setIClickAble(false)
                    registerEventBus()
                    dispatchMessage()
                }
            })
        }
    }

    private fun registerEventBus() {
        if (eventBus == null) {
            eventBus = EventBus()
        }
        eventBus?.register(this)
    }

    private fun unRegisterEventBus() {
        eventBus?.unregister(this)
    }

    private fun dispatchMessage() {
        var preMessage = context.getString(R.string.re_rend_message)
        val posMessage = context.getString(R.string.second)
        var surplusTime = MAX_TIME
        var curTime = TIME_INTERVAL

        context.startService(Intent(context, TimeCutDownService::class.java)
            .putExtra("STARTTIME", surplusTime)
            .putExtra("PRECUTDOWNTIME", curTime))
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    private fun onMessageEvent(event: EventBusMessageTypeBean) {
        when (event.eventBusMessageTypeBeanEnum) {
            EventBusMessageTypeBean.EventBusMessageTypeBeanEnum.CUTDOWNTIME -> {setIClickText(event.getLong(TimeCutDownService::class.java.simpleName).toString())}
            EventBusMessageTypeBean.EventBusMessageTypeBeanEnum.CUTDOWNEND -> {unRegisterEventBus();setIClickAble(true)}
        }
    }

}