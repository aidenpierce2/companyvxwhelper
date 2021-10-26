package com.xwq.companyvxwhelper.const

import com.xwq.companyvxwhelper.base.BaseEntity

object Const : BaseEntity() {

    // 默认地址经纬度
    const val DEAFULT_LATITUDE : Double = 39.90960
    const val DEAFULT_LONTITUDE : Double = 116.39722

    // 目标app包名
    const val TARGET_PACKAGE_NAME : String = "com.tencent.wework"
    // 目标app 下载地址
    const val DOWNLOAD_PATH : String = "https://work.weixin.qq.com/#indexDownload"
    // 用户手机号码
    const val USER_PHONENUM = "user_phonenum"
    // uuid 每个用户唯一的uuid
    const val USER_UUID = "user_uuid"
    // 签到浮动时间
    const val SIGN_MIN = "sign_min"

    // 默认浮动签到时间(分钟）
    const val DEF_SIGN_MIN : Int = 3
    // 默认早晨提前打卡时间
    const val PRE_CHECKIN_MIN : Int = 10
    // 默认下午晚点打卡时间
    const val POS_CHECKOUT_MIN : Int = 30
    // 默认早上打卡时间
    const val CHECKIN_AM : String = "09:00"
    // 默认下午打卡时间1
    const val CHECKOUT_PM1 : String = "18:00"
    // 默认下午打卡时间2
    const val CHECKOUT_PM2 : String = "21:00"

    //注册短信倒计时间

}