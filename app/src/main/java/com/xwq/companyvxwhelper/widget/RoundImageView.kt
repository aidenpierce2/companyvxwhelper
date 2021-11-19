package com.xwq.companyvxwhelper.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.nineoldandroids.view.ViewHelper.getScrollX
import com.nineoldandroids.view.ViewHelper.getScrollY
import com.xwq.companyvxwhelper.R


class RoundImageView : AppCompatImageView {
    var paint : Paint? = null
    var curMatrix : Matrix? = null

    constructor(context: Context) : this(context, null) {

    }
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

    }
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr : Int) :super(context, attributeSet, defStyleAttr) {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        if (drawable is BitmapDrawable) {
            var bitMap = (drawable as BitmapDrawable).bitmap
            clipToRound(canvas, bitMap)
        } else {
            super.onDraw(canvas)
        }
    }

    fun init() {
        if (paint == null) {
            paint = Paint()
        }
        paint?.isAntiAlias = true
        if (curMatrix == null) {
            curMatrix = Matrix()
        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun clipToRound(canvas: Canvas?, bitmap: Bitmap) {
        var bitmapWidth = bitmap.width
        var bitmapHeight = bitmap.height

        var canvasWidth : Int = canvas?.width!!
        var canvasHeight : Int = canvas?.height!!

        var minMetrix = Math.min(bitmapWidth/canvasWidth, bitmapHeight/canvasHeight)
        curMatrix?.reset()
        curMatrix?.setScale(minMetrix.toFloat(), minMetrix.toFloat())

        var newBitMap = Bitmap.createBitmap(bitmap, 0, 0,bitmapWidth,bitmapHeight,curMatrix,true)
        var layerId : Int = canvas?.saveLayer(0F, 0F, canvasWidth.toFloat(), canvasHeight.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        canvas.drawCircle((canvasWidth / 2).toFloat(),
            (canvasHeight / 2).toFloat(), Math.min(canvasWidth / 2, bitmapWidth / 2).toFloat(), paint)
        paint?.reset()
        paint?.isAntiAlias = true
        paint?.color = Color.WHITE
        paint?.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        canvas.drawBitmap(newBitMap, ((canvasWidth - newBitMap.width) / 2).toFloat(), ((canvasHeight - newBitMap.height) / 2).toFloat(), paint)
        paint?.setXfermode(null)
        canvas?.restoreToCount(layerId)
    }
}