package com.xwq.companyvxwhelper.bean.NormalBean

import com.xwq.companyvxwhelper.base.BaseEntity
import com.xwq.companyvxwhelper.bean.ResponseBean.HistoryResBean

data class HistoryItemBean(var itemLevel : Int, var historyResDataBean: HistoryResBean.HistoryResDataBean) : BaseEntity(){
    constructor() : this(0, HistoryResBean.HistoryResDataBean())
}