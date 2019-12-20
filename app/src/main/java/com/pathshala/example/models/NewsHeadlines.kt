package com.pathshala.example.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.pathshala.example.Convertors

@Entity
data class NewsHeadlines(
    @ColumnInfo
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("totalResults")
    @ColumnInfo
    var totalResults: Int? = null,


    @SerializedName("articles")
    @ColumnInfo(name = "articles")
    @TypeConverters(Convertors::class)
    var articles: List<Articles>? = null
) {
    @PrimaryKey(autoGenerate = true)
    var newsId: Int = 0
}

