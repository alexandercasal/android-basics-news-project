package com.alexander.udacity.technews.controller

import android.app.LoaderManager
import android.content.Loader
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.alexander.udacity.technews.R
import com.alexander.udacity.technews.model.BASE_URL
import com.alexander.udacity.technews.model.NewsArticle
import com.alexander.udacity.technews.model.NewsArticleAsyncLoader
import com.alexander.udacity.technews.model.NewsRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.label_empty_list
import kotlinx.android.synthetic.main.activity_main.list_news_feeds

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<MutableList<NewsArticle>>,
        NewsRecyclerAdapter.OnReadMoreClickListener {

    private val LOADER_NEWS_ARTICLES = 1
    private lateinit var mNewsAdapter: NewsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNewsAdapter = NewsRecyclerAdapter(this, ArrayList<NewsArticle>(), this)
        list_news_feeds.adapter = mNewsAdapter
        list_news_feeds.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list_news_feeds.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        label_empty_list.visibility = View.GONE

        loaderManager.initLoader(LOADER_NEWS_ARTICLES, null, this)
    }

    override fun onClickReadMore(articleURL: String) {
        Toast.makeText(this, articleURL, Toast.LENGTH_LONG).show()
    }

    override fun onCreateLoader(id: Int, p1: Bundle?): Loader<MutableList<NewsArticle>>? {
        return when (id) {
            LOADER_NEWS_ARTICLES -> {
                return NewsArticleAsyncLoader(this, BASE_URL)
            }
            else -> null
        }
    }

    override fun onLoadFinished(loader: Loader<MutableList<NewsArticle>>, data: MutableList<NewsArticle>?) {
        when (loader.id) {
            LOADER_NEWS_ARTICLES -> {
                mNewsAdapter.clear()

                if (data != null && data.isNotEmpty()) {
                    mNewsAdapter.addAll(data)
                }
            }
        }
    }

    override fun onLoaderReset(loader: Loader<MutableList<NewsArticle>>) {
        mNewsAdapter.clear()
    }
}
