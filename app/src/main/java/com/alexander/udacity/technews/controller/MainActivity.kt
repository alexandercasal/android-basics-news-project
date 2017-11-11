package com.alexander.udacity.technews.controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.alexander.udacity.technews.R
import com.alexander.udacity.technews.model.NewsArticle
import com.alexander.udacity.technews.model.NewsRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.list_news_feeds

class MainActivity : AppCompatActivity(), NewsRecyclerAdapter.OnReadMoreClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list_news_feeds.adapter = NewsRecyclerAdapter(this, ArrayList<NewsArticle>(), this)
        list_news_feeds.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list_news_feeds.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    override fun onClickReadMore(articleURL: String) {
        Toast.makeText(this, articleURL, Toast.LENGTH_LONG).show()
    }
}
