package com.example.fragmentlesson7

import android.os.Looper
import android.util.Log
import androidx.annotation.MainThread
import kotlin.concurrent.thread

@MainThread
interface MainRepresentative {
    // callback -это наше avtivity
    fun startGettingUpdates (callback:UiObserver<Int>)
    fun stopGettingUpdates ()
    fun startAsync1()
    fun startAsync2()
    fun startAsync3()
    fun checkTotalProgress():Int
    fun clearTimer()

    class Base(
        private  val observable:UIObservable <Int>
    ):MainRepresentative{

        private var data:Int = 0

        // после нажатия старттред, мы ждем 5 сек и проверяем после, а жива ли активити, если да , то обновляем ЮАЙ
        // если нет то меняем флаг, и при вызове новой активити и работе онстарт, флаг уже будет тру и в методе startGettingUodates
        // вызовется адейт ui. и мы обновим текствью только в живаой активити



        override fun startGettingUpdates(callback:UiObserver<Int>)=observable.updateObserver(callback)

        override fun stopGettingUpdates()=observable.updateObserver()

        override fun startAsync1() {
            val thread = Thread {
                while (observable.getTotalProgress()<100){

                    if(observable.getTotalProgress()<100) {
                        Thread.sleep(3000)
                        observable.update(1)}
                }
                return@Thread
            }
            thread.start()
        }

        override fun startAsync2() {
            val thread = Thread {
                while (observable.getTotalProgress()<100){

                    if(observable.getTotalProgress()<100) {
                        Thread.sleep(2000)
                    observable.update(10)}
                }
                return@Thread
            }
            thread.start()
        }

        override fun startAsync3() {
            val thread = Thread {
                while (observable.getTotalProgress()<100){

                    if(observable.getTotalProgress()<100) {
                        Thread.sleep(1000)
                        Log.i("090LO", Thread.currentThread().toString())
                        observable.update(1)}
                }
                return@Thread
            }
            thread.start()
        }

        override fun checkTotalProgress():Int {
            return observable.getTotalProgress()
        }

        override fun clearTimer() {
            observable.clearProgress()
        }
    }
}