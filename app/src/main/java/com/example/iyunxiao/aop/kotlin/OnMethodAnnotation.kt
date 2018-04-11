package com.example.iyunxiao.aop.kotlin

/**
 * Created by dongxiaofei on 2018/3/30.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION,AnnotationTarget.CLASS,AnnotationTarget.CONSTRUCTOR)
annotation class OnMethodAnnotation