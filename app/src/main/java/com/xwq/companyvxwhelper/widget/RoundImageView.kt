package com.xwq.companyvxwhelper.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.nineoldandroids.view.ViewHelper.getScrollX
import com.nineoldandroids.view.ViewHelper.getScrollY
import com.xwq.companyvxwhelper.R


class RoundImageView : AppCompatImageView {
    enum class RoundMode {
        ROUND_VIEW, ROUND_DRAWABLE
    }

    private var roundDisable = false
    private var roundMode = RoundMode.ROUND_DRAWABLE
    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH
    private var fillColor = DEFAULT_FILL_COLOR
    private var borderPaint: Paint? = null
    private var fillPaint: Paint? = null
    private var imagePaint: Paint? = null
    private var portPaint: Paint? = null
    private val bounds: Rect = Rect()
    private var radius = 0f
    private var cx = 0f
    private var cy = 0f

    constructor(context: Context?) : this(context, null) {

    }

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {

    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        portPaint = Paint()
        portPaint!!.setAntiAlias(true)
        borderPaint = Paint()
        borderPaint!!.setAntiAlias(true)
        borderPaint!!.setColor(DEFAULT_BORDER_COLOR)
        borderPaint!!.setStrokeWidth(DEFAULT_BORDER_WIDTH.toFloat())
        borderPaint!!.setStyle(Paint.Style.STROKE)
        fillPaint = Paint()
        fillPaint!!.setAntiAlias(true)
        fillPaint!!.setColor(DEFAULT_FILL_COLOR)
        fillPaint!!.setStyle(Paint.Style.FILL)
        imagePaint = Paint()
        imagePaint!!.setAntiAlias(true)
        imagePaint!!.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
    }

    fun setRoundMode(roundMode: RoundMode?) {
        requireNotNull(roundMode) { "roundMode is null." }
        if (this.roundMode != roundMode) {
            this.roundMode = roundMode
            invalidate()
        }
    }

    fun setRoundDisable(roundDisable: Boolean) {
        if (this.roundDisable != roundDisable) {
            this.roundDisable = roundDisable
            invalidate()
        }
    }

    fun isRoundDisable(): Boolean {
        return roundDisable
    }

    fun setBorderColor(borderColor: Int) {
        if (this.borderColor != borderColor) {
            this.borderColor = borderColor
            borderPaint!!.setColor(borderColor)
            invalidate()
        }
    }

    fun setBorderWidth(borderWidth: Int) {
        if (this.borderWidth != borderWidth) {
            this.borderWidth = borderWidth
            borderPaint!!.setStrokeWidth(borderWidth.toFloat())
            invalidate()
        }
    }

    fun setFillColor(fillColor: Int) {
        if (this.fillColor != fillColor) {
            this.fillColor = fillColor
            fillPaint!!.setColor(fillColor)
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (roundDisable) {
            super.onDraw(canvas)
            return
        }
        if (getDrawable() == null && roundMode == RoundMode.ROUND_DRAWABLE) {
            super.onDraw(canvas)
            return
        }
        computeRoundBounds()
        drawCircle(canvas)

        val src = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444)
        super.onDraw(Canvas(src))
        var port = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444)
        var portCanvas = Canvas(port)
        var saveCount: Int = portCanvas.getSaveCount()
        portCanvas.save()
        adjustCanvas(portCanvas)
        portCanvas.drawCircle(cx, cy, radius, portPaint)
        portCanvas.restoreToCount(saveCount)
        portCanvas.drawBitmap(src, 0f, 0f, imagePaint)
        src.recycle()
        canvas.drawBitmap(port, 0f, 0f, null)
        port.recycle()
    }

    private fun drawCircle(canvas: Canvas) {
        val saveCount: Int = canvas.getSaveCount()
        canvas.save()
        adjustCanvas(canvas)
        canvas.drawCircle(cx, cy, radius, fillPaint)
        if (borderWidth > 0) {
            canvas.drawCircle(cx, cy, radius - borderWidth / 2f, borderPaint)
        }
        canvas.restoreToCount(saveCount)
    }

    private fun computeRoundBounds() {
        if (roundMode == RoundMode.ROUND_VIEW) {
            bounds.left = getPaddingLeft()
            bounds.top = getPaddingTop()
            bounds.right = getWidth() - getPaddingRight()
            bounds.bottom = getHeight() - getPaddingBottom()
        } else if (roundMode == RoundMode.ROUND_DRAWABLE) {
            getDrawable().copyBounds(bounds)
        } else {
            throw RuntimeException("unknown round mode:$roundMode")
        }
        radius = Math.min(bounds.width(), bounds.height()) / 2f
        cx = bounds.left + bounds.width() / 2f
        cy = bounds.top + bounds.height() / 2f
    }

    private fun adjustCanvas(canvas: Canvas) {
        if (roundMode == RoundMode.ROUND_DRAWABLE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (getCropToPadding()) {
                    val scrollX = getScrollX().toInt()
                    val scrollY = getScrollY().toInt()
                    canvas.clipRect(
                        scrollX + getPaddingLeft(), scrollY + getPaddingTop(),
                        scrollX + getRight() - getLeft() - getPaddingRight(),
                        scrollY + getBottom() - getTop() - getPaddingBottom()
                    )
                }
            }
            canvas.translate(getPaddingLeft().toFloat(), getPaddingTop().toFloat())
            if (getImageMatrix() != null) {
                val m = Matrix(getImageMatrix())
                canvas.concat(m)
            }
        }
    }

    companion object {
        private const val DEFAULT_BORDER_WIDTH = 0
        private val DEFAULT_BORDER_COLOR: Int = Color.TRANSPARENT
        private val DEFAULT_FILL_COLOR: Int = Color.TRANSPARENT
    }
}