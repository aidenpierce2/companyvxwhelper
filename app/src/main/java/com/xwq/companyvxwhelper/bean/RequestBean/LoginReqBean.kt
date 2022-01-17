package com.xwq.companyvxwhelper.bean.RequestBean

import com.xwq.companyvxwhelper.base.BaseEntity
import java.util.*

data class LoginReqBean(var encryptUserPhone : String, var encryptUserPassWord : String, var signKey : String, var keyUUID : String = UUID.randomUUID().toString(), var loginType : String, var userDefaultSignKey : Boolean) : BaseEntity() {

    constructor() : this("", "", "", "","", false) {

    }
}