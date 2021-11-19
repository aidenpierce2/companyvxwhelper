package com.xwq.companyvxwhelper.bean.ResponseBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class UserInfoResBean(var headerImgUrl : String, var nickName : String, var sexual : String,var telePhoneNumStr : String, var longitudeStr :String, var latitudeStr : String) : BaseEntity() {

    constructor() : this("", "", "","", "","")
}