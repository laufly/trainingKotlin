package com.example.iyunxiao.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.iyunxiao.RxBus
import com.example.iyunxiao.bean.User
import com.example.iyunxiao.training.R
import kotlinx.android.synthetic.main.activity_rxbus.*

class RxBusActivity : AppCompatActivity() {
     var index:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxbus)
        btn_post.setOnClickListener{
            val user = User("afei"+index.toString(),index.toString()+"456789")
            RxBus.post(user)
            index++
        }

        btn_subscribe.setOnClickListener{
            RxBus.toSubscribe(User::class.java).subscribe{
                Log.i("RxBus",it.toString())
                showMessage("name: "+it.name+"password: "+it.password)
            }
        }
    }

    fun Context.showMessage(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}
