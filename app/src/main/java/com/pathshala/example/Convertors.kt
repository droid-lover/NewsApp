package com.pathshala.example

import androidx.room.TypeConverter;
import com.google.gson.Gson
import com.pathshala.example.models.Articles

class Convertors {
    @TypeConverter
    fun listToJson(value: List<Articles>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Articles>? {

        val objects = Gson().fromJson(value, Array<Articles>::class.java) as Array<Articles>
        val list = objects.toList()
        return list
    }
}