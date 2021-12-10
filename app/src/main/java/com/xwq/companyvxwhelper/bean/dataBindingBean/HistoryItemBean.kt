package com.xwq.companyvxwhelper.bean.dataBindingBean

import android.graphics.drawable.Drawable
import com.xwq.companyvxwhelper.base.BaseEntity

data class HistoryItemBean(var bgDrawable : Drawable?, var statusDrwable : Drawable?, var tickAddress : String, var timeStamp : String) : HistoryBaseBean() {

    constructor() : this(null, null, "", "")

}
