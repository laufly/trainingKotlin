package com.example.iyunxiao.kodein

import com.example.iyunxiao.bean.User
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton

/**
 * Created by iyunxiao on 2018/3/30.
 */
//前置步骤 引包：
// 使用步骤
// 1、创建model
var testModule = Kodein.Module{
    bind<User>() with singleton { User() }
}
// 2、创建kodein对象
var kodein = Kodein{
    import(testModule)
}
// 3、使用
var user: User = kodein.instance()

fun getUserInfo(): User {
    user.name = "dxf"
    user.password = "asd"
    return user
}
