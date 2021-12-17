package com.xwq.companyvxwhelper.widget

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView.BufferType
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.*
import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.databinding.WidgetUserInputVerifycodeBinding
import com.xwq.companyvxwhelper.listener.NoDoubleClickListener
import com.xwq.companyvxwhelper.mvvm.activity.RegistActivity
import com.xwq.companyvxwhelper.service.TimeCutDownService
import com.xwq.companyvxwhelper.utils.ServiceUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
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

    val PRE_CUT_DOWN_TIME = 1
    var hintText : String? = ""
    var inputtext : String? = ""
    var clicktext : String? = ""
    var clickAble : Boolean = false

    lateinit var editInput : AppCompatEditText
    lateinit var showACTV : AppCompatTextView

    var onIHintTextChange : OnIHintTextChangeListener? = null
    var onIClickTextListener : OnIClickTextListener? = null
    var onIClickAbleChange : OnIClickAbleListener? = null

    var observer : ParcelabelObserver = ParcelabelObserver()

    constructor(context : Context) : this(context, null) {
    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr : Int) : super(context, attributeSet, defStyleAttr) {
        init(attributeSet)
    }

    interface OnIHintTextChangeListener {
        fun onIHintTextChange()
    }

    interface OnIClickTextListener {
        fun onIClickTextChange()
    }

    interface OnIClickAbleListener {
        fun onIClickAbleChange()
    }

    class ParcelabelObserver() : Observer<String>,Parcelable {


        constructor(parcel: Parcel) : this() {
        }

        override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: String) {
        var leftOverTime = t.toLong()
        appCompatActivity?.runOnUiThread {
            showLeftOverTime(leftOverTime)
        }
    }

     override fun onError(e: Throwable) {

    }

    override fun onComplete() {

    }

    override fun describeContents(): Int {
        return 0;
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {

    }

      private fun showLeftOverTime(leftOverTime : Long) {
          val preMessage = MyApplication.app.getString(R.string.re_rend_message)
          val preSecond = MyApplication.app.getString(R.string.second)
          if (leftOverTime > 0) {
              setIClickText(userInputVerifyCodeEditView!!, preMessage.replace("x", leftOverTime.toString()) + preSecond)
          } else {
              setIClickText(userInputVerifyCodeEditView!!, MyApplication.app.getString(R.string.get_verifycode))
          }
      }

        companion object CREATOR : Parcelable.Creator<ParcelabelObserver> {
            override fun createFromParcel(parcel: Parcel): ParcelabelObserver {
                return ParcelabelObserver(parcel)
            }

            override fun newArray(size: Int): Array<ParcelabelObserver?> {
                return arrayOfNulls(size)
            }
        }
    }

    companion object {
        var appCompatActivity : AppCompatActivity? = null
        var userInputVerifyCodeEditView: UserInputVerifyCodeEditView? = null
        @BindingAdapter("iHintText")
        @JvmStatic
        fun setIHintText(userInputVerifyCodeEditView: UserInputVerifyCodeEditView, hintText : String) {
            if(!hintText.isNullOrEmpty()) {
                userInputVerifyCodeEditView.hintText = hintText
                userInputVerifyCodeEditView.editInput.hint = hintText
            }
        }

        @BindingAdapter("iInputText")
        @JvmStatic
        fun setIInputText(userInputVerifyCodeEditView: UserInputVerifyCodeEditView, inputtext : String) {
            if (!inputtext.isNullOrEmpty()) {
                userInputVerifyCodeEditView.inputtext = inputtext
                userInputVerifyCodeEditView.editInput.setText(inputtext,BufferType.EDITABLE)
            }
        }

        @InverseBindingAdapter(attribute = "iInputText", event = "iInputTextChanged")
        @JvmStatic
        fun getIInputText(userInputVerifyCodeEditView: UserInputVerifyCodeEditView) : String{
            return userInputVerifyCodeEditView.editInput.text.toString()
        }

        @BindingAdapter(value = ["iInputText", "iInputTextChanged"], requireAll = false)
        @JvmStatic
        fun setOnIHintTextChangeListener(userInputVerifyCodeEditView: UserInputVerifyCodeEditView, onIHintTextChange : OnIHintTextChangeListener?,
            inverseBindingListener: InverseBindingListener) {
            if (inverseBindingListener == null) {
                userInputVerifyCodeEditView.onIHintTextChange = onIHintTextChange
            } else {
                if (onIHintTextChange == null) {
                    userInputVerifyCodeEditView.onIHintTextChange =
                        object : OnIHintTextChangeListener {
                            override fun onIHintTextChange() {
                                if (onIHintTextChange != null) {
                                    onIHintTextChange.onIHintTextChange()
                                } else {
                                    inverseBindingListener.onChange()
                                }
                            }
                        }
                }
            }
        }

        @BindingAdapter("iClickText")
        @JvmStatic
        fun setIClickText(userInputVerifyCodeEditView: UserInputVerifyCodeEditView, iClickText : String) {
            if (!iClickText.isNullOrEmpty()) {
                userInputVerifyCodeEditView.clicktext = iClickText
                userInputVerifyCodeEditView.showACTV.setText(iClickText, BufferType.EDITABLE)
            }
        }

        @InverseBindingAdapter(attribute = "iClickText", event = "iClickTextChanged")
        @JvmStatic
        fun getIClickText(userInputVerifyCodeEditView: UserInputVerifyCodeEditView) : String{
            return userInputVerifyCodeEditView.showACTV.text.toString()
        }

        @BindingAdapter(value = ["iClickText", "iClickTextChanged"], requireAll = false)
        fun setOnIClickTextListener(userInputVerifyCodeEditView: UserInputVerifyCodeEditView, onIClickTextListener: OnIClickTextListener,
        inverseBindingListener: InverseBindingListener) {
            if (inverseBindingListener == null) {
                userInputVerifyCodeEditView.onIClickTextListener = onIClickTextListener
            } else {
                userInputVerifyCodeEditView.onIClickTextListener = object : OnIClickTextListener {
                    override fun onIClickTextChange() {
                        if (onIClickTextListener != null) {
                            onIClickTextListener.onIClickTextChange()
                        } else {
                            inverseBindingListener.onChange()
                        }
                    }
                }
            }
        }

        @BindingAdapter("iClickAble")
        @JvmStatic
        fun setIClickAble(userInputVerifyCodeEditView: UserInputVerifyCodeEditView, iClickAble : Boolean) {
            userInputVerifyCodeEditView.clickAble = iClickAble
            userInputVerifyCodeEditView.showACTV.isClickable = iClickAble
        }

        @InverseBindingAdapter(attribute = "iClickAble", event = "iClickAbleChanged")
        @JvmStatic
        fun getIClickAble(userInputVerifyCodeEditView: UserInputVerifyCodeEditView) : Boolean {
            return userInputVerifyCodeEditView.clickAble
        }

        @BindingAdapter(value = ["iClickAble", "iClickAbleChanged"], requireAll = false)
        @JvmStatic
        fun setOnIClickAbleListener(userInputVerifyCodeEditView: UserInputVerifyCodeEditView, onIClickAbleListener: OnIClickAbleListener?,
        inverseBindingListener: InverseBindingListener) {
            if (inverseBindingListener == null) {
                userInputVerifyCodeEditView.onIClickAbleChange = onIClickAbleListener
            } else {
                userInputVerifyCodeEditView.onIClickAbleChange = object : OnIClickAbleListener {
                    override fun onIClickAbleChange() {
                        if (onIClickAbleListener != null) {
                            onIClickAbleListener.onIClickAbleChange()
                        } else {
                            inverseBindingListener.onChange()
                        }
                    }
                }
            }
        }

    }

    private fun init(attributeSet: AttributeSet?) {
        var array : TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.UserInputVertifyView)
        hintText = array.getString(R.styleable.UserInputVertifyView_iHintText)
        inputtext = array.getString(R.styleable.UserInputVertifyView_iInputText)
        clicktext = array.getString(R.styleable.UserInputVertifyView_iClickText)
        clickAble = array.getBoolean(R.styleable.UserInputVertifyView_iClickAble, false)
        array.recycle()

        var viewDataBinding : WidgetUserInputVerifycodeBinding = DataBindingUtil.inflate<WidgetUserInputVerifycodeBinding>(LayoutInflater.from(context), R.layout.widget_user_input_verifycode, this, true)
        if (context is AppCompatActivity) {
            appCompatActivity = context as AppCompatActivity
        }
        userInputVerifyCodeEditView = this
        if (!ServiceUtils.isTargetServiceRunning(TimeCutDownService::class.java.name)) {
            dispatchMessage(true)
        }
        initView(viewDataBinding)
    }

    private fun initView(viewDataBinding : WidgetUserInputVerifycodeBinding) {
        editInput = WidgetUserInputVerifycodeBinding.inflate(LayoutInflater.from(context)).widgetInputVerifyAcetInput
        showACTV = WidgetUserInputVerifycodeBinding.inflate(LayoutInflater.from(context)).widgetUserTelorpassActvInput

        initListener()
    }

    private fun initListener() {
        editInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        showACTV.setOnClickListener(object : NoDoubleClickListener{
            override fun onClick(v: View?) {
                super.onClick(v)
                if (getIClickAble(this@UserInputVerifyCodeEditView)) {
                    if (appCompatActivity is RegistActivity && (appCompatActivity as RegistActivity).getSmsVerifyCode()) {
                        dispatchMessage(false)
                    }
                }
            }
        })
    }

    private fun dispatchMessage(isInit : Boolean) {
        var surplusTime = PRE_CUT_DOWN_TIME

        context.startService(Intent(context, TimeCutDownService::class.java)
            .putExtra("ISINIT", isInit)
            .putExtra("PRECUTDOWNTIME", surplusTime)
            .putExtra("OBSERVER", observer))

    }

}