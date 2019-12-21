package com.pathshala.example.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson

import com.pathshala.example.R
import com.pathshala.example.models.Articles
import com.pathshala.example.models.NewsHeadlines
import kotlinx.android.synthetic.main.item_news_headline.view.*
import java.text.SimpleDateFormat


/**
 * Created by Sachin.
 */
class NewsHeadlinesAdapter(private val context: Context, private val newsHealines: NewsHeadlines) :
    androidx.recyclerview.widget.RecyclerView.Adapter<NewsHeadlinesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_news_headline,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        newsHealines.articles?.also {
            holder.bindToView(it[position])
        }
    }

    override fun getItemCount(): Int {
        return newsHealines.articles?.size ?: 0
    }

    inner class ViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        private val ivNewsCover = itemView.ivNewsCover!!
        private val tvNewsDescription = itemView.tvNewsDescription!!
        private val tvPublishedDate = itemView.tvPublishedDate!!
        fun bindToView(article: Articles) {
            article.urlToImage?.also { ivNewsCover.setImageURI(it) }
            article.description?.also { tvNewsDescription.text = it }
            article.publishedAt?.also { tvPublishedDate.text = getDayFromDate(it) }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDayFromDate(date: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd").parse(date)
        return SimpleDateFormat("dd-MM-yyyy").format(date)
    }


}