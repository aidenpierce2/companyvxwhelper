package com.xwq.companyvxwhelper.bean.RequestBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class ForgetPassWordReqBean(var encryptUserPhone : String?, var verifyCode : String?, var encryptUserPassWord : String?) :  BaseEntity(){
}