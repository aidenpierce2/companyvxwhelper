package com.xwq.companyvxwhelper.widget

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.*
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.listener.NoDoubleClickListener
import kotlinx.android.synthetic.main.widget_user_telorpass.view.*

@InverseBindingMethods(
    InverseBindingMethod(
        type = UserTelOrPassInputEditView::class,
        attribute = "tHintText",
        event = "tHintTextAttrChange"
    ),
    InverseBindingMethod(
        type = UserTelOrPassInputEditView::class,
        attribute = "tInputText",
        event = "tInputTextAttrChange"
    ),
    InverseBindingMethod(
        type = UserTelOrPassInputEditView::class,
        attribute = "tShowClear",
        event = "tShowClearAttrChange"
    )
)
class UserTelOrPassInputEditView : ConstraintLayout {

    var hintText : String = ""
    var inputtext : String = ""
    var showClear : Boolean = false
    var inputStyle : Float = 0F

    lateinit var editInput : AppCompatEditText
    lateinit var clearIcon : AppCompatImageView
    lateinit var tHintTextAttrChangeListener : InverseBindingListener
    lateinit var tInputTextAttrChangeListener : InverseBindingListener
    lateinit var tShowClearAttrChangeListener : InverseBindingListener

    constructor(context : Context) : this(context, null) {
    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr :  Int) : super(context, attributeSet, defStyleAttr) {
        init(attributeSet)
    }

    fun setTHintText(hintText : String) {
        if (!this.hintText.equals(hintText)) {
            this.hintText = hintText
        }
    }

    fun getTHintText() : String {
        return this.hintText
    }

    fun setTHintTextAttrChange(tHintTextAttrChangeListener : InverseBindingListener ) {
        if (tHintTextAttrChangeListener != null) {
            this.tHintTextAttrChangeListener = tHintTextAttrChangeListener
        }
    }

    fun setTInputText(tInputText : String) {
        if (!this.inputtext.equals(tInputText)) {
            this.inputtext = tInputText
        }
    }

    fun getTInputText() : String {
        return this.inputtext
    }

    fun setTInputTextAttrChange(tInputTextAttrChangeListener : InverseBindingListener ) {
        if (tInputTextAttrChangeListener != null) {
            this.tInputTextAttrChangeListener = tInputTextAttrChangeListener
        }
    }

    fun setTShowClear(showClear : Boolean) {
        if (this.showClear != showClear) {
            this.showClear = showClear
        }
    }

    fun getTShowClear() : Boolean {
        return this.showClear
    }

    fun setTShowClearAttrChange(tShowClearAttrChangeListener : InverseBindingListener ) {
        if (tShowClearAttrChangeListener != null) {
            this.tShowClearAttrChangeListener = tShowClearAttrChangeListener
        }
    }

    private fun init(attributeSet: AttributeSet?) {
        var array : TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.UserTelOrPassView)
        inputStyle = array.getDimension(R.styleable.UserTelOrPassView_tInputStyle, 0F)
        hintText = array.getString(R.styleable.UserTelOrPassView_tHintText)
        inputtext = array.getString(R.styleable.UserTelOrPassView_tInputText)
        showClear = array.getBoolean(R.styleable.UserTelOrPassView_tShowClear, false)
        array.recycle()

        LayoutInflater.from(context).inflate(R.layout.widget_user_telorpass, this@UserTelOrPassInputEditView)
        initView()
    }

    private fun initView() {
        editInput = widget_user_telorpass_acet_input
        clearIcon = widget_user_telorpass_aciv_delete

        when (inputStyle) {
            0F -> {editInput.inputType = InputType.TYPE_CLASS_NUMBER}
            1F -> {editInput.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD}
        }

        initListener()
    }

    private fun initListener() {
        editInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                var curText = s.toString()
                if (TextUtils.isEmpty(curText)) {
                    clearIcon.visibility = View.VISIBLE
                    setTShowClear(true)
                } else {
                    clearIcon.visibility = View.GONE
                    setTShowClear(false)
                }
                setTInputText(s.toString())
            }
        })

        clearIcon.setOnClickListener(object : NoDoubleClickListener{
            override fun onClick(v: View?) {
                super.onClick(v)
                setTInputText("")
            }
        })
    }


}