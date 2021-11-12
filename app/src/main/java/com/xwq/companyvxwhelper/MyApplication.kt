package com.xwq.companyvxwhelper

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.xwq.companyvxwhelper.bean.dataBindingBean.LocationBean
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.text.DecimalFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.collections.HashMap

class MyApplication : MultiDexApplication() {

    private var store: Stack<Activity>? = Stack()
    private var locationClient: AMapLocationClient? = null
    private var locationOption: AMapLocationClientOption? = null
    private var locationObservable : LocationObservable = LocationObservable()
    private var lat = 0.0
    private var lon = 0.0

    companion object {
        lateinit var app : MyApplication
    }

    override fun onCreate() {
        super.onCreate()

        app = this
        // 初始化高德
        initLocation()
        startLocation()

        // 初始化生命周期
        registerActivityLifecycleCallbacks(SwitchBackgroundCallbacks())
        // 信任所有证书
        handleSSLHandshake()
    }

    private fun initLocation() {
        //初始化client
        locationClient = AMapLocationClient(applicationContext)
        locationOption = getDefaultOption()
        //设置定位参数
        locationClient!!.setLocationOption(locationOption)
        // 设置定位监听
        locationClient!!.setLocationListener(locationListener)
    }

    private fun getDefaultOption(): AMapLocationClientOption? {
        val mOption = AMapLocationClientOption()
        mOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.isGpsFirst = false //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.httpTimeOut = 5000 //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.interval = 5000 //可选，设置定位间隔。默认为30秒
        mOption.isNeedAddress = true //可选，设置是否返回逆地理地址信息。默认是true
        mOption.isOnceLocation = false //可选，设置是否单次定位。默认是false
        mOption.isOnceLocationLatest = false //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP) //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.isSensorEnable = false //可选，设置是否使用传感器。默认是false
        mOption.isWifiScan = true //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.isLocationCacheEnable = true //可选，设置是否使用缓存定位，默认为true
        mOption.geoLanguage = AMapLocationClientOption.GeoLanguage.DEFAULT //可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption
    }

    var locationListener = AMapLocationListener { location ->
        if (null != location) {
            val sb = StringBuffer()
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.errorCode == 0) {
                sb.append("""
    定位成功
    
    """.trimIndent())
                sb.append("""
    定位类型: ${location.locationType}
    
    """.trimIndent())
                sb.append("""
    经    度    : ${location.longitude}
    
    """.trimIndent())
                sb.append("""
    纬    度    : ${location.latitude}
    
    """.trimIndent())
                sb.append("""
    精    度    : ${location.accuracy}米
    
    """.trimIndent())
                sb.append("""
    提供者    : ${location.provider}
    
    """.trimIndent())
                sb.append("""
    速    度    : ${location.speed}米/秒
    
    """.trimIndent())
                sb.append("""
    角    度    : ${location.bearing}
    
    """.trimIndent())
                // 获取当前提供定位服务的卫星个数
                sb.append("""
    星    数    : ${location.satellites}
    
    """.trimIndent())
                sb.append("""
    国    家    : ${location.country}
    
    """.trimIndent())
                sb.append("""
    省            : ${location.province}
    
    """.trimIndent())
                sb.append("""
    市            : ${location.city}
    
    """.trimIndent())
                sb.append("""
    城市编码 : ${location.cityCode}
    
    """.trimIndent())
                sb.append("""
    区            : ${location.district}
    
    """.trimIndent())
                sb.append("""
    区域 码   : ${location.adCode}
    
    """.trimIndent())
                sb.append("""
    地    址    : ${location.address}
    
    """.trimIndent())
                sb.append("""
    兴趣点    : ${location.poiName}
    
    """.trimIndent())
            } else {
                //定位失败
                sb.append("""
    定位失败
    
    """.trimIndent())
                sb.append("""
    错误码:${location.errorCode}
    
    """.trimIndent())
                sb.append("""
    错误信息:${location.errorInfo}
    
    """.trimIndent())
                sb.append("""
    错误描述:${location.locationDetail}
    
    """.trimIndent())
            }
            sb.append("***定位质量报告***").append("\n")
            sb.append("* WIFI开关：").append(if (location.locationQualityReport.isWifiAble) "开启" else "关闭").append("\n")
            sb.append("* GPS星数：").append(location.locationQualityReport.gpsSatellites).append("\n")
            sb.append("* 网络类型：" + location.locationQualityReport.networkType).append("\n")
            sb.append("* 网络耗时：" + location.locationQualityReport.netUseTime).append("\n")
            sb.append("****************").append("\n")

            lon = DecimalFormat("#.00000").format(location.longitude).toDouble()
            lat = DecimalFormat("#.00000").format(location.latitude).toDouble()
            //解析定位结果，
            val result = sb.toString()
            Log.i(MyApplication.javaClass.toString(), result)
        }

        // 通知所有订阅者
        locationObservable.notifyAllObervers(lon, lat)
    }

    private fun startLocation() {
        // 设置定位参数
        locationClient!!.setLocationOption(locationOption)
        // 启动定位
        locationClient!!.startLocation()
    }

    private inner class SwitchBackgroundCallbacks : ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}

        override fun onActivityStarted(activity: Activity) {

        }

        override fun onActivityResumed(activity: Activity) {
            if (!store!!.contains(activity)) {
                store!!.add(activity)
            }
        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            store!!.remove(activity)
        }
    }

    fun getStore () : Stack<Activity>? {
        return store
    }

    fun exit() {
        var store1 = MyApplication.app!!.getStore()
        for ((index , e) in store1!!.withIndex()) {
            e.finish()
            store1.remove(e)
        }
    }

    fun handleSSLHandshake() {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }

                override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}
                override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
            })
            val sc = SSLContext.getInstance("TLS")
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
            HttpsURLConnection.setDefaultHostnameVerifier { hostname, session -> true }
        } catch (ignored: java.lang.Exception) {
        }
    }

    class LocationObservable : Observable() {

        var observerMap : HashMap<String, Observer> = HashMap()

        private fun init () {
            if (observerMap == null) {
                observerMap = hashMapOf()
            }
        }

        fun addImpl (tag : String, observer : Observer) {
            init()

            if (tag.isNullOrEmpty() || observer == null) {
                return
            }

            observerMap.put(tag, observer)
        }

        fun removeImpl (tag : String) {
            init()

            if ( tag.isNullOrEmpty() ){
                return
            }

            observerMap.remove(tag)
        }

        fun notifyAllObervers(lon : Double, lat : Double) {
            if (observerMap == null || observerMap.isEmpty()) {
                return
            }

            var entrySet : MutableSet<MutableMap.MutableEntry<String, Observer>> = observerMap.entries
            var iterator : MutableIterator<MutableMap.MutableEntry<String, Observer>> = entrySet.iterator()
            while (iterator.hasNext()) {
                var entry : MutableMap.MutableEntry<String, Observer> = iterator.next()
                var observer : Observer = entry.value
                observer.update(this, LocationBean(lon, lat))
            }
        }

    }

    fun getLocationObserver() : LocationObservable{
        return locationObservable
    }

}