package com.example.iyunxiao.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
import kotlinx.android.synthetic.main.dialog_weixin_code.view.*
import kotlinx.android.synthetic.main.title.*
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var qrCodeDialog:Dialog
    companion object {
        val TAG = MainActivity::class.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        apb.setMaxProgress(100f)
//        apb.setProgress(85.5f)
        addTextView()
        addImageView()
//        startLunchActivity()
//        Observable.fromArray("22","haha","").subscribeOn(AndroidSchedulers.mainThread()).subscribe(object:Observer<String>{
//            override fun onComplete() {
//            Log.i("info","complete")
//            }
//
//            override fun onNext(value: String?) {
//                showToast(value!!)
//            }
//
//            override fun onError(e: Throwable?) {
//                Log.e("error",e!!.stackTrace.toString())
//            }
//
//            override fun onSubscribe(d: Disposable?) {
//                Log.i("info","onSubscribe")
//            }
//        })
//
//        FirstFragment().apply { this@MainActivity.supportFragmentManager.beginTransaction().replace(R.id.fl_content,this).commit()  }
//        Flowable.create(FlowableOnSubscribe<Int>{
//            it.onNext(555)
//            it.onNext(666)
//        },BackpressureStrategy.BUFFER).subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe { it->showToast(it!!.toString())  }
//        Flowable.just("sdd","sd2","sd3").map { it.length>2 }
//        testWith()
//        showUser(getUser())
//        showUser(getUserInfo())
//        showUser(getUser())
//        testArray()
//        testRxjava2ByKotlin()
//        Thread(){
//            testChuck()
//        }



        btn_navigate.setOnClickListener{
            startActivity(Intent(this@MainActivity,RxBusActivity::class.java))
        }
        btn_webview.setOnClickListener{
            startActivity(Intent(this@MainActivity,WebviewActivity::class.java))
        }
        btn_wxwebview.setOnClickListener{
            startActivity(Intent(this@MainActivity,ReplayActivity::class.java))
        }
        btn_sendcode.setOnClickListener{
            startActivity(Intent(this@MainActivity,CodeSendActivity::class.java))
        }
        btn_showResult.setOnClickListener{
           // showDialog()
           // startActivity(Intent(this@MainActivity,RecordPlayActivity::class.java))

        }
        btn_corountine.setOnClickListener {
            startActivity(Intent(this@MainActivity,CorountineActivity::class.java))
        }
    }


    fun Dialog.safeShow(width: Int, height: Int) {
        try {
            this.show()
            val layoutParams = window.attributes
            layoutParams.height = height
            layoutParams.width = width
            window.attributes = layoutParams
        } catch (e: Exception) {
        }
    }

    fun showDialog(){
        val payMethod = "微信"
        val height = completeHeight()
        val view = View.inflate(this@MainActivity,R.layout.dialog_weixin_code, null).apply {
            closeIv.setOnClickListener {
                qrCodeDialog?.dismiss()
            }
            moneyTv.text = "使用${payMethod}支付50元"
        }
        qrCodeDialog = AlertDialog.Builder(this@MainActivity)
                .setView(view)
                .create()
        qrCodeDialog?.safeShow(height,height)
    }

    private fun completeHeight():Int{
        return Math.min(450,340)
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


      fun addTextView(){
        val resultTv = TextView(this@MainActivity).apply {
          //  width = ViewGroup.LayoutParams.MATCH_PARENT
          //  height = ViewGroup.LayoutParams.WRAP_CONTENT
            textSize = 12f
            gravity = Gravity.CENTER_VERTICAL
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 10, 0, 10)
            layoutParams = params
          //  setPadding(10, 5, 10, 5)
        }
        resultTv.text = "我的文字"
          btnLL.addView(resultTv)
      }

    fun addImageView(){
        for(i in 1..10){
            val imageView = ImageView(this@MainActivity).apply {
                val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                params.setMargins(0, 10, 0, 10)
                layoutParams = params
                setPadding(10, 5, 10, 5)
                background = resources.getDrawable(R.drawable.close)
            }
            btnLL.addView(imageView)
        }

    }

}
