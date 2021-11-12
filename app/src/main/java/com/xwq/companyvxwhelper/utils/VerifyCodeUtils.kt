package com.xwq.companyvxwhelper.utils

import java.util.regex.Pattern

class VerifyCodeUtils {

    companion object {

        fun checkVerifyCode(verifyCodeStr : String?) : Boolean{
            if(verifyCodeStr.isNullOrEmpty()) {
                return false
            }
            var pattern : Pattern = Pattern.compile("^\\d{6}$")
            var matcher = pattern.matcher(verifyCodeStr)
            return matcher.matches()
        }
    }
}