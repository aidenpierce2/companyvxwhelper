package com.xwq.companyvxwhelper.api

import retrofit2.http.GET

class Urls {
    companion object {
        const val ALI_TIME_STAMP = "http://api.m.taobao.com/rest/api3.do?api=mtop.common.getTimestamp"

        // 获取rsa私钥
        const val GET_PUBLIC_RSA = "/api/user/preLogin"
        //本地服务
        const val SEND_SMS = "/api/sendsms"
        // 判断token是否有效
        const val CHECK_TOKEN_VALID = "/api/token/checkvalid"
        //首页获取最新广告图
        const val GET_LATEST_ADV = "/api/advertisement/getlatest"
        //获取短信
        const val GET_ATTESTATION_MSG = "/api/message/getattestation"
        //提交注册
        const val SUB_USER_REGISTER = "/api/user/register"
        //提交登录
        const val SUB_USER_LOGIN = "/api/user/login"
        //忘记密码
        const val FORGET_PASSWORD = "/api/user/forgetpassword"
        //获取个人信息
        const val GET_USER_INFO = "/api/user/getpersioninfo"
        //上传个人信息头像
        const val SUB_USER_HEADER = "/api/user/userheader"
        //保存个人信息
        const val SUB_USER_INFO = "/api/user/subpersioninfo"
        //获取历史记录
        const val GET_HISTORY_INFO = "/api/history/gethistorydata"
    }
}