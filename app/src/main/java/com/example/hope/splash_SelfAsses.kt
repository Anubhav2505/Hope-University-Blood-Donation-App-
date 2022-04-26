package com.example.hope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class splash_SelfAsses : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_self_asses)


       Handler().postDelayed({
           val intent  = Intent(this ,SelfAssessment::class.java )
           startActivity(intent)
           finish()
       }, 3000)

    }
}