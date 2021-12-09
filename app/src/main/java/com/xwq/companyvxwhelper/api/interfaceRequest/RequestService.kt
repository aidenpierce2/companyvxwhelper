package com.xwq.companyvxwhelper.api.interfaceRequest

import com.xwq.companyvxwhelper.base.BaseNetworkResponse
import com.xwq.companyvxwhelper.bean.ResponseBean.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface RequestService {

    // 获取时间戳
    @GET
    fun getTimeStamp(@retrofit2.http.Url requestUrl : String) : Observable<AliTimeStampResponse>

    //发送短信
    @FormUrlEncoded
    @POST
    fun sendSms(@retrofit2.http.Url requestUrl: String, @FieldMap map: Map<String, String>) : Observable<BaseNetworkResponse<SendSmsResBean>>

    // 检测token合法性
    @POST
    fun checkTokenValid(@retrofit2.http.Url requestUrl: String, @Body jsonStr : RequestBody) : Observable<BaseNetworkResponse<CheckTokenResBean>>

    // 登录
    @FormUrlEncoded
    @POST
    fun login(@retrofit2.http.Url requestUrl: String, @FieldMap map : Map<String, String>) : Observable<BaseNetworkResponse<LoginResBean>>

    //注册
    @FormUrlEncoded
    @POST
    fun register(@retrofit2.http.Url requestUrl: String, @FieldMap map: Map<String, String>) : Observable<BaseNetworkResponse<RegisterResBean>>

    // 忘记密码
    @FormUrlEncoded
    @POST
    fun forgetPassWord(@retrofit2.http.Url requestUrl: String, @FieldMap map : Map<String, String>) : Observable<BaseNetworkResponse<ForgetPassWordResBean>>

    // 获取个人信息
    @GET
    fun getUserInfo(@retrofit2.http.Url requestUrl: String) : Observable<BaseNetworkResponse<UserInfoResBean>>

    // 获取历史记录
    @GET
    fun getHistoryDate(@retrofit2.http.Url requestUrl: String, @FieldMap map : Map<String, String>) : Observable<BaseNetworkResponse<HistoryResBean>>

}