package com.xwq.companyvxwhelper.widget

import android.content.Context
import android.content.res.TypedArray
import android.text.*
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView.BufferType
import androidx.annotation.ColorInt
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

    var hintText : String? = ""
    var inputtext : String? = ""
    var showClear : Boolean = false
    var inputStyle : Int = 0

    lateinit var editInput : AppCompatEditText
    lateinit var clearIcon : AppCompatImageView
    var onTHintListener : onTHintTextChangeListener? = null
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

    interface onTHintTextChangeListener {
        fun onTHintTextChange()
    }

    companion object {
        @BindingAdapter("tHintText")
        @JvmStatic
        fun setTHintText(userTelOrPassInputEditView: UserTelOrPassInputEditView, hintText : String) {
            if (!hintText.isNullOrEmpty()) {
                userTelOrPassInputEditView.hintText = hintText
                userTelOrPassInputEditView.editInput.hint = hintText
            }
        }

        @BindingAdapter("tInputText")
        @JvmStatic
        fun setTInputText(userTelOrPassInputEditView: UserTelOrPassInputEditView, inputtext : String) {
            if (!inputtext.isNullOrEmpty()) {
                userTelOrPassInputEditView.inputtext = inputtext
                userTelOrPassInputEditView.editInput.setText(inputtext, BufferType.EDITABLE)
            }
        }

        @InverseBindingAdapter(attribute = "tInputText", event = "tInputTextAttrChange")
        @JvmStatic
        fun getTInputText(userTelOrPassInputEditView: UserTelOrPassInputEditView) : String{
            return userTelOrPassInputEditView.editInput.text.toString()
        }

        @BindingAdapter(value = ["onTHintTextChange", "tInputTextAttrChange"], requireAll = false)
        @JvmStatic
        fun setOnTHintTextChangeListener(userTelOrPassInputEditView: UserTelOrPassInputEditView, onTHintListener : onTHintTextChangeListener?,
            inverseBindingListener : InverseBindingListener) {
            if(inverseBindingListener == null) {
                userTelOrPassInputEditView.onTHintListener = onTHintListener
            } else {
                userTelOrPassInputEditView.onTHintListener = object : onTHintTextChangeListener {
                    override fun onTHintTextChange() {
                        if (onTHintListener != null) {
                            onTHintListener.onTHintTextChange()
                        } else {
                            inverseBindingListener.onChange()
                        }
                    }
                }
            }
        }
    }

    fun getTHintText() : String? {
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

    fun getTInputText() : String? {
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
        inputStyle = array.getInt(R.styleable.UserTelOrPassView_tInputStyle, 0)
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

        editInput.setHintTextColor(resources.getColor(R.color.inputHint))
        editInput.setTextColor(resources.getColor(R.color.contentColor))
        when (inputStyle) {
            0 -> {editInput.inputType = InputType.TYPE_CLASS_NUMBER;editInput.filters = arrayOf(
                InputFilter.LengthFilter(11))
            }
            1 -> {editInput.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD; editInput.transformationMethod = PasswordTransformationMethod()}
        }
        if (showClear) {
            clearIcon.visibility = View.VISIBLE
        } else {
            clearIcon.visibility = View.GONE
        }
        setTShowClear(showClear)

        initListener()
    }

    private fun initListener() {
        editInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                this@UserTelOrPassInputEditView.onTHintListener?.onTHintTextChange()
                var curText = s.toString()
                if (curText.isNullOrEmpty()) {
                    clearIcon.visibility = View.GONE
                    setTShowClear(true)
                } else {
                    clearIcon.visibility = View.VISIBLE
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