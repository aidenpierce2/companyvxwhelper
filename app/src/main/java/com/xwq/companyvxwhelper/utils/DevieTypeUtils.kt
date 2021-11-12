package com.xwq.companyvxwhelper.utils

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Build.VERSION_CODES.O
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.bean.Enum.PhoneOrSimulatorEnum

class DevieTypeUtils {

    companion object {

        //判断当前设备是手机还是模拟器
        fun getDeviceType() :  PhoneOrSimulatorEnum{
            // 序列号判断
            var serialNo : String = ""
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                serialNo = Build.SERIAL
            }
            else if (ActivityCompat.checkSelfPermission(
                    MyApplication.app,
                    Manifest.permission.READ_PHONE_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                serialNo = Build.getSerial()
            }
            if (serialNo.isNullOrEmpty() || serialNo.equals("android")) {
                return PhoneOrSimulatorEnum.SIMULATOR
            }
            // 蓝牙判断
            var defaultAdapter = BluetoothAdapter.getDefaultAdapter()
            if (defaultAdapter == null) {
                return PhoneOrSimulatorEnum.SIMULATOR
            } else {
                var name = defaultAdapter.name
                if (name.isNullOrEmpty()) {
                    return PhoneOrSimulatorEnum.SIMULATOR
                }
            }
            // 光传感器
            var systemService : SensorManager = MyApplication.app.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            var defaultSensor = systemService.getDefaultSensor(Sensor.TYPE_LIGHT)
            if (defaultSensor == null) {
                return PhoneOrSimulatorEnum.SIMULATOR
            }
            return PhoneOrSimulatorEnum.TELEPHONE
        }

        fun getDeviceTypeValue() : String {
            return getDeviceType().phoneOrSimulatorStr
        }
    }
}