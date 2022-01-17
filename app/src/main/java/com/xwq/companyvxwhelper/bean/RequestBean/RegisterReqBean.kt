package com.xwq.companyvxwhelper.bean.RequestBean

import com.xwq.companyvxwhelper.base.BaseEntity
import java.util.*

data class RegisterReqBean(var encryptUserPhone : String, var veriftCode : String, var encrypyUserPassWord : String, var signKey : String, var keyUUID : String = UUID.randomUUID().toString(), var loginType : String) : BaseEntity() {

    constructor() : this("", "", "","", "","") {

    }
}