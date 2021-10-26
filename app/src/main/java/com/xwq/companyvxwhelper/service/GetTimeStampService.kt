package com.xwq.companyvxwhelper.service

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.xwq.companyvxwhelper.bean.ResponseBean.AliTimeStampResponse
import com.xwq.companyvxwhelper.const.ShareePreferenceConst
import com.xwq.companyvxwhelper.service.request.RequestHttp
import com.xwq.companyvxwhelper.utils.SharePreferenceUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// 服务用来获取最新的时间戳
class GetTimeStampService : IntentService("GetTimeStamp") {

    override fun onHandleIntent(p0: Intent?) {

    }

    override fun onBind(intent: Intent?): IBinder? {

        CoroutineScope(Dispatchers.IO).launch {
            // 第一步
            // 绑定服务开始请求
            updateNewestTime()
            // 第二步 从服务器读取自动打卡配置信息(暂时没有服务器)
            // 从数据库读取
        }


        return super.onBind(intent)
    }

    fun updateNewestTime() {
        RequestHttp().getTimeStamp(application).observer(object : RequestHttp.ResponseListener {
            override fun onSuccess(data: AliTimeStampResponse.Data) {
                SharePreferenceUtil.instance.setData(ShareePreferenceConst.TIMESTAMP, data!!.t!!)
            }

            override fun onFail() {
                // 异常？ 获取本地时间
                getLocalTime()
            }

        })
    }

    fun getLocalTime() {
        var currentTimeMillis = System.currentTimeMillis()
        SharePreferenceUtil.instance.setData(ShareePreferenceConst.TIMESTAMP, currentTimeMillis.toString())
    }

}