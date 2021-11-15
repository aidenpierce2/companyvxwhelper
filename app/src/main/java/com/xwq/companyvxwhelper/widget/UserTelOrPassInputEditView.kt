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
        var userTelOrPassInputEditView : UserTelOrPassInputEditView? = null
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

        @BindingAdapter("tShowClear")
        @JvmStatic
        fun setTShowClear(userTelOrPassInputEditView: UserTelOrPassInputEditView, showClear : Boolean) {
            if (userTelOrPassInputEditView != null) {
                if (showClear) {
                    userTelOrPassInputEditView.clearIcon.visibility = View.VISIBLE
                } else {
                    userTelOrPassInputEditView.clearIcon.visibility = View.GONE
                }
            }
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
        userTelOrPassInputEditView = this@UserTelOrPassInputEditView
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
        setTShowClear(userTelOrPassInputEditView!!, showClear)

        initListener()
    }

    private fun initListener() {
        userTelOrPassInputEditView!!.editInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                var curText = s.toString()
                if (curText.isNullOrEmpty()) {
                    setTShowClear(userTelOrPassInputEditView!!, false)
                } else {
                    setTShowClear(userTelOrPassInputEditView!!, true)
                }
            }
        })

        userTelOrPassInputEditView!!.clearIcon.setOnClickListener(object : NoDoubleClickListener{
            override fun onClick(v: View?) {
                super.onClick(v)
                setTInputText(userTelOrPassInputEditView!!,"")
            }
        })
    }


}