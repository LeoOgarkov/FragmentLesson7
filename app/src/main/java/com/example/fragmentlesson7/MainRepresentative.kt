package com.example.fragmentlesson7

import androidx.annotation.MainThread

@MainThread
interface MainRepresentative {
    // callback -это наше avtivity
    fun startGettingUpdates (callback:UiObserver<Int>)
    fun stopGettingUpdates ()
    fun startAsync()

    class Base(
        private  val observable:UIObservable <Int>
    ):MainRepresentative{

        private var data:Int = 0

        // после нажатия старттред, мы ждем 5 сек и проверяем после, а жива ли активити, если да , то обновляем ЮАЙ
        // если нет то меняем флаг, и при вызове новой активити и работе онстарт, флаг уже будет тру и в методе startGettingUodates
        // вызовется адейт ui. и мы обновим текствью только в живаой активити

        val thread = Thread {
            Thread.sleep(5000)
            observable.update(R.string.hello)
        }

        override fun startGettingUpdates(callback:UiObserver<Int>)=observable.updateObserver(callback)

        override fun stopGettingUpdates()=observable.updateObserver()

        override fun startAsync() {
            thread.start()
        }



    }
}