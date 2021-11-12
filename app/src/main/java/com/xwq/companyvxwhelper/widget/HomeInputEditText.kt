package com.xwq.companyvxwhelper.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.listener.NoDoubleClickListener
import com.xwq.companyvxwhelper.utils.ColorTranslate
import com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment.SearchLocationDialog

class HomeInputEditText: ConstraintLayout, NoDoubleClickListener {
    val TAG : String = HomeInputEditText::class.java.simpleName.toString()
    var container : View? = null

    //左侧icon的大小
    val left_icon_width : Int = 25
    val left_icon_height : Int = 25
    // 中间edittext的大小
    var center_edit_width : Int = 0
    var center_edit_height : Int = 0
    //右侧button的大小
    var right_button_width : Int = 0
    val right_button_height : Int = 35
    val iAEmargin : Int = 5
    val eABMargin : Int = 5
    val strLARPadding : Int = 10
    val right_button_size = 16F

    //左侧imageview
    lateinit var leftACIV : AppCompatImageView
    //中间edittext
    lateinit var centerACET : AppCompatEditText
    //右侧button
    lateinit var rightACBT : AppCompatButton


    var leftImage : Int? = null
    var centerHint : String? = ""
    var centerHintColor : String? = ""
    var centerText : String? = ""
    var centerStrColor : String? = ""
    var rightStr : String? = ""
    var rightStrColor : String? = ""
    var listener : onButtonClickListener? = null

    constructor(context: Context) : this(context, null){
    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0){
    }

    constructor(context: Context,attributeSet: AttributeSet?,defStyleAttr : Int) : super(context, attributeSet, defStyleAttr){
        init(attributeSet)
    }

    fun init(attributeSet: AttributeSet?) {
        var array : TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.HomeInputEditText)
        leftImage = array.getResourceId(R.styleable.HomeInputEditText_leftImage, 0)
        centerHint = array.getString(R.styleable.HomeInputEditText_centerHint)
        centerHintColor = array.getString(R.styleable.HomeInputEditText_centerHintColor)
        centerText = array.getString(R.styleable.HomeInputEditText_centerText)
        centerStrColor = array.getString(R.styleable.HomeInputEditText_centerTextColor)
        rightStr = array.getString(R.styleable.HomeInputEditText_rightText)
        rightStrColor = array.getString(R.styleable.HomeInputEditText_rightTextColor)
        array.recycle()

        initView()
    }

    fun initView() {
        container = LayoutInflater.from(context).inflate(R.layout.widget_location_search, this,true)
        leftACIV = container!!.findViewById(R.id.widget_home_input_aciv_search)
        centerACET = container!!.findViewById(R.id.widget_home_input_aciv_input)
        rightACBT = container!!.findViewById(R.id.widget_home_input_acbt_input)

        if (centerHintColor.isNullOrEmpty()) {
            centerHintColor = ColorTranslate.tranColorIntToStr(R.color.inputHint)
        }
        if (centerStrColor.isNullOrEmpty()) {
            centerStrColor = ColorTranslate.tranColorIntToStr(R.color.input)
        }
        if (rightStrColor.isNullOrEmpty()) {
            rightStrColor = ColorTranslate.tranColorIntToStr(R.color.searchColor)
        }

        centerACET!!.setText(centerText)
        leftACIV!!.setImageResource(leftImage!!)
        centerACET!!.textSize = 14F
        centerACET!!.setHintTextColor(Color.parseColor(centerHintColor!!))
        centerACET!!.setTextColor(Color.parseColor(centerStrColor!!))
        centerACET!!.hint = centerHint
        rightACBT!!.setTextColor(Color.parseColor(rightStrColor!!))
        rightACBT!!.text = rightStr

        initListener()
    }

    fun getEnterText() : String{
        return centerACET.text.toString()
    }

    fun initListener() {
        rightACBT.setOnClickListener(object : NoDoubleClickListener {

            override fun noDoubleClick(v: View?) {
                super.noDoubleClick(v)
                if (listener != null) {
                    listener!!.onButtonClick()
                }
            }
        });
    }

    fun setOnButtonClickListener(listener : onButtonClickListener) {
        this@HomeInputEditText.listener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

    }

    interface onButtonClickListener {
        fun onButtonClick()
    }

}
