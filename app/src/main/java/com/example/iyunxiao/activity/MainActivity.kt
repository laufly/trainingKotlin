package com.example.iyunxiao.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.common.fragment.FirstFragment
import com.example.iyunxiao.bean.User
import com.example.iyunxiao.aop.java.JavaAnnotation
import com.example.iyunxiao.aop.kotlin.OnMethodAnnotation
import com.example.iyunxiao.kodein.kodein
import com.example.iyunxiao.training.R
import com.github.salomonbrys.kodein.instance
import com.readystatesoftware.chuck.Chuck
import com.readystatesoftware.chuck.ChuckInterceptor
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startLunchActivity()
        Observable.fromArray("22","haha","").subscribeOn(AndroidSchedulers.mainThread()).subscribe(object:Observer<String>{
            override fun onComplete() {
            Log.i("info","complete")
            }

            override fun onNext(value: String?) {
                showToast(value!!)
            }

            override fun onError(e: Throwable?) {
                Log.e("error",e!!.stackTrace.toString())
            }

            override fun onSubscribe(d: Disposable?) {
                Log.i("info","onSubscribe")
            }
        })

        FirstFragment().apply { this@MainActivity.supportFragmentManager.beginTransaction().replace(R.id.fl_content,this).commit()  }
        Flowable.create(FlowableOnSubscribe<Int>{
            it.onNext(555)
            it.onNext(666)
        },BackpressureStrategy.BUFFER).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { it->showToast(it!!.toString())  }
        Flowable.just("sdd","sd2","sd3").map { it.length>2 }
        testWith()
        showUser(getUser())
        showUser(getUserInfo())
        showUser(getUser())
        testArray()
        testRxjava2ByKotlin()
        Thread(){
            testChuck()
        }
        btn_navigate.setOnClickListener{
            startActivity(Intent(this@MainActivity,RxBusActivity::class.java))
        }
    }

    fun startLunchActivity(){
        startActivity(Chuck.getLaunchIntent(this@MainActivity))
    }

    fun testChuck(){
      var client =  OkHttpClient.Builder()
                                .addInterceptor(ChuckInterceptor(this@MainActivity)).build()
        var requestbody = FormBody.Builder().add("","").build()
        var request = Request.Builder().url("https://www.baidu.com").post(requestbody).build()
        var call =client.newCall(request)
        call.enqueue(object:Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                Log.e("OkHttp","exception: "+e.toString());
                  }

            override fun onResponse(call: Call?, response: Response?) {
                Log.i("Okhttp","success: "+response!!.body().string());

                  }

        })

    }

    fun testArray(){
        var array = listOf("apple","orange","banana")
        for (index in array.indices){
            Log.i("array","index:"+index+" value:"+array[index])
        }
    }

    fun showUser(user: User){
        showToast("name:"+user.name+" password:"+user.password)
    }
    @JavaAnnotation
    fun testWith()= with("Test with String"){
        this.forEach {
            Log.i(TAG,it.toString())
        }
       showToast(this)
    }

    @OnMethodAnnotation
    fun getUser(): User {
        return kodein.instance()
    }

    @OnMethodAnnotation
    fun getUserInfo(): User {
        // 3、使用
        var user: User = kodein.instance()
        user.name = "dxf"
        user.password = "asd"
        return user
    }

    fun Context.showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    fun testRxjava2ByKotlin(){
        Observable.create(object:ObservableOnSubscribe<Integer>{
            override fun subscribe(e: ObservableEmitter<Integer>?){
                e!!.onNext(Integer(2))
                e!!.onNext(Integer(3))
                e!!.onNext(Integer(4))
                e!!.onComplete()
            }
        }).subscribe (object:Observer<Integer>{
           lateinit var disposable:Disposable
            override fun onComplete() {
            Log.i("result","complete")
            }

            override fun onError(e: Throwable?) {
            Log.e("result",e!!.stackTrace.toString())
            }

            override fun onNext(value: Integer?) {
            Log.i("result",value.toString())
            if(value!!.toInt()%2==0){
                Log.i("result",value.toString())
            }else{
                disposable.dispose()
            }
            }

            override fun onSubscribe(d: Disposable?) {
                disposable  = d!!
            }
        })
    }

}
