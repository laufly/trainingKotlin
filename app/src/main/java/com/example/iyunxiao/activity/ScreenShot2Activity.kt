package com.example.iyunxiao.activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.iyunxiao.training.R
import kotlinx.android.synthetic.main.activity_screen_shot2.*
import android.content.Context.TELEPHONY_SERVICE
import android.telephony.TelephonyManager
import android.util.Log
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method


class ScreenShot2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_shot2)
        //showResult2()
        tv_title.text = ""+getSecondImsi(this)
    }

    fun showResult1(){
        ll_question.isDrawingCacheEnabled = true
        ll_question.buildDrawingCache()
        var bitmap =ll_question.drawingCache
        iv_result.setImageBitmap(bitmap)
    }
    fun showResult(){
        ll_question.isDrawingCacheEnabled = true
        ll_question.buildDrawingCache()
        var bitmap =ll_question.drawingCache
        bitmap.setHasAlpha(false)
        bitmap.prepareToDraw()
        iv_result.setImageBitmap(bitmap)
    }
    fun showResult2(){
        var bitmap = Bitmap.createBitmap(ll_question.width,ll_question.height,Bitmap.Config.RGB_565)
        var canvas = Canvas(bitmap)
        canvas.translate(-ll_question.scaleX,-ll_question.scaleY)
        ll_question.draw(canvas)
        iv_result.setImageBitmap(bitmap)
    }

    @Throws(InvocationTargetException::class, IllegalAccessException::class, NoSuchMethodException::class)
    fun getSecondImsi(context: Context): Int {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val telephonyclass = telephonyManager.javaClass
        var method: Method? = null
        var param = true
        try {
            method = telephonyclass.getDeclaredMethod("getSubscriberId", Int::class.javaPrimitiveType)

        } catch (e: NoSuchMethodException) {
         Log.e(
                 "",e.printStackTrace().toString()
         )
        }

        var `object`: Any? = null
        val buildSet = hashSetOf<String>()
        if (param) {
            for (i in 0..8) {
                `object` = method!!.invoke(telephonyManager, i)
                if (`object` != null) {
                    buildSet.add(`object`!!.toString())
                }
            }
        }

        return 0
    }
}
