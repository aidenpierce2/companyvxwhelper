package com.xwq.companyvxwhelper.bean.RequestBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class HistoryReqBean(var startTimeEncrypt : String, var endTimeEncrypt : String, var chooseStatus : Int, var page : Int, var limit : Int ) : BaseEntity() {

    constructor() : this("", "", 0, 0, 0)
}
