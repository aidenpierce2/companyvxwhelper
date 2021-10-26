package com.xwq.companyvxwhelper.bean

import android.os.Bundle

class EventBusMessageTypeBean(eventBusMessageTypeBeanEnum : EventBusMessageTypeBeanEnum) {
    var bundle : Bundle? = null
    var eventBusMessageTypeBeanEnum : EventBusMessageTypeBeanEnum? = null

    init {
        this.eventBusMessageTypeBeanEnum = eventBusMessageTypeBeanEnum
    }

    enum class EventBusMessageTypeBeanEnum(var eventBusMessageTypeStr: String) {
        CUTDOWNTIME("cutdowntime"),CUTDOWNEND("cutdownend")
    }

    fun put(keyInt : String, value : Int) {
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle?.putInt(keyInt, value)
    }

    fun put(keyLong : String, value : Long) {
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle?.putLong(keyLong, value)
    }

    fun put(keyLong : String, value : Boolean) {
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle?.putBoolean(keyLong, value)
    }

    fun getInt(keyInt: String) : Int?{
        return bundle?.getInt(keyInt)
    }

    fun getLong(keyLong: String) : Long?{
        return bundle?.getLong(keyLong)
    }

    fun getBoolean(keyBoolean: String) : Boolean? {
        return bundle?.getBoolean(keyBoolean)
    }
}