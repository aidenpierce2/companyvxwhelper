package com.xwq.companyvxwhelper.utils

import android.content.Context
import android.content.SharedPreferences
import com.xwq.companyvxwhelper.MyApplication.Companion.app

class SharePreferenceUtil {

    var SHARE_TAG = "share_tag"

    var util : SharePreferenceUtil? = null
    var share : SharedPreferences? = null
    var editor : SharedPreferences.Editor? = null

    companion object {
        @JvmStatic
        var instance : SharePreferenceUtil = SharePreferenceUtil()
    }

    constructor() {
        share = app.getSharedPreferences(SHARE_TAG, Context.MODE_PRIVATE) as SharedPreferences
        editor = share!!.edit()
    }

    fun setData (key : String, value : String) {
        if (editor != null) {
            editor!!.putString(key, value)
            editor!!.commit()
        }
    }

    fun setData (key: String, value: Boolean) {
        if (editor != null) {
            editor!!.putBoolean(key, value)
            editor!!.commit()
        }
    }

    fun setData (key: String, value: Long) {
        if (editor != null) {
            editor!!.putLong(key, value)
            editor!!.commit()
        }
    }

    fun getData (key : String)  : String{
        return getData(key, "")
    }

    fun getData (key : String, defaultValue : String) : String{
        if (share != null) {
            return share!!.getString(key, defaultValue)!!
        }
        return defaultValue
    }

    fun getData (key: String, defaultValue: Boolean) : Boolean {
        if (share != null) {
            return share!!.getBoolean(key, defaultValue)
        }
        return defaultValue
    }

    fun getData (key: String, defaultValue: Long) : Long {
        if (share != null) {
            return share!!.getLong(key, defaultValue)
        }
        return defaultValue
    }

    fun emptyData (key : String) {
        if (editor != null) {
            editor!!.putString(key, "")
            editor!!.commit()
        }
    }

    fun clearData () {
        if (editor != null) {
            editor!!.clear()
            editor!!.commit()
        }
    }
}