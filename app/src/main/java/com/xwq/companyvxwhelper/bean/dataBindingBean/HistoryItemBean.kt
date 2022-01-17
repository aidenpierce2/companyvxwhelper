package com.xwq.companyvxwhelper.bean.dataBindingBean

import android.graphics.drawable.Drawable
import com.xwq.companyvxwhelper.base.BaseEntity

data class HistoryItemBean(var dateStr : String, var statusStr : String, var statusDrwable : Drawable?, var tickAddress : String, var timeStamp : String, var startWork : Boolean, var success : Boolean,
                           override var itemType : Int = 0) : HistoryBaseBean() {

    constructor() : this("","", null, "", "", true, true)

}
