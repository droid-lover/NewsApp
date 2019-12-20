package com.pathshala.example.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.pathshala.example.repository.NewsRepo

class NewsViewModel : ViewModel() {

    private val repo = NewsRepo()
    var showProgressBar = repo.showProgressBar

    val newsHeadlines = repo.newsHeadlines

    override fun onCleared() {
        super.onCleared()
        repo.onCleared()
    }

    fun getNewsHeadlines(context: Context) = repo.getNewsHeadlines(context)
}

