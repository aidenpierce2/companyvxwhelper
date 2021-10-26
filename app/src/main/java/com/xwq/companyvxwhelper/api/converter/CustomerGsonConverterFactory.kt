package com.xwq.companyvxwhelper.api.converter

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by xwq 2021 5 27
 */
class CustomerGsonConverterFactory private constructor(gson: Gson?) : Converter.Factory() {
    private val gson: Gson
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        var adapter = gson.getAdapter(TypeToken.get(type))
        return GsonResponseBodyConverter<Any>(gson, adapter as TypeAdapter<Any>)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter<Any>(gson, adapter as TypeAdapter<Any>)
    }

    companion object {
        fun create(): CustomerGsonConverterFactory {
            val builder = GsonBuilder()
            builder.registerTypeAdapter(
                Date::class.java,
                JsonDeserializer { json, typeOfT, context ->
                    val value = json.asString
                    if (value.indexOf("-") != -1) { //格式为yyyy-MM-dd HH:mi:ss
                        try {
                            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            return@JsonDeserializer sdf.parse(value)
                        } catch (ex: ParseException) {
                            Log.e(CustomerGsonConverterFactory::class.java.name, ex.message, ex)
                        }
                    }
                    Date(json.asJsonPrimitive.asLong)
                })
            val gson = builder.create()

            return create(gson)
        }

        fun create(gson: Gson?): CustomerGsonConverterFactory {
            return CustomerGsonConverterFactory(gson)
        }
    }

    init {
        if (gson == null) throw NullPointerException("gson == null")
        this.gson = gson
    }
}