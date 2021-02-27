package com.example.iyunxiao.training

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.iyunxiao.training", appContext.packageName)
    }
    @Test
    fun usess() {
        val map = mapOf(Pair(1, listOf(1,2,3)),Pair(2, listOf(4,5,6)),Pair(3, listOf(7,8,9)))
        var result = 0
        map.forEach out@{
            it.value.forEach {
                if (it > 4) {
                    result = it
                    return@out
                }
            }
        }
        System.out.print(result)
    }

}
