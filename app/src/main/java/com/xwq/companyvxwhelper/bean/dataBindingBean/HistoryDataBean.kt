package com.xwq.companyvxwhelper.bean.dataBindingBean

import android.graphics.drawable.Drawable
import com.xwq.companyvxwhelper.base.BaseEntity

data class HistoryDataBean(var menuDrawable : Drawable?) : BaseEntity()  {

    constructor() : this(null)
}