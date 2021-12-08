package com.xwq.companyvxwhelper.utils

import java.text.SimpleDateFormat
import java.util.*

class HistoryChooseTimeUtils {

    companion object {
        @JvmStatic
        fun getStartDay() : String{
            var instance : Calendar = Calendar.getInstance()
            instance.time = Date()
            instance.add(Calendar.MONTH, -3)
            var time = instance.time
            return SimpleDateFormat("yyyy-MM-dd").format(time)
        }

        @JvmStatic
        fun getEndDay() : String {
            return SimpleDateFormat("yyyy-MM-dd").format(Date())
        }
    }
}