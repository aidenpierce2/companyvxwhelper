package com.xwq.companyvxwhelper.api.converter

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.xwq.companyvxwhelper.utils.LogUtil
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import java.io.*
import java.util.regex.Pattern

/**
 * Created by xwq 2021 5 27
 */
class GsonResponseBodyConverter<T> internal constructor(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) : Converter<ResponseBody, T> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        var s = getString(value.byteStream())
        try {
            val jsonObject = JSONObject(s)
            val code = jsonObject.optInt("code")
            val msg = jsonObject.optString("msg")
            if (code != 0) {
                val newJson = JSONObject()
                newJson.put("code", code)
                newJson.put("msg", msg)
                newJson.put("data", null)
                s = newJson.toString()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        if (!s.isNullOrEmpty()) {
            s = formatStringForPHP(s)
        }
        val jsonReader = gson.newJsonReader(getReader(s))
        return try {
            adapter.read(jsonReader)
        } finally {
            value.close()
        }
    }

    @Throws(IOException::class)
    private fun getString(`is`: InputStream): String {
        var line: String?
        BufferedReader(InputStreamReader(`is`)).use { `in` ->
            val buffer = StringBuffer()
            while (`in`.readLine().also { line = it } != null) {
                buffer.append(line)
            }
            return buffer.toString()
        }
    }

    private fun getReader(s: String?): Reader {
        return StringReader(s)
    }

    private fun formatStringForPHP(origin: String?): String {
        val preRegEx = ",\"[a-zA-Z0-9_]+?\":\\[]"
        val tailRegEx = "\"[a-zA-Z0-9_]+?\":\\[],"
        val r_pre = Pattern.compile(preRegEx)
        val m_pre = r_pre.matcher(origin)
        m_pre.reset()
        val sb = StringBuffer()
        while (m_pre.find()) {
            m_pre.appendReplacement(sb, "")
            LogUtil.log("", "发现空数组直接过滤了")
        }
        m_pre.appendTail(sb)
        val pattern = Pattern.compile(tailRegEx)
        val matcher = pattern.matcher(sb.toString())
        matcher.reset()
        val sbNew = StringBuffer()
        while (matcher.find()) {
            matcher.appendReplacement(sbNew, "")
            LogUtil.log("", "发现空数组直接过滤了")
        }
        matcher.appendTail(sbNew)
        return sbNew.toString()
    }
}