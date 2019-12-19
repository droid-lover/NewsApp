package com.assignment.rxjavakotlinway.viewmodels

import androidx.lifecycle.ViewModel
import com.rentomojo.repository.ExampleRepo

class ExampleViewModel : ViewModel() {

    private val repo = ExampleRepo()
    var showProgressBar = repo.showProgressBar
    val recipes = repo.recipes
    val users = repo.users
    val popularMovies = repo.popularMovies

    override fun onCleared() {
        super.onCleared()
        repo.onCleared()
    }

    fun loadRecipesList() = repo.loadRecipesList()
    fun loadUsersList() = repo.loadUsersList()
    fun loadPhotos() = repo.loadPhotos()
    fun getCountries() = repo.getCountries()
    fun getPopularMovies() = repo.getPopularMovies()
    fun getTopRatedMovies() = repo.getTopRatedMovies()
}

