package com.example.iyunxiao.activity

import android.graphics.Color.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.iyunxiao.training.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_code_send.*
import java.util.concurrent.TimeUnit

class CodeSendActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_send)
        btn_code.setOnClickListener{
            onCodeClick()
        }
    }

    fun onCodeClick(){
        val count :Long = 60
        Observable.interval(0,1,TimeUnit.SECONDS)
                  .take(count+1)
//                .map(object :Function<Long,Long>{
//                    override fun apply(t: Long?): Long {
//                       return count-t!!
//
//                    }
//                })
                .map{
                    count-it
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{
                    btn_code.isEnabled =false
                    btn_code.setTextColor(GRAY)
                }.subscribe(object : Observer<Long>{
                    override fun onComplete() {
                        btn_code.isEnabled = true
                        btn_code.setTextColor(BLACK)
                        btn_code.text = "发送验证码"
                          }

                    override fun onError(e: Throwable?) {
                        Log.e("error","错误信息："+e?.printStackTrace().toString());
                         }

                    override fun onNext(value: Long?) {
                    btn_code.text = value.toString()+"秒"
                    btn_code.setTextColor(RED)
                     }

                    override fun onSubscribe(d: Disposable?) {
                    }
                } )
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
