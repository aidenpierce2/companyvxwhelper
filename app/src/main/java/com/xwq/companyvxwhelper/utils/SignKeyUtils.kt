package com.xwq.companyvxwhelper.utils

import android.util.Base64
import com.xwq.companyvxwhelper.bean.Enum.EncryOrDecryEnum
import com.xwq.companyvxwhelper.const.Const
import com.xwq.companyvxwhelper.utils.Md5Util.Companion.md5Encry

class SignKeyUtils {

    companion object {
        val ckeyLength = 4
        val arrayLength = 256
        val subStart = 0
        val padLength = 10
        val subMiddle = 16
        val padEnd = 26
        val subEnd = 32
        fun encryOrDecryValue(
            encryOrDecryValue: String?,
            encryOrDecryEnum: EncryOrDecryEnum,
            signKey: String = ""
        ) : String{
            if (encryOrDecryValue.isNullOrEmpty()) {
                return ""
            }
            var finalEncryOrDecryValue : String = ""
            var finalSignkey : String = ""
            if (signKey.isNullOrEmpty()) {
                finalSignkey = SharePreferenceUtil.instance.getData(Const.SIGN_KEY)
            } else {
                finalSignkey = signKey
            }
            var key = md5Encry(finalSignkey)
            var keyA = md5Encry(key.substring(subStart, subMiddle))
            var keyB = md5Encry(key.substring(subMiddle, subEnd))
            var keyC : String = ""
            when (encryOrDecryEnum) {
                EncryOrDecryEnum.ENCRYPTION -> {
                    var encryValueTemp = md5Encry(System.currentTimeMillis().toString())
                    keyC = encryValueTemp.substring(subStart, encryValueTemp.length - ckeyLength)
                }
                EncryOrDecryEnum.DECRYPTION -> {
                    keyC = encryOrDecryValue.substring(subStart, ckeyLength)
                }
            }
            var cryptKey : String = keyA + md5Encry(keyA + keyC)
            var keyLength = encryOrDecryValue.length
            when (encryOrDecryEnum) {
                EncryOrDecryEnum.ENCRYPTION -> {
                    finalEncryOrDecryValue = StringBuffer().append(
                        toSpecialLength(TimeStampUtils.getCurrentSecond(), padLength)
                    ).append(
                        md5Encry(encryOrDecryValue + keyB).substring(subStart, subMiddle)
                    ).append(encryOrDecryValue).toString()
                }
                EncryOrDecryEnum.DECRYPTION -> {
                    finalEncryOrDecryValue = baseEncryOrDecry(
                        encryOrDecryValue.substring(ckeyLength),
                        EncryOrDecryEnum.DECRYPTION
                    ).toString()
                }
            }
            var encryOrDecryValueLength = finalEncryOrDecryValue.length
            var result : String = ""
            var box : Array<Int?> = arrayOfNulls<Int>(arrayLength)
            for (i in subStart..arrayLength - 1) {
                box[i] = i
            }
            var rndKey : Array<Int?> = arrayOfNulls<Int>(arrayLength)
            for (i in subStart..arrayLength - 1) {
                rndKey[i] = cryptKey[i % keyLength].toInt()
            }
            var j : Int = subStart
            for (i in subStart..arrayLength - 1) {
                j = (j + box[i]!! + rndKey[i]!!) % arrayLength
                var tmp = box[i]
                box[i] = box[j]
                box[j] = tmp
            }
            var a : Int = subStart
            j = subStart
            for (i in subStart..encryOrDecryValueLength - 1) {
                a = (a + 1) % arrayLength
                j = (j + box[a]!!) % arrayLength
                var tmp = box[a]
                box[a] = box[j]
                box[j] = tmp
                result += (finalEncryOrDecryValue[i].toInt() xor (box[(box[a]!! + box[j]!!) % arrayLength])!!.toInt()).toChar()
            }
            when (encryOrDecryEnum) {
                EncryOrDecryEnum.ENCRYPTION -> {
                    return keyC.replace(
                        "=",
                        baseEncryOrDecry(result, EncryOrDecryEnum.ENCRYPTION).toString()
                    )
                }
                EncryOrDecryEnum.DECRYPTION -> {
                    if (result.substring(subStart, padLength).isNullOrEmpty() || result.substring(subStart, padLength)
                            .toLong() - TimeStampUtils.getCurrentSecond().toLong() > 0 &&
                        result.substring(padLength, padEnd) == md5Encry(
                            result.substring(
                                padEnd,
                                result.length - 1
                            ) + keyB
                        ).substring(subStart, subMiddle)
                    ) {
                        return result.substring(padEnd, result.length - 1)
                    } else {
                        return ""
                    }
                }
            }
        }

        // base64 加解密
        fun baseEncryOrDecry(encryOrDecryValue: String, encryOrDecryEnum: EncryOrDecryEnum) : ByteArray{
            var result : ByteArray
            when (encryOrDecryEnum) {
                EncryOrDecryEnum.ENCRYPTION -> {
                    result = Base64.encode(encryOrDecryValue.toByteArray(), Base64.DEFAULT)
                }
                EncryOrDecryEnum.DECRYPTION -> {
                    result = Base64.decode(encryOrDecryValue.toByteArray(), Base64.DEFAULT)
                }
            }
            return result
        }

        fun toSpecialLength(stringToPad: String, padToLength: Int): String? {
            var retValue: String? = null
            retValue = if (stringToPad.length < padToLength) {
                String.format(
                    "%0" + (padToLength - stringToPad.length).toString() + "d%s",
                    0,
                    stringToPad
                )
            } else {
                stringToPad
            }
            return retValue
        }
    }
}