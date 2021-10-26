package com.xwq.companyvxwhelper.utils

import androidx.annotation.Size
import com.xwq.companyvxwhelper.R

class ColorTranslate {

    companion object {
        fun tranColorIntToStr(@Size(min = 1) colorInt: Int) : String{
            var trueCololrInt : Int
            if (colorInt == null) {
                trueCololrInt = R.color.white
            } else {
                trueCololrInt = colorInt
            }
            return String.format("#%06X", 0xFFFFFF and trueCololrInt)
        }
    }
}