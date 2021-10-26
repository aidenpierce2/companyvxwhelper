package com.xwq.companyvxwhelper.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.amap.api.maps2d.AMapOptions
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.CameraPosition
import com.amap.api.maps2d.model.LatLng
import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.const.Const


class MainMapView : MapView {

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        init()
    }

    fun init() {
        this.viewTreeObserver.addOnGlobalLayoutListener {
            var group : ViewGroup = getChildAt(0) as ViewGroup
            if (group.getChildAt(2) != null) {
                group.getChildAt(2).visibility = View.GONE
            }
            if (group.getChildAt(5) != null) {
                group.getChildAt(5).visibility = View.GONE
            }
        }

    }
}