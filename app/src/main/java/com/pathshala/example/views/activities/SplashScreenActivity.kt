package com.pathshala.example.views.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.pathshala.example.R
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreenActivity : AppCompatActivity() {

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initializeViews()
        startNextActivity(MainActivity::class.java)
    }

    private fun initializeViews() {
//        handler.postDelayed({
//            tvAppName.visibility = View.VISIBLE
//            tvAppName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce_anim))
//        }, 1500)
    }


    private fun startNextActivity(activity: Class<out AppCompatActivity>?) {
        val intent = Intent(this@SplashScreenActivity, activity).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        handler.postDelayed({ startActivity(intent) }, 5000)
    }

}
