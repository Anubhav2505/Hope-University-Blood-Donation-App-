package com.example.hope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ResultSA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_sa)


        val backBtn = findViewById<Button>(R.id.backbtnpos)
         backBtn.setOnClickListener {
             val intent = Intent(this , MainLayout::class.java)
             startActivity(intent)
             finish()
        }
    }
}