package com.xwq.companyvxwhelper.widget

import android.content.ClipDescription
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import com.xwq.companyvxwhelper.R

class FocusImageConstraintLayout : ConstraintLayout {

    val TAG : String = this@FocusImageConstraintLayout::class.java.simpleName.toString()
    val ANIMATION_DURATION : Long = 200L
    val SCALL_UP_RATE : Float = 1.10f
    val SCALL_DOWN_RATE : Float = 1.0f
    lateinit var mContext : Context
    lateinit var headerACIV : AppCompatImageView
    lateinit var switchSB: SwitchButton
    lateinit var descriptionACTV : AppCompatTextView
    lateinit var contentView : View

    constructor(mContext : Context) : this(mContext, null){

    }

    constructor(mContext: Context, attrs : AttributeSet?) : this(mContext, attrs, 0) {

    }

    constructor(mContext: Context, attrs : AttributeSet?, defStyleAttr : Int) : this(mContext, attrs, defStyleAttr, 0) {

    }

    constructor(mContext: Context, attrs : AttributeSet?, defStyleAttr : Int, defStyleRes : Int) : super(mContext, attrs, defStyleAttr, defStyleRes) {
        contentView = LayoutInflater.from(context).inflate(R.layout.widget_focus_image, this@FocusImageConstraintLayout, false)
    }

    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
        if (gainFocus) {
            scallUp()
        }else {
            scallDown()
        }
    }

    // 放大
    private fun scallUp() {
        ViewCompat.animate(this@FocusImageConstraintLayout)
            .setDuration(ANIMATION_DURATION)
            .scaleX(SCALL_UP_RATE)
            .scaleY(SCALL_UP_RATE)
            .start()
    }

    //缩小
    private fun scallDown() {
        ViewCompat.animate(this@FocusImageConstraintLayout)
            .setDuration(ANIMATION_DURATION)
            .scaleX(SCALL_DOWN_RATE)
            .scaleY(SCALL_DOWN_RATE)
            .start()
    }
}