package com.xwq.companyvxwhelper.bean.ResponseBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class RegisterResBean(var token : String, var signKey : String) : BaseEntity() {

    constructor() : this("", "") {

    }
}