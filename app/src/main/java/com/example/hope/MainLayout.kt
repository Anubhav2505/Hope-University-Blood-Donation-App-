package com.example.hope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class MainLayout : AppCompatActivity() {


    private val rotateOpen: Animation by lazy {AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy {AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy {AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy {AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim)}

        private var clicked = false
    val add_btn : FloatingActionButton
    get() = findViewById<FloatingActionButton>(R.id.floatingActionButton)
    val raiseReqBtn : FloatingActionButton
    get() = findViewById<FloatingActionButton>(R.id.raiseRequestbtn)
    val selfAssesBtn : FloatingActionButton
    get() = findViewById<FloatingActionButton>(R.id.SelfAssesbtn)

    val logoutBtn : FloatingActionButton
    get() = findViewById(R.id.logoutBtn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)
         // Hide status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

          // mapping with bottom navigation components
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView)
        bottomNavView.setupWithNavController(navController)





        logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this , mainscreen::class.java)
            startActivity(intent)
            finish()
        }

        add_btn.setOnClickListener {
                 onAddButtonClicked()
        }

        raiseReqBtn.setOnClickListener {
           val intent = Intent(this, Raise_BloodRequest::class.java)
            startActivity(intent)
        }

        selfAssesBtn.setOnClickListener {
            val intent = Intent(this , splash_SelfAsses::class.java)
            startActivity(intent)
        }


    }

    private fun onAddButtonClicked() {
       setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setClickable(clicked: Boolean) {
    if (!clicked){
        raiseReqBtn.isClickable = true
        selfAssesBtn.isClickable = true
        logoutBtn.isClickable = true
    }else{
        raiseReqBtn.isClickable = false
        selfAssesBtn.isClickable = false
        logoutBtn.isClickable = false
    }
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            raiseReqBtn.startAnimation(fromBottom)
            selfAssesBtn.startAnimation(fromBottom)
            logoutBtn.startAnimation(fromBottom)
            add_btn.startAnimation(rotateOpen)
        }else{
            raiseReqBtn.startAnimation(toBottom)
            selfAssesBtn.startAnimation(toBottom)
            logoutBtn.startAnimation(toBottom)
            add_btn.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
      if(!clicked){
          raiseReqBtn.visibility = View.VISIBLE
          selfAssesBtn.visibility = View.VISIBLE
          logoutBtn.visibility = View.VISIBLE
      }else{
          raiseReqBtn.visibility = View.INVISIBLE
          selfAssesBtn.visibility = View.INVISIBLE
          logoutBtn.visibility = View.VISIBLE
      }
    }
}