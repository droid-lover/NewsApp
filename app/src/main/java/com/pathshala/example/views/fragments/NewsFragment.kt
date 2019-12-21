package com.pathshala.example.views.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.pathshala.example.R
import com.pathshala.example.models.NewsHeadlines
import com.pathshala.example.viewmodels.NewsViewModel
import com.assignment.rxjavakotlinway.utils.Result
import com.pathshala.example.adapter.NewsHeadlinesAdapter
import com.pathshala.example.utils.ItemOffsetDecoration
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {

    private val newsViewModel by lazy { ViewModelProviders.of(this).get(NewsViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelChanges()
        newsViewModel.getNewsHeadlines(activity!!)
    }

    private fun observeViewModelChanges() {
        newsViewModel.newsHeadlines.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Result.Success<NewsHeadlines> -> {
                    Log.d("ComingHere", "InsideApiSuccess ${Gson().toJson(it.data)}")
                    setNews(it.data)
                }
                is Result.Failure -> {
                    Log.d("ComingHere", "InsideApiFailure ${it.throwable.localizedMessage}")
                }
            }
        })
    }

    private fun setNews(data: NewsHeadlines) {
        val spacing = resources.getDimensionPixelOffset(R.dimen.default_spacing_small)
        rvNewsHeadlines.apply {
            visibility = View.VISIBLE
            adapter = NewsHeadlinesAdapter(activity!!, data)
            layoutManager = LinearLayoutManager(activity!!)
            addItemDecoration(ItemOffsetDecoration(spacing))
        }
        rvNewsHeadlines.startLayoutAnimation()
    }

}
