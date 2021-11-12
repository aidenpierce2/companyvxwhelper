package com.xwq.companyvxwhelper.bean.RequestBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class SendSmsReqBean(var userPhoneNum : String, var timeStamp : String) :BaseEntity() {

}