package com.xwq.companyvxwhelper.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

class PhoneNumMatcherUtils {

    companion object {
        fun checkIsVaildPhoneNum(phoneNumStr : String?): Boolean {
            if (phoneNumStr.isNullOrEmpty()) {
                return false
            }
            var regex : String = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1、5、8、9]))\\d{8}$";
            var p : Pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            var m : Matcher = p.matcher(phoneNumStr);
            return m.matches();
        }
    }
}