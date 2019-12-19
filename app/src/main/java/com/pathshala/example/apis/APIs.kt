package com.assignment

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Sachin.
 */

interface APIs {

    //BASE_URL_MOVIES
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String,
                         @Query("language") language: String,
                         @Query("page") page: Int): Single<String>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String,
                          @Query("language") language: String,
                          @Query("page") page: Int): Single<String>

//append urls to this http://image.tmdb.org/t/p/w185/{URL}

}