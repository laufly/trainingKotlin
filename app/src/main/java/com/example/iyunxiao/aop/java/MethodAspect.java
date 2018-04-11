package com.example.iyunxiao.aop.java;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by dongxiaofei on 2018/4/2.
 */
@Aspect
public class MethodAspect {
    @Pointcut("@annotation(com.example.iyunxiao.aop.java.JavaAnnotation)")
    public void onJavaMethodRun(){}

    @Before("onJavaMethodRun()")
    public void methodRun(JoinPoint joinPoint){
        Log.i("joinPoint" ,"java: "+"method run");
    }

}
