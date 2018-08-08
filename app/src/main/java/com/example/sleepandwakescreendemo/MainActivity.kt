package com.example.sleepandwakescreendemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent


class MainActivity : AppCompatActivity() {
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gestureDetector = GestureDetector(this, WorkGestureDetectorListener(this))
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    class WorkGestureDetectorListener(context: Context) : GestureDetector.SimpleOnGestureListener() {
        private val mTAG = "MainActivity"
        private val mDEBUG = BuildConfig.DEBUG
        private val mContext = context

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            if (mDEBUG) {
                Log.d(mTAG, "onDoubleTap()")
            }
            val intent = Intent(mContext, VirtualLockScreenActivity::class.java)
            mContext.startActivity(intent)
            return super.onDoubleTap(e)
        }
    }
}
