package com.example.fragmentlesson7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.annotation.StringRes

class MainActivity : AppCompatActivity() {
    private lateinit var representative:MainRepresentative

    private lateinit var activityCallback:ActivityCallback



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // это переменная типа интерфейса MainRepresentative, будем работать через него,
        // мы можем при необходимости его занулить и ГБ его удалит
        representative = (application as App).mainRepresentative

        val textView = findViewById<TextView>(R.id.text1)

        textView.setOnClickListener{
            representative.startAsync()
        }

        if (savedInstanceState==null){
            textView.text = "0"
        }

        activityCallback = object : ActivityCallback{
            override fun update(data: Int) = runOnUiThread {
                textView.setText(data)}
            }
        }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(activityCallback)
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
    }
}

interface  ActivityCallback:UiObserver<Int> {
}