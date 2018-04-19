package com.example.iyunxiao

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

/**
 * Created by dongxiaofei on 2018/4/4.
 */
object RxBus{
    // RxBus 一共有三种实现方式 RxBus 最基本的实现

   // 支持注解方式订阅事件。
   // 支持粘滞事件。
    // 1.Publish Bus( RxBus，参见 PublishSubject )
    // Behavior Bus(参见 BehaviorSubject)
    // Replay Bus(参见ReplaySubject)

    // 1.创建一个单例的bus对象，用来发送和接受任意类型的数据
    private val bus :Relay<Any> = PublishRelay.create<Any>().toSerialized()

    // 2.声明post 方法
    fun post(event: Any){
        bus.accept(event)
    }
    //3.接受方法
     fun<T> toSubscribe(clazz: Class<T>):Flowable<T>{
     /*
     *
     * OnNext events are written without any buffering or dropping.
     * Downstream has to deal with any overflow.
     * <p>Useful when one applies one of the custom-parameter onBackpressureXXX operators.
     MISSING,
     *
     * Signals a MissingBackpressureException in case the downstream can't keep up.
     *
     ERROR,
     *
     * Buffers <em>all</em> onNext values until the downstream consumes it.
     *
     BUFFER,
     *
     * Drops the most recent onNext value if the downstream can't keep up.
     *
     DROP,
     *
     * Keeps only the latest onNext value, overwriting any previous value if the
     * downstream can't keep up.
     *
     LATEST
     *
     * */

        return bus.ofType(clazz).toFlowable(BackpressureStrategy.DROP)
    }
}