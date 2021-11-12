package com.xwq.companyvxwhelper.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeStampUtils {

    companion object {
        fun getCurrentTime() : String{
            var curDate : Date = Date()
            var simpleDataFormta : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return simpleDataFormta.format(curDate)
        }

        //获取当前秒数
        fun getCurrentSecond() : String {
            return (Date().time / 1000).toString()
        }
    }
}