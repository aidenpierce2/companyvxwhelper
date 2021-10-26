package com.xwq.companyvxwhelper.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.red
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import com.xwq.companyvxwhelper.R

class RedDotRadioButton : AppCompatRadioButton {

    var shouldShowDots : Boolean = false
    private var dot : Dot? = null

    constructor(context: Context) : super(context) {
        AppCompatRadioButton(context, null)
    }

    constructor(context: Context,attributeSet: AttributeSet) : super(context, attributeSet) {
        AppCompatRadioButton(context, attributeSet, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr : Int) :super(context){
        init()
    }

    private fun init() {
        shouldShowDots = context.theme.resolveAttribute(R.attr.showDots, TypedValue(), true)
        dot = Dot()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (shouldShowDots) {
            var cx = (width - dot!!.radius).toFloat()
            var cy = dot!!.radius.toFloat() + dot!!.marginTop.toFloat()

            val drawableTop = compoundDrawables[1]
            if (drawableTop != null) {
                val drawableTopWidth = drawableTop.intrinsicWidth
                if (drawableTopWidth > 0) {
                    var dotLeft = width / 2 + drawableTopWidth / 2 + dot!!.radius
                    cx =dotLeft.toFloat() + dot!!.marginLeft
                }
            }

            val paint = paint
            // save
            val tempColor = paint.color

            paint.color = dot!!.color
            paint.style = Paint.Style.FILL
            canvas!!.drawCircle(cx, cy, dot!!.radius.toFloat(), paint)

            paint.color = tempColor
        }
    }

    private inner class Dot constructor() {
        // 颜色
        var color : Int = 0
        // 半径
        var radius : Int = 0
        // 距离顶部距离
        var marginTop : Int = 0
        // 距离左边距离
        var marginLeft : Int = 0

        init {
            var density = context.resources.displayMetrics.density

            radius = (5 * density).toInt()
            marginTop = (5 * density).toInt()
            marginLeft = (5 * density).toInt()

            color = Color.RED
        }
    }

}