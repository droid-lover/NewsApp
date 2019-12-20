package com.pathshala.example.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pathshala.example.R
import com.pathshala.example.views.fragments.NewsFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setNewsFragment()
    }

    private fun setNewsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.rlContainer, NewsFragment())
            .commitAllowingStateLoss()
    }

}
