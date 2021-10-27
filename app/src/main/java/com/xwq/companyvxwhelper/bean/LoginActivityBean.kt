package com.xwq.companyvxwhelper.bean

import android.graphics.drawable.Drawable
import com.xwq.companyvxwhelper.base.BaseEntity

data class LoginActivityBean(var iconDrawable : Drawable?, val userTelBean : UserTelOrPassBean?, val userVerifyCodeBean : UserVerifyCodeBean?, val userPassBean : UserTelOrPassBean?,
                             val userRePassBean : UserTelOrPassBean?, var hasCheckRePass : Boolean, var hasEnsureAuth : Boolean) : BaseEntity() {

    constructor() : this(null, null, null, null,null,false,false)


    data class UserTelOrPassBean(var hintText : String, var inputText : String, var showClear : Boolean) {

    }

    data class UserVerifyCodeBean(var hintText : String, var inputText : String, var clickText : String, var clickAble : Boolean) {

    }
}