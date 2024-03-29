package com.pathshala.example.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assignment.ApiClient
import com.assignment.rxjavakotlinway.utils.Result
import com.assignment.utils.C
import com.google.gson.Gson
import com.pathshala.example.database.NewsDatabase
import com.pathshala.example.models.NewsHeadlines
import com.rentomojo.repository.Repo
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class NewsRepo : Repo() {

    private val _newsHeadlines = MutableLiveData<Result<NewsHeadlines>>()
    val newsHeadlines: LiveData<Result<NewsHeadlines>> = _newsHeadlines


    fun getNewsHeadlines(context: Context) {
        _showProgressBar.value = true

        disposables.add(
            Maybe.fromCallable {
                NewsDatabase.getDatabase(context).newsDao().getNewsHeadlinesFromDB()
            }.toSingle().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsHeadlines>() {
                    override fun onSuccess(newsHeadlines: NewsHeadlines) {
                        Log.d("ComingHere", "inside_onSuccess ${Gson().toJson(newsHeadlines)}")
                        _newsHeadlines.postValue(Result.Success(newsHeadlines))
                    }

                    override fun onError(e: Throwable) {
                        Log.d("ComingHere", "inside_onError ${e.localizedMessage}")
                        getNewsHeadlinesFromServer(context)
                    }

                })
        )
    }

    private fun getNewsHeadlinesFromServer(context: Context) {
        disposables.add(
            ApiClient.client.getNewsHeadlines("us", C.API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableSingleObserver<NewsHeadlines>() {
                    override fun onSuccess(data: NewsHeadlines) {
                        _newsHeadlines.postValue(Result.Success(data))
                        saveNewsInDb(context, data)
                        _showProgressBar.postValue(false)
                    }

                    override fun onError(throwable: Throwable) {
                        _newsHeadlines.postValue(Result.Failure(throwable))
                        _showProgressBar.postValue(false)
                    }
                })
        )
    }

    private fun saveNewsInDb(context: Context, data: NewsHeadlines) {
        val news = NewsHeadlines(data.status, data.totalResults, data.articles)
        Observable.fromCallable {
            NewsDatabase.getDatabase(context).newsDao()
                .insertNews(news)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }


}
