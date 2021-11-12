package com.xwq.companyvxwhelper.bean.dataBindingBean

import android.graphics.drawable.Drawable
import android.text.SpannableString
import com.xwq.companyvxwhelper.base.BaseEntity

data class RegisteActivityBean(var backDrawable: Drawable?, var iconDrawable : Drawable?, val userTelBean : UserTelOrPassBean, val userVerifyCodeBean : UserVerifyCodeBean, val userPassBean : UserTelOrPassBean,
                               val userRePassBean : UserTelOrPassBean, var hasEnsureAuth : Boolean, var userPolicy : SpannableString, var loginStr : String,
                                var versionStr : String) : BaseEntity() {

    constructor() : this(null, null, UserTelOrPassBean("", "", false),
        UserVerifyCodeBean("", "", "", false),
        UserTelOrPassBean("", "", false),
        UserTelOrPassBean("", "", false), false, SpannableString(""), "",
    "")


    data class UserTelOrPassBean(var hintText : String, var inputText : String, var showClear : Boolean) {

    }

    data class UserVerifyCodeBean(var hintText : String, var inputText : String, var clickText : String, var clickAble : Boolean) {

    }
}