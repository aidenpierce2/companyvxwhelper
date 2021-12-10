package com.xwq.companyvxwhelper.bean.ResponseBean

data class HistoryResBean(var historyResDataBeanList : List<HistoryResDataBean>, var allCount : Int) {

    constructor() : this(arrayListOf<HistoryResDataBean>(), 0) {

    }

    data class HistoryResDataBean(var yearStr : String, var monthStr : String, var dayStr : String, var statusCode : Int, var tickAddress : String, var tickTimeStamp : String) {
        constructor() : this("","","", 0, "", "") {

        }
    }
}
