package com.pathshala.example.views.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.assignment.rxjavakotlinway.viewmodels.ExampleViewModel
import com.google.gson.Gson
import com.pathshala.example.R

//Free API used from - https://gorest.co.in/
// username - SuperHotFire
// password - mnbvcxz
/**
 * Garage Fragment
 */
class GarageFragment : Fragment() {

    private val offersViewModel by lazy {
        ViewModelProviders.of(this).get(ExampleViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelChanges()
        offersViewModel.getPopularMovies()
//        offersViewModel.getTopRatedMovies()
    }

    private fun observeViewModelChanges() {
        offersViewModel.popularMovies.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is com.assignment.rxjavakotlinway.utils.Result.Success<String> -> {
                    Log.d("ComingHere", "InsideApiSuccess ${Gson().toJson(it.data)}")
                }
                is com.assignment.rxjavakotlinway.utils.Result.Failure -> {
                    Log.d("ComingHere", "InsideApiFailure")
                }
            }
        })
    }

}
