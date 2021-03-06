package com.xwq.companyvxwhelper.mvvm.adapter.decoration

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


open class RevenueSummaryItemDecoration(context: Context, orientation: Int) : RecyclerView.ItemDecoration() {
    private var mContext: Context? = null
    private var mDivider: Drawable? = null
    private var mOrientation = 0

    companion object {
        var HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
        var VERTICAL_LIST = LinearLayoutManager.VERTICAL
    }

    //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
    val ATRRS = intArrayOf(
            R.attr.listDivider
    )
    init {
        mContext = context
        val ta: TypedArray = context.obtainStyledAttributes(ATRRS)
        mDivider = ta.getDrawable(0)
        ta.recycle()
        setOrientation(orientation)
    }

    //设置屏幕的方向
    fun setOrientation(orientation: Int) {
        require(!(orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST)) { "invalid orientation" }
        mOrientation = orientation
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (mOrientation == HORIZONTAL_LIST) {
            drawVerticalLine(c, parent, state)
        } else {
            drawHorizontalLine(c, parent, state)
        }
    }

    //画横线, 这里的parent其实是显示在屏幕显示的这部分
    fun drawHorizontalLine(c: Canvas?, parent: RecyclerView, state: RecyclerView.State?) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)

            //获得child的布局信息
            val params = child.getLayoutParams() as RecyclerView.LayoutParams
            val top: Int = child.getBottom() + params.bottomMargin
            val bottom: Int = top + mDivider!!.getIntrinsicHeight()
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c!!)
            //Log.d("wnw", left + " " + top + " "+right+"   "+bottom+" "+i);
        }
    }

    //画竖线
    fun drawVerticalLine(c: Canvas?, parent: RecyclerView, state: RecyclerView.State?) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)

            //获得child的布局信息
            val params = child.getLayoutParams() as RecyclerView.LayoutParams
            val left: Int = child.getRight() + params.rightMargin
            val right: Int = left + mDivider!!.getIntrinsicWidth()
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c!!)
        }
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (mOrientation == HORIZONTAL_LIST) {
            //画横线，就是往下偏移一个分割线的高度
            outRect.set(0, 0, 0, mDivider!!.getIntrinsicHeight())
        } else {
            //画竖线，就是往右偏移一个分割线的宽度
            outRect.set(0, 0, mDivider!!.getIntrinsicWidth(), 0)
        }
    }
}