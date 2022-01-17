package com.xwq.companyvxwhelper.utils

import android.util.Base64
import java.nio.charset.Charset
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class RsaAndAesUtils {

    companion object {
        /*
     * 加密用的Key 可以用26个字母和数字组成 使用AES-128-CBC加密模式，key需要为16位。
     */
        private var key = "hj7x89HbyuBI0456"
        private var iv = "NIfb&95GUY36Gfgh"
        private const val RSA = "RSA"

        /**
         * @author miracle.qu
         * @Description AES算法加密明文
         * @param data 明文
         * @param key 密钥，长度16
         * @param iv 偏移量，长度16
         * @return 密文
         */
        @Throws(Exception::class)
        fun encryptAES(data: String, aesKey: String = key): String? {
             try {
                val cipher = Cipher.getInstance("AES/CBC/NoPadding")
                val blockSize = cipher.blockSize
                val dataBytes = data.toByteArray()
                var plaintextLength = dataBytes.size
                if (plaintextLength % blockSize != 0) {
                    plaintextLength = plaintextLength + (blockSize - plaintextLength % blockSize)
                }
                val plaintext = ByteArray(plaintextLength)
                System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.size)
                val keyspec = SecretKeySpec(aesKey.toByteArray(), "AES")
                val ivspec = IvParameterSpec(iv.toByteArray()) // CBC模式，需要一个向量iv，可增加加密算法的强度
                cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec)
                val encrypted = cipher.doFinal(plaintext)
                return encode(encrypted).trim() // BASE64做转码。
            } catch (e: Exception) {
                e.printStackTrace()
                 return null
            }
        }

        /**
         * @author miracle.qu
         * @Description AES算法解密密文
         * @param data 密文
         * @param key 密钥，长度16
         * @param iv 偏移量，长度16
         * @return 明文
         */
        @Throws(Exception::class)
        fun decryptAES(data: String?, aesKey: String = key): String? {
            if (data.isNullOrEmpty()) {
                return null
            }
            try {
                val encrypted1: ByteArray = RsaAndAesUtils.decode(data) //先用base64解密
                val cipher = Cipher.getInstance("AES/CBC/NoPadding")
                val keyspec = SecretKeySpec(aesKey.toByteArray(), "AES")
                val ivspec = IvParameterSpec(iv.toByteArray())
                cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec)
                val original = cipher.doFinal(encrypted1)
                val originalString = String(original, Charset.forName("utf-8"))
                return originalString.trim()
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        /**
         * 编码
         * @param byteArray
         * @return
         */
        private fun encode(byteArray: ByteArray): String {
            return String(Base64.encode(byteArray, Base64.DEFAULT))
        }

        /**
         * 解码
         * @param base64EncodedString
         * @return
         */
        private fun decode(base64EncodedString: String): ByteArray {
            return Base64.decode(base64EncodedString, Base64.DEFAULT)
        }

        // RSA加密
        open fun encryptRSA(data: String, publicKeyStr: String?): String {
            if (publicKeyStr.isNullOrEmpty()) {
                return ""
            }
            return try {
                val cipher = Cipher.getInstance(RSA)
                // 编码前设定编码方式及密钥
                cipher.init(Cipher.ENCRYPT_MODE, loadPublicKey(publicKeyStr))
                // 传入编码数据并返回编码结果
                var original : ByteArray = cipher.doFinal(decode(data))
                return String(original, Charset.forName("utf-8"))
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
        }

        @Throws(java.lang.Exception::class)
        private fun loadPublicKey(publicKeyStr: String): PublicKey? {
            return try {
                val buffer: ByteArray = decode(publicKeyStr)
                val keyFactory: KeyFactory = KeyFactory.getInstance(RSA)
                val keySpec = X509EncodedKeySpec(buffer)
                keyFactory.generatePublic(keySpec) as RSAPublicKey
            } catch (e: NoSuchAlgorithmException) {
                throw java.lang.Exception("无此算法")
            } catch (e: InvalidKeySpecException) {
                throw java.lang.Exception("公钥非法")
            } catch (e: NullPointerException) {
                throw java.lang.Exception("公钥数据为空")
            }
        }

        public fun makeAesKey(aesKey : String) {
            if (!aesKey.isNullOrEmpty()) {
                key = aesKey
            }
        }

        public fun makeAesIv(aesIv : String) {
            if (!aesIv.isNullOrEmpty()) {
                iv = aesIv
            }
        }

        public fun getAesKey() : String{
            return key
        }
    }
}