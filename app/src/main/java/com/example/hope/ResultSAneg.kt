package com.example.hope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ResultSAneg : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_saneg)

        val backbtn = findViewById<Button>(R.id.backbtn)

        backbtn.setOnClickListener {
            val intent = Intent(this , MainLayout::class.java)
            startActivity(intent)
            finish()
        }

    }
}