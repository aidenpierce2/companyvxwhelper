package com.xwq.companyvxwhelper.utils

import com.xwq.companyvxwhelper.bean.Enum.UserCheckInEnum
import com.xwq.companyvxwhelper.const.Const
import java.text.SimpleDateFormat
import java.util.*

class CalculateTimeStampUtils {

    companion object {
        var calculateTimeStampUtils : CalculateTimeStampUtils? = null

        @Synchronized fun getInstance() : CalculateTimeStampUtils{
            if (calculateTimeStampUtils == null) {
                return CalculateTimeStampUtils()
            }
            return calculateTimeStampUtils!!
        }

        fun calculateRealCheckInTime(userCheckInEnum: UserCheckInEnum, isCheckIn : Boolean) : String{
            when (userCheckInEnum) {
                UserCheckInEnum.NINESIXFIVE -> {
                    if (isCheckIn) {
                        return getInstance().realCalculateCheckIn(Const.CHECKIN_AM, Const.PRE_CHECKIN_MIN)
                    } else {
                        return getInstance().realCalculateCheckIn(Const.CHECKOUT_PM1, Const.POS_CHECKOUT_MIN)
                    }
                }
                UserCheckInEnum.NINESIXSIX -> {
                    if (isCheckIn) {
                        return getInstance().realCalculateCheckIn(Const.CHECKIN_AM, Const.PRE_CHECKIN_MIN)
                    } else {
                        return getInstance().realCalculateCheckIn(Const.CHECKOUT_PM1, Const.POS_CHECKOUT_MIN)
                    }
                }
                UserCheckInEnum.NINENINESIX -> {
                    if (isCheckIn) {
                        return getInstance().realCalculateCheckIn(Const.CHECKIN_AM, Const.PRE_CHECKIN_MIN)
                    } else {
                        return getInstance().realCalculateCheckIn(Const.CHECKOUT_PM2, Const.POS_CHECKOUT_MIN)
                    }
                }
                UserCheckInEnum.CUSTOMIZE -> {
                    //不处理
                    return ""
                }
            }
        }
    }

    private fun realCalculateCheckIn(time : String, minutes : Int) : String{
        var simpleDateFormat = SimpleDateFormat("HHmm")
        var curDate : Date = simpleDateFormat.parse(time)
        var calendarInstance = Calendar.getInstance()
        calendarInstance.time = curDate
        calendarInstance.set(Calendar.MINUTE, calendarInstance.get(Calendar.MINUTE) - minutes)
        return simpleDateFormat.format(calendarInstance.time)
    }
}