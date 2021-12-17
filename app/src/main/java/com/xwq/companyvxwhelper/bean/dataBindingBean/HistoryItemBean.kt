package com.xwq.companyvxwhelper.bean.dataBindingBean

import android.graphics.drawable.Drawable
import com.xwq.companyvxwhelper.base.BaseEntity

data class HistoryItemBean(var dateStr : String, var bgDrawable : Drawable?, var statusDrwable : Drawable?, var tickAddress : String, var timeStamp : String, var startWork : Boolean,var success : Boolean) : HistoryBaseBean() {

    constructor() : this("",null, null, "", "", true, true)

}
