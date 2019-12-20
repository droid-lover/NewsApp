package com.assignment

import com.pathshala.example.models.NewsHeadlines
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Sachin.
 */

interface APIs {

    @GET("v2/top-headlines")
    fun getNewsHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Single<NewsHeadlines>


}