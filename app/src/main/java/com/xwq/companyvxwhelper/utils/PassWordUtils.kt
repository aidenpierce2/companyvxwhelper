package com.xwq.companyvxwhelper.utils

import com.xwq.companyvxwhelper.bean.Enum.PassWordErrEnum
import java.util.regex.Pattern

class PassWordUtils {

    companion object {
        val MIN_PASS_LENGTH = 8
        val MAX_PASS_LENGTH = 16
        fun checkPassWordValid(userPassWord : String?) : PassWordErrEnum{
            if (userPassWord.isNullOrEmpty()) {
                return PassWordErrEnum.EMPTYERR
            }
            if (userPassWord.length <8 || userPassWord.length > 16) {
                return PassWordErrEnum.LENGTHERR
            }
            var characterPatt : Pattern = Pattern.compile("[a-zA-Z]")
            var charMatcher = characterPatt.matcher(userPassWord)
            if (!charMatcher.find()) {
                return PassWordErrEnum.NOCHARACTERERR
            }
            var numberPatt : Pattern = Pattern.compile("[\\d]{2,}")
            var numberMatcher = numberPatt.matcher(userPassWord)
            if (!numberMatcher.find()) {
                return  PassWordErrEnum.NONUMBERERR
            }
            return PassWordErrEnum.NOERR
        }
    }
}