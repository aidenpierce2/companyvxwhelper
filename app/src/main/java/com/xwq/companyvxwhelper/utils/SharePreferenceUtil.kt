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
        var instance : SharePreferenceUtil = SharePreferenceUtil()
    }


    fun SharePreferenceUtil() {
        share = app!!.getSharedPreferences(SHARE_TAG, Context.MODE_PRIVATE) as SharedPreferences
        editor = share!!.edit()
    }


    fun setData (key : String, value : String) {
        if (editor != null) {
            editor!!.putString(key, value)
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