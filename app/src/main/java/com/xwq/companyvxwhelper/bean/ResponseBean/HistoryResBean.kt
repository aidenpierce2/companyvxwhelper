package com.xwq.companyvxwhelper.bean.ResponseBean

data class HistoryResBean(var historyResDataBeanList : List<HistoryResDataBean>, var allCount : Int) {

    constructor() : this(arrayListOf<HistoryResDataBean>(), 0) {

    }

    data class HistoryResDataBean(var yearStr : String, var monthStr : String, var dayStr : String, var statusCode : Int, var tickAddressEncry : String, var tickTimeStamp : String , var startWork : Boolean,
                                  var tag : String, var itemType : Int = 2) {
        constructor() : this("","","", 0, "", "", true,"") {

        }

        constructor(tag: String) : this("","","", 0, "", "",true, tag)

        var next : HistoryResBean.HistoryResDataBean? = null
    }
}
