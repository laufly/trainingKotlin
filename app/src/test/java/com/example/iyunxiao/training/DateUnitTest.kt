package com.example.iyunxiao.training

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Created by dongxiaofei on 2018/4/12.
 */
class DateUnitTest{
    @Test
    fun testTimestamp2Date(){
        val timestamp = 1234123412345
       assertEquals(DateUtil.timestamp2Date(timestamp), Date(timestamp))

    }
}