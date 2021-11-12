package com.xwq.companyvxwhelper.bean.dataBindingBean

import android.graphics.drawable.Drawable
import com.xwq.companyvxwhelper.base.BaseEntity

data class LoginActivityBean(var iconDrawable : Drawable?, var userTelBean : UserTelOrPassBean, var userPassBean : UserTelOrPassBean, var checkDrawable : Drawable?, var checkStatusStr : String,
                             var loginStr : String, var registStr : String, var forgetStr : String, var versionStr : String) : BaseEntity() {

    constructor() : this(null, UserTelOrPassBean(), UserTelOrPassBean(), null, "", "",
        "", "","")


    data class UserTelOrPassBean(var hintText : String, var inputText : String, var showClear : Boolean) {

        constructor() : this("", "", false)
    }
}