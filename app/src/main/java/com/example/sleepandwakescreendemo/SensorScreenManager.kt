package com.example.sleepandwakescreendemo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.PowerManager
import android.util.Log

class SensorScreenManager : SensorEventListener {
    private val TAG = "SensorScreenManager"
    private lateinit var sensorManager: SensorManager
    private lateinit var localPowerManager: PowerManager
    private lateinit var localWakeLock: PowerManager.WakeLock

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event?.values
        when (event?.sensor?.type) {
            Sensor.TYPE_PROXIMITY -> {
                Log.d(TAG, values!![0]?.toString())
                if (values[0].toDouble() == 0.0) {// 贴近手机
                    Log.d(TAG, "hands up in calling activity: ${localWakeLock.isHeld}")
                    if (localWakeLock.isHeld) {
                        return
                    } else {
                        localWakeLock.setReferenceCounted(false)
                        localWakeLock.release() // 释放设备电源锁
                    }
                } else {// 远离手机
                    Log.d(TAG, "hands moved in calling activity:  ${localWakeLock.isHeld}")
                    if (localWakeLock.isHeld) {
                        return
                    } else {
                        localWakeLock.acquire()// 申请设备电源锁
                    }
                }
            }
        }
    }

    fun onStart(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        localPowerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        localWakeLock = this.localPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG)

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_UI)
    }

    fun onDestory() {
        sensorManager.unregisterListener(this)
    }

}