package com.xwq.companyvxwhelper.api.interfaceRequest

import com.xwq.companyvxwhelper.base.BaseNetworkResponse
import com.xwq.companyvxwhelper.bean.ResponseBean.AliTimeStampResponse
import com.xwq.companyvxwhelper.bean.ResponseBean.SendSmsResBean
import io.reactivex.Observable
import retrofit2.http.*

interface RequestService {

    @GET
    fun getTimeStamp(@retrofit2.http.Url requestUrl : String) : Observable<AliTimeStampResponse>

    //发送短信
    @FormUrlEncoded
    @POST
    fun sendSms(@retrofit2.http.Url requestUrl: String, @FieldMap map: Map<String, String>) : Observable<BaseNetworkResponse<SendSmsResBean>>

}