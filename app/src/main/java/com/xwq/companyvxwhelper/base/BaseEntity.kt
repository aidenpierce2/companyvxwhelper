package com.xwq.companyvxwhelper.base

import android.os.Parcel
import android.os.Parcelable
import com.xwq.companyvxwhelper.utils.LogUtil
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.HashMap

open class BaseEntity() : Parcelable {

    val parcelableVersionUID : Long = 1L

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseEntity> {
        override fun createFromParcel(parcel: Parcel): BaseEntity {
            return BaseEntity(parcel)
        }

        override fun newArray(size: Int): Array<BaseEntity?> {
            return arrayOfNulls(size)
        }
    }

    fun toMap () : Map<String, String>{
        var clazz: Class<out BaseEntity?> = this.javaClass
        var declaredFields : Array<Field> = clazz.declaredFields

        if (declaredFields != null || declaredFields.size == 0) {
            return Collections.emptyMap()
        }

        var params = HashMap<String, String>()
        for (field in declaredFields) {
            field.isAccessible = true
            params.put(field.name, field[this].toString())
        }

        LogUtil.log("", "map 长度： " + params.size)
        LogUtil.log("", "map 内容:  " + params.toString())

        return params
    }

}