package com.example.iyunxiao.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.iyunxiao.training.R
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class CorountineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corountine)
        launch(CommonPool){
            delay(1000L)
            Toast.makeText(this@CorountineActivity,"执行完耗时操作",Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this,"进入",Toast.LENGTH_SHORT).show()
    }
}