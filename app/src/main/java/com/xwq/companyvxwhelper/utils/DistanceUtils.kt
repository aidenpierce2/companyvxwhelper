package com.xwq.companyvxwhelper.utils

import java.math.BigDecimal
import java.text.DecimalFormat

class DistanceUtils {

    companion object {

        /**
         * 地球半径,单位 km
         */
        private const val KM_RATE = 1000
        private const val EARTH_RADIUS = 6378.137

        fun getDistanceKm(
            longitude1: Double,
            latitude1: Double,
            longitude2: Double,
            latitude2: Double
        ) : String {
             var kmDistance = getDistance(longitude1, latitude1, longitude2, latitude2)
            if (kmDistance < 1) {
                var multiply : BigDecimal = BigDecimal(kmDistance).multiply(BigDecimal(KM_RATE))
                //保留2位小数
                val df = DecimalFormat("#.00")
                return df.format(multiply) + "m"
            } else {
                //保留2位小数
                val df = DecimalFormat("#.00")
                return df.format(kmDistance) + "km"
            }
        }

        /**
         * 根据经纬度，计算两点间的距离
         *
         * @param longitude1 第一个点的经度
         * @param latitude1  第一个点的纬度
         * @param longitude2 第二个点的经度
         * @param latitude2  第二个点的纬度
         * @return 返回距离 单位千米
         */
        fun getDistance(
            longitude1: Double,
            latitude1: Double,
            longitude2: Double,
            latitude2: Double
        ): Double {
            // 纬度
            val lat1 = Math.toRadians(latitude1)
            val lat2 = Math.toRadians(latitude2)
            // 经度
            val lng1 = Math.toRadians(longitude1)
            val lng2 = Math.toRadians(longitude2)
            // 纬度之差
            val a = lat1 - lat2
            // 经度之差
            val b = lng1 - lng2
            // 计算两点距离的公式
            var s = 2 * Math.asin(
                Math.sqrt(
                    Math.pow(Math.sin(a / 2), 2.0) +
                            Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(b / 2), 2.0)
                )
            )
            // 弧长乘地球半径, 返回单位: 千米
            s = s * EARTH_RADIUS
            return s
        }
    }
}