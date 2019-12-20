package com.pathshala.example.views.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.pathshala.example.R
import com.pathshala.example.models.NewsHeadlines
import com.pathshala.example.viewmodels.NewsViewModel
import com.assignment.rxjavakotlinway.utils.Result

class NewsFragment : Fragment() {

    private val newsViewModel by lazy { ViewModelProviders.of(this).get(NewsViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelChanges()
        newsViewModel.getNewsHeadlines(activity!!)
    }

    private fun observeViewModelChanges() {
        newsViewModel.newsHeadlines.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Result.Success<NewsHeadlines> -> {
                    Log.d("ComingHere", "InsideApiSuccess ${Gson().toJson(it.data)}")
                }
                is Result.Failure -> {
                    Log.d("ComingHere", "InsideApiFailure ${it.throwable.localizedMessage}")
                }
            }
        })
    }

}
