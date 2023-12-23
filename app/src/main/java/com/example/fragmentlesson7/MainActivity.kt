package com.example.fragmentlesson7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isInvisible

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
        val processBar = findViewById<ProgressBar>(R.id.progressBar)
        processBar.max=100
        val buttonRestart = findViewById<Button>(R.id.button)

        buttonRestart.setOnClickListener{
            if (representative.checkTotalProgress()>=100 && textView.isClickable){
                representative.clearTimer()
                textView.text="0"
                processBar.progress=0
            }
        }

        textView.setOnClickListener{
            textView.isClickable=false
            representative.startAsync1()
            representative.startAsync2()
            representative.startAsync3()
            if (processBar.progress==100){
                textView.isClickable=true
            }
        }

        if (savedInstanceState==null){
            textView.text = "0"
        }

        activityCallback = object : ActivityCallback{
            override fun update(data: Int) = runOnUiThread {
                processBar.progress=data
                if (data<=100)
                {textView.text = data.toString()
                    } else {textView.text="100"}
                if (processBar.progress==100){
                    textView.isClickable=true
                }

            }
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