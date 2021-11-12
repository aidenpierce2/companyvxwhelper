package com.xwq.companyvxwhelper.bean.RequestBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class ForgetPassWordReqBean(var userTelephone : String?, var verifyCode : String?, var userTelPassWord : String?) :  BaseEntity(){
}