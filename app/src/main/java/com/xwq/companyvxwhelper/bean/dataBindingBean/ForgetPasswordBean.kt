package com.xwq.companyvxwhelper.bean.dataBindingBean

import android.graphics.drawable.Drawable
import com.xwq.companyvxwhelper.base.BaseEntity

data class ForgetPasswordBean(var backDrawable: Drawable?,var iconDrawable : Drawable?, var userTelBean : UserTelOrPassBean, var userVerifyCodeBean : UserVerifyCodeBean,
                                var userPassBean : UserTelOrPassBean,var userRePassBean : UserTelOrPassBean, var resetPasswordStr : String?, var versionCodeStr : String?) : BaseEntity() {

    constructor() : this(null, null, UserTelOrPassBean("", "", false),
        UserVerifyCodeBean("", "", "", false),
        UserTelOrPassBean("", "", false),
        UserTelOrPassBean("", "", false), "", "")


    data class UserTelOrPassBean(var hintText : String, var inputText : String, var showClear : Boolean) {

    }

    data class UserVerifyCodeBean(var hintText : String, var inputText : String, var clickText : String, var clickAble : Boolean) {

    }

}
