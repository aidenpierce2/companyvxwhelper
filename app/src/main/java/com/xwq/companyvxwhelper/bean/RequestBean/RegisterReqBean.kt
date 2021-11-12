package com.xwq.companyvxwhelper.bean.RequestBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class RegisterReqBean(var encryptUserPhone : String, var veriftCode : String, var encrypyUserPassWord : String, var signKey : String, var loginType : String, var userDefaltSignKey : Boolean = false) : BaseEntity() {

    constructor() : this("", "", "","","", false) {

    }
}