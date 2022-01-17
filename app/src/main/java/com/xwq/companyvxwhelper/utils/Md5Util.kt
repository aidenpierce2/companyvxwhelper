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

        // aes 或者 iv生成 (此方法登录成功 就使用!!)
        fun makePrivatAes(hasUseAes : Boolean) : String {
            val signLength = 16
            val priority = 9
            var keyMapNum : String = "0123456789"
            var keySmallLetter : String = "abcdefghijklmnopqrstuvwxyz"
            var keyCapitalizeLetter : String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            var sbData : StringBuffer = StringBuffer()
            for (i in 1..signLength) {
                var priortyNum : Int = Random(System.currentTimeMillis()).nextInt(priority)
                when (priortyNum) {
                    0,1 -> {
                        if (hasUseAes) {
                            sbData.append(keyMapNum.get(Random(System.currentTimeMillis()).nextInt(keyMapNum.length)))
                        } else {
                            sbData.append(keySmallLetter.get(Random(System.currentTimeMillis()).nextInt(keySmallLetter.length)))
                        }
                    }
                    2,3,4 -> {
                        if (hasUseAes) {
                            sbData.append(keySmallLetter.get(Random(System.currentTimeMillis()).nextInt(keySmallLetter.length)))
                        } else {
                            sbData.append(keyCapitalizeLetter.get(Random(System.currentTimeMillis()).nextInt(keyCapitalizeLetter.length)))
                        }
                    }
                    5,6,7,8 -> {
                        if (hasUseAes) {
                            sbData.append(keyCapitalizeLetter.get(Random(System.currentTimeMillis()).nextInt(keyCapitalizeLetter.length)))
                        } else {
                            sbData.append(keyMapNum.get(Random(System.currentTimeMillis()).nextInt(keyMapNum.length)))
                        }
                    } else -> {
                        sbData.append("")
                    }
                }
            }
            return sbData.toString()
        }
    }
}