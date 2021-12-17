package com.xwq.companyvxwhelper.utils

import java.security.MessageDigest
import kotlin.random.Random

class Md5Util {

    companion object {

        // md5加密
        fun md5Encry(encryValue : String) : String{
            var md5Instance : MessageDigest = MessageDigest.getInstance("MD5")
            var byteArray : ByteArray = md5Instance.digest(encryValue.toByteArray())
            var sb : StringBuffer = StringBuffer()
            for (value in byteArray) {
                var i : Int = value.toInt() and 0xFF
                var hexValue = Integer.toHexString(i)
                if (hexValue.length < 2) {
                    hexValue = "0" + hexValue
                }
                sb.append(hexValue)
            }
            return sb.toString()
        }

        // md5生成 (此方法仅第一次登陆 才能使用!!)
        fun makeSignKey() : String{
            val signLength = 32
            var keyMap : String = "0123456789abcdef"
            var result = "";
            for (i in  0..signLength-1) {
                result += keyMap.get(Random.nextInt(keyMap.length - 1))
            }
            return result
        }
    }
}