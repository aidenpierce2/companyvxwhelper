package com.xwq.companyvxwhelper.const

import android.security.keystore.KeyNotYetValidException
import com.xwq.companyvxwhelper.base.BaseEntity

object Const : BaseEntity() {

    // 默认地址经纬度
    const val DEAFULT_LATITUDE : Double = 39.90960
    const val DEAFULT_LONTITUDE : Double = 116.39722

    // 目标app包名
    const val TARGET_PACKAGE_NAME : String = "com.tencent.wework"
    // 目标app 下载地址
    const val DOWNLOAD_PATH : String = "https://work.weixin.qq.com/#indexDownload"
    // 当前rsa公钥
    const val PUBLIC_RSA : String = "public_rsa"
    // 当前uuid
    const val KEY_UUID : String = "key_uuid"
    // 当前aes秘钥
    const val PRIVATE_AES : String = "private_aes"
    // 用户手机号码
    const val USER_PHONENUM = "user_phonenum"
    // 用户密码
    const val USER_PASSWORD = "user_password"
    // uuid 每个用户唯一的uuid
    const val USER_UUID = "user_uuid"
    // 签到浮动时间
    const val SIGN_MIN = "sign_min"
    // singKey
    const val SIGN_KEY = "sign_key"
    // 记住密码
    const val REMEMBER_PASS = "remember_pass"
    // 用户政策
    const val USER_POLICY_CHECKED = "user_policy_checked"
    // 权限通知
    const val USER_ENUSRE_AUTH = "user_ensure_auth"
    // 启动状态
    const val SERVICE_RUNNING = "service_running"
    // 获取验证码剩余时间
    const val GET_VERIFYCODE_START_TIME = "get_verifycode_start_time"
    // 我的下拉刷新 是否可以点击
    const val PULLDOWN_CAN_CLICK = "pulldown_can_click"

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
    // token
    const val USER_TOKEN : String = ""

}