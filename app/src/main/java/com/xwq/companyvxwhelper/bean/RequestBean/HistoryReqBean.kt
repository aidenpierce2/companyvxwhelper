package com.xwq.companyvxwhelper.bean.RequestBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class HistoryReqBean(var startTimeEncrypt : String, var endTimeEncrypt : String, var chooseStatus : String) : BaseEntity() {

    constructor() : this("", "", "")
}
