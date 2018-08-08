package com.example.sleepandwakescreendemo

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log

class VirtualLockScreenActivity : Activity() {
    private val TAG = "VirtualLockScreen"
    private val DEBUG = BuildConfig.DEBUG
    private var mOldScreenOffTime = 30 * 1000
    private val mScreenOffTime = 10 * 1000

    private val mScreenReceiver = ScreenBroadcastReceiver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemUtil.setFullScreen(this)
        SystemUtil.setWindowBrightness(this, 0f)
        mOldScreenOffTime = SystemUtil.getScreenOffTimeout(this, mOldScreenOffTime)
        SystemUtil.setScreenOffTimeout(this, mScreenOffTime)
        registerListener()
    }

    private fun registerListener() {
        if(DEBUG){
            Log.d(TAG, "registerListener")
        }
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        registerReceiver(mScreenReceiver, filter)
    }

    private fun unregisterListener() {
        if(DEBUG){
            Log.d(TAG, "unregisterListener")
        }
        unregisterReceiver(mScreenReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (DEBUG) {
            Log.d(TAG, "onDestroy()")
        }
        SystemUtil.setScreenOffTimeout(this, mOldScreenOffTime)
        unregisterListener()
    }

    class ScreenBroadcastReceiver(activity: Activity) : BroadcastReceiver() {
        private val TAG = "VirtualLockScreen"
        private val DEBUG = BuildConfig.DEBUG
        private val mActivity = activity
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent!!.action
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                if (DEBUG) {
                    Log.d(TAG, "屏幕关闭")
                }
                mActivity.finish()
            }
        }
    }
}
