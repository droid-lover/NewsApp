package com.pathshala.example.views.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.assignment.R
import com.assignment.utils.C
import com.assignment.utils.Utils
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.util.*


class SplashScreenActivity : AppCompatActivity() {

    private var permissionsToRequest: ArrayList<String>? = null
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initializeViews()
        checkIfLocationIsAccessible()
    }

    private fun initializeViews() {
        handler.postDelayed({
            tvAppName.visibility = View.VISIBLE
            tvAppName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce_anim))
        }, 1500)
    }

    private fun checkIfLocationIsAccessible() {
        if (!Utils.checkLocation(this@SplashScreenActivity)) {
            startNextActivity(LocationActivity::class.java)
            return
        }
        val permissions = ArrayList<String>()
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        permissionsToRequest = Utils.permissionsToRequest(permissions, this@SplashScreenActivity)

        var activity: Class<out AppCompatActivity>? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity = if (permissionsToRequest!!.size > 0) {
                LocationActivity::class.java
            } else {
                HomeActivity::class.java
            }

        }
        startNextActivity(activity)
    }

    private fun startNextActivity(activity: Class<out AppCompatActivity>?) {
        val intent = Intent(this@SplashScreenActivity, activity).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        handler.postDelayed({ startActivity(intent) }, 5000)
    }

}
