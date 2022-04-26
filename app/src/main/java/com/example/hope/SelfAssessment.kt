package com.example.hope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SelfAssessment : AppCompatActivity() {

    private var clicks = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_assessment)

        val q1Y = findViewById<Button>(R.id.q1BtnY)
        val q1N = findViewById<Button>(R.id.q1BtnN)
        val q2Y = findViewById<Button>(R.id.q2BtnY)
        val q2N = findViewById<Button>(R.id.q2BtnN)
        val q3Y = findViewById<Button>(R.id.q3BtnY)
        val q3N = findViewById<Button>(R.id.q3BtnN)
        val q4Y = findViewById<Button>(R.id.q4BtnY)
        val q4N = findViewById<Button>(R.id.q4BtnN)
        val q5Y = findViewById<Button>(R.id.q5BtnY)
        val q5N = findViewById<Button>(R.id.q5BtnN)
        val subBtn = findViewById<Button>(R.id.submitBtn)

        q1Y.setOnClickListener {
            clicks += 1

        }

        q1N.setOnClickListener {
            val intent = Intent(this , ResultSAneg::class.java)
            startActivity(intent)
            finish()
        }


        q2Y.setOnClickListener {
            clicks += 1
        }

        q2N.setOnClickListener {
            val intent = Intent(this , ResultSAneg::class.java)
            startActivity(intent)
            finish()
        }


        q3Y.setOnClickListener {
            val intent = Intent(this , ResultSAneg::class.java)
            startActivity(intent)
            finish()
        }

        q3N.setOnClickListener {
            clicks += 1
        }

        q4Y.setOnClickListener {
            val intent = Intent(this , ResultSAneg::class.java)
            startActivity(intent)
            finish()
        }

        q4N.setOnClickListener {
            clicks += 1
        }

        q5Y.setOnClickListener {
            val intent = Intent(this , ResultSAneg::class.java)
            startActivity(intent)
            finish()
        }

        q5N.setOnClickListener {
            clicks += 1
            if(clicks == 5){
                clicks = 0
                val intent = Intent(this , ResultSA::class.java)
                startActivity(intent)
                finish()

            }else{


            }
        }

        subBtn.setOnClickListener {
            val intent = Intent(this , ResultSA::class.java)
            startActivity(intent)
            finish()
        }


    }
}