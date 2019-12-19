package com.rentomojo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assignment.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import com.assignment.rxjavakotlinway.utils.Result
import com.assignment.utils.C

class ExampleRepo : Repo() {

    private val _popularMovies = MutableLiveData<Result<String>>()
    val popularMovies: LiveData<Result<String>> = _popularMovies

    private val _topRatedMovies = MutableLiveData<Result<String>>()
    val topRatedMovies: LiveData<Result<String>> = _topRatedMovies

    fun getPopularMovies() {

        _showProgressBar.value = true

        disposables.add(
                ApiClient.client.getPopularMovies(C.themoviedbAPI_KEY, C.ENGLISH, 1)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(object : DisposableSingleObserver<String>() {
                            override fun onSuccess(recipes: String) {
                                _popularMovies.postValue(Result.Success(recipes))
                                _showProgressBar.postValue(false)
                            }

                            override fun onError(throwable: Throwable) {
                                _popularMovies.postValue(Result.Failure(throwable))
                                _showProgressBar.postValue(false)
                            }
                        })
        )
    }

    fun getTopRatedMovies() {

        _showProgressBar.value = true

        disposables.add(
                ApiClient.client.getTopRatedMovies(C.themoviedbAPI_KEY, C.ENGLISH, 1)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(object : DisposableSingleObserver<String>() {
                            override fun onSuccess(recipes: String) {
                                _topRatedMovies.postValue(Result.Success(recipes))
                                _showProgressBar.postValue(false)
                            }

                            override fun onError(throwable: Throwable) {
                                _topRatedMovies.postValue(Result.Failure(throwable))
                                _showProgressBar.postValue(false)
                            }
                        })
        )
    }
}
