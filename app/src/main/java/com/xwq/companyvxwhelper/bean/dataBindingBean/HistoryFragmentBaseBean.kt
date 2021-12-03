package com.xwq.companyvxwhelper.bean.dataBindingBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class HistoryFragmentBaseBean(var timeIntever : String, var startTime : String, var endTime : String, var statusChoose : String, var allStatus : String, var succStatus : String, var errStatus : String,
    var cancelStr : String, var ensureStr : String) : BaseEntity(){

    constructor() : this("", "", "", "" ,"", "", "", "", "")
}
