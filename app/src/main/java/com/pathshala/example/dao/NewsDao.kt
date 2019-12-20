package com.pathshala.example.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pathshala.example.models.NewsHeadlines

@Dao
interface NewsDao {

    @Insert
    fun insertNews(news:NewsHeadlines)

    @Query("Select * from NewsHeadlines")
    fun getNewsHeadlinesFromDB(): NewsHeadlines
}