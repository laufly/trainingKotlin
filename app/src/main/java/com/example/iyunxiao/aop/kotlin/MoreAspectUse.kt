package com.example.iyunxiao.aop.kotlin

import android.util.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*

/**
 * Created by dongxiaofei on 2018/3/30.
 */
// 1. 声明自定义注解
// 2. 声明Aspect的类
// 3. 生效
/*@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class OnMethodAnnotation{
}*/

@Aspect
class OnMethodRunAspect{
    @Pointcut("execution(@com.example.iyunxiao.aop.kotlin.OnMethodAnnotation * *..*.*(..))")
   // @Pointcut("@annotation(com.example.iyunxiao.aop.kotlin.OnMethodAnnotation)")
    fun onKotlinMethodRun(){}

    @After("onKotlinMethodRun()")
    fun methodRun(joinPoint: JoinPoint){
        Log.i("joinPoint","kotlin:"+joinPoint.toString())
    }

}
