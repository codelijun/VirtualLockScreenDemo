package com.example.sleepandwakescreendemo

import android.app.Activity
import android.content.Context
import android.provider.Settings
import android.util.Log
import android.view.View

object SystemUtil {
    private val TAG = "SystemUtil"
    private val DEBUG = BuildConfig.DEBUG
    fun setScreenOffTimeout(context: Context, time: Int) {
        if (DEBUG) {
            Log.d(TAG, "setScreenOffTimeout() screen off timeout: $time")
        }
        try {
            Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, time)
        } catch (e: Exception) {
            if (DEBUG) {
                throw e
            }
        }
    }

    fun getScreenOffTimeout(context: Context, defValue: Int): Int {
        var screenOffTimeout = defValue
        try {
            screenOffTimeout = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT)
        } catch (e: Exception) {
            if (DEBUG) {
                throw e
            }
        }
        if (DEBUG) {
            Log.d(TAG, "getScreenOffTimeout() screen off timeout: $screenOffTimeout")
        }
        return screenOffTimeout
    }

    fun setFullScreen(activity: Activity) {
        val decorView = activity.window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }

    fun setWindowBrightness(activity: Activity, value: Float) {
        try {
            val params = activity.window.attributes
            params.screenBrightness = value
            activity.window.attributes = params
        } catch (e: Exception) {
            if (DEBUG) {
                throw e
            }
        }
    }
}