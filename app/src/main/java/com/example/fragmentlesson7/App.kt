package com.example.fragmentlesson7

import android.app.Application
import android.util.Log

class App: Application() {
    lateinit var mainRepresentative: MainRepresentative
    private val handleDeath = HandleDeath.Base()

    override fun onCreate() {
        super.onCreate()
        mainRepresentative=MainRepresentative.Base(UIObservable.Single())

    }
    fun activityCreated (firstOpening:Boolean){
        if (firstOpening){
            Log.i("090LO", "first opening")
            //Bundle == null, It is the first opening
            handleDeath.firstOpening()
        } else {
            if (handleDeath.wasDeathHappened()){
                // Bundle is not null, It is not the first opening
                Log.i("090LO", "death happened")
                handleDeath.deathHandled()
            } else{
                Log.i("090LO", "activity recreated")
            }
        }
    }
}