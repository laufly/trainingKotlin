package com.example.iyunxiao.aop.kotlin

import android.util.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut

/**
 * Created by dongxiaofei on 2018/3/30.
 */

// 单个方法的使用
@Aspect
class ActivityAspect{
    @Pointcut("execution(* *.onCreate(..)) || execution(* *.OnResume())")
    fun logForActivityOnMethod(){}

    @Before("logForActivityOnMethod()")
    fun log(joinPoint:JoinPoint){
        Log.i("joinPoint",joinPoint.toString())
    }
}

