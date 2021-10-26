package com.xwq.companyvxwhelper.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.xwq.companyvxwhelper.R

class RadiusCardView : CardView {

    var topLeftRadius : Float  = 0F
    var topRightRadius : Float = 0F
    var bottomLeftRadius : Float = 0F
    var bottomRightRadius : Float = 0F

    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        radius = 0F
        init(attributeSet)
    }

    fun init(attributeSet: AttributeSet?) {
        var array : TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.RadiusCardView)
        topLeftRadius = array.getDimension(R.styleable.RadiusCardView_rcTopLeftRadius, 0F)
        topRightRadius = array.getDimension(R.styleable.RadiusCardView_rcTopRightRadius, 0F)
        bottomLeftRadius = array.getDimension(R.styleable.RadiusCardView_rcBottomLeftRadius, 0F)
        bottomRightRadius = array.getDimension(R.styleable.RadiusCardView_rcBottomRightRadius, 0F)
        array.recycle()
        background = ColorDrawable()
    }

    override fun onDraw(canvas: Canvas?) {
        var path = Path()
        val readius = floatArrayOf(topLeftRadius, topLeftRadius,
            topRightRadius, topRightRadius,
            bottomLeftRadius, bottomLeftRadius,
            bottomRightRadius, bottomRightRadius
        )
        path.addRoundRect(getRectF(), readius, Path.Direction.CW)
        canvas!!.clipPath(path, Region.Op.INTERSECT)
        super.onDraw(canvas)
    }

    fun getRectF() : RectF {
        var rect = Rect()
        getDrawingRect(rect)
        return RectF(rect)

    }

}