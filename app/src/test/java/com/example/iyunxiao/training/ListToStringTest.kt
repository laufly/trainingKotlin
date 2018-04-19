package com.example.iyunxiao.training

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by dongxiaofei on 2018/4/12.
 */
class ListToStringTest{
    @Test
    fun testList2String(){
        val list = listOf<Int>(1,4)
        var resultStr = list.joinToString(",")
        assertEquals(resultStr,"1,4")
        var correntResultStr = list.joinToString(",",prefix = "[",postfix = "]")
        assertEquals(correntResultStr,"[1,4]")
    }
}