package com.xwq.companyvxwhelper.bean.RequestBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class LoginReqBean(var encryptUserPhone : String, var encryptUserPassWord : String,  var signKey : String, var loginType : String, var userDefaltSignKey : Boolean) : BaseEntity() {

    constructor() : this("", "", "", "", false) {

    }
}