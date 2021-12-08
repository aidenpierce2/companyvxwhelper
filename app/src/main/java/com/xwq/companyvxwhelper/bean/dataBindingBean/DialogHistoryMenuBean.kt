package com.xwq.companyvxwhelper.bean.dataBindingBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class DialogHistoryMenuBean(var titleStr : String, var timeIntever : String, var startTime : String, var startTimeValue : String, var endTime : String,var endTimeValue : String, var statusChoose : String, var allStatus : String, var succStatus : String, var errStatus : String,
                                 var cancelStr : String, var ensureStr : String) : BaseEntity(){

    constructor() : this("","", "", "", "", "", "" ,"", "", "", "", "")
}
