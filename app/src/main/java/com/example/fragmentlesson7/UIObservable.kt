package com.example.fragmentlesson7

import androidx.annotation.MainThread
import androidx.annotation.StringRes
/**
 *  Если что-то происходит снаружи (кто-то пингует observable), то мы внутреннему объекту observer,
 *  если он не пустой, говорим пинганись observer.update(data).
 *
 *  1. (Через onResume) Через updateObserver мы закидываем в observer наше активити и если он не пустой то мы отдаем cache в observer
 *
 *  2. в fun Update, если мы получаем снаружи данные и наш observer не пустой, то мы закидываем данные в observer и зануляем cache. Но если observer еще не готов (пустой)
 *  т.к. активити еще находится не в onResume(), то мы просто кешируем Data и в следующем вызове updateObserver достаем данные из кеша и пингуем.
 *
 */

interface UIObservable<T:Any> : UiUpdate<T> {
    fun updateObserver(uiObserver: UiObserver<T> = UiObserver.Empty())

    class  Single <T:Any> : UIObservable<T> {

        @Volatile
        private var cache: T? = null

        @Volatile
        private var observer:UiObserver<T> = UiObserver.Empty()
        /**
        1    @MainThread
         */

        @MainThread
        override fun updateObserver(uiObserver: UiObserver<T>) = synchronized(Single::class.java) {
            observer=uiObserver
            /**
             * Called when observer is not empty. (If 1 Activity death, but 2 Activity doent Start,
             * we have Date, but doesnt have Observer and we call applyChanges when we get Observer)
             */
            /**
             * Called when observer is not empty. (If 1 Activity death, but 2 Activity doent Start,
             * we have Date, but doesnt have Observer and we call applyChanges when we get Observer)
             */
            if (!observer.isEmpty()){
                cache?.let {
                    observer.update(it)
                    cache=null
                }
            }
        }
        /**
        2 @AnotherThread
         */
        /**
         * Called by Model. (If Observer is not ready, we cash the Date/
         */
        override fun update(data: T) =  synchronized(Single::class.java) {
            if (observer.isEmpty()){
                cache = data
            } else {
                cache=null
                observer.update(data)
            }
        }


    }
}
// Дженерик типа Т, типа Any, если стоит Any без восклицательного знака, то не может быть null
interface UiUpdate <T:Any> {
    fun update(data:T)
}


interface UiObserver<T: Any>: UiUpdate<T> {

    fun isEmpty(): Boolean = false

    class Empty <T:Any> :UiObserver<T> {
        override fun isEmpty() = true

        override fun update(data: T) = Unit
    }

}