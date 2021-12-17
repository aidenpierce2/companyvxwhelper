package com.xwq.companyvxwhelper.bean.dataBindingBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class DialogHistoryMenuBean(var titleStr : String, var timeIntever : String, var startTime : String, var startTimeValue : String, var endTime : String,var endTimeValue : String, var statusChoose : String, var allStatus : String,
                                 var chooseAll : Boolean, var succStatus : String, var chooseSucc : Boolean, var errStatus : String, var chooseErr : Boolean, var cancelStr : String, var ensureStr : String) : BaseEntity(){

    constructor() : this("","", "", "", "", "", "" ,"",
        true,"", false,"",false,"","")

    fun checkIsDifferent(dialogHistoryMenuBean : DialogHistoryMenuBean) : Boolean{
        if (dialogHistoryMenuBean == null) {
            return false
        }
        return this.startTimeValue.equals(dialogHistoryMenuBean.startTimeValue) &&
                this.endTimeValue.equals(dialogHistoryMenuBean.endTimeValue) &&
                this.chooseAll.equals(dialogHistoryMenuBean.chooseAll) &&
                this.chooseSucc.equals(dialogHistoryMenuBean.chooseSucc) &&
                this.chooseErr.equals(dialogHistoryMenuBean.chooseErr)
    }

}
