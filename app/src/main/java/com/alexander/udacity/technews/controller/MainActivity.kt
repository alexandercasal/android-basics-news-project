package com.alexander.udacity.technews.controller

import android.app.LoaderManager
import android.content.Context
import android.content.Intent
import android.content.Loader
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.alexander.udacity.technews.R
import com.alexander.udacity.technews.model.BASE_URL
import com.alexander.udacity.technews.model.NewsArticle
import com.alexander.udacity.technews.model.NewsArticleAsyncLoader
import com.alexander.udacity.technews.model.NewsRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.label_empty_list
import kotlinx.android.synthetic.main.activity_main.list_news_feeds
import kotlinx.android.synthetic.main.activity_main.progress_load_news
import kotlinx.android.synthetic.main.activity_main.toolbar_main

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<MutableList<NewsArticle>>,
        NewsRecyclerAdapter.OnReadMoreClickListener {

    private val TAG = MainActivity::class.java.simpleName
    private val LOADER_NEWS_ARTICLES = 1
    private lateinit var mNewsAdapter: NewsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar_main)

        mNewsAdapter = NewsRecyclerAdapter(this, ArrayList<NewsArticle>(), this)
        list_news_feeds.adapter = mNewsAdapter
        list_news_feeds.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list_news_feeds.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        label_empty_list.visibility = View.GONE
        progress_load_news.visibility = View.GONE

        if (hasInternetConnection()) {
            label_empty_list.visibility = View.GONE
            label_empty_list.text = ""
            loaderManager.initLoader(LOADER_NEWS_ARTICLES, null, this)
        } else {
            label_empty_list.text = getString(R.string.no_internet)
            label_empty_list.visibility = View.VISIBLE
        }

    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork:NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
            return true
        }

        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_settings) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClickReadMore(articleURL: String) {
        if (articleURL.startsWith("http://") || articleURL.startsWith("https://")) {
            try {
                val uri = Uri.parse(articleURL)
                val readMoreIntent = Intent(Intent.ACTION_VIEW, uri)
                if (readMoreIntent.resolveActivity(packageManager) != null) {
                    startActivity(readMoreIntent)
                    return
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }
        }

        Toast.makeText(this, "Unable to open article", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateLoader(id: Int, p1: Bundle?): Loader<MutableList<NewsArticle>>? {
        return when (id) {
            LOADER_NEWS_ARTICLES -> {
                progress_load_news.visibility = View.VISIBLE
                return NewsArticleAsyncLoader(this, BASE_URL)
            }
            else -> null
        }
    }

    override fun onLoadFinished(loader: Loader<MutableList<NewsArticle>>, data: MutableList<NewsArticle>?) {
        when (loader.id) {
            LOADER_NEWS_ARTICLES -> {
                progress_load_news.visibility = View.GONE
                mNewsAdapter.clear()

                if (data != null && data.isNotEmpty()) {
                    mNewsAdapter.addAll(data)
                    label_empty_list.visibility = View.GONE
                } else {
                    label_empty_list.text = getString(R.string.no_news)
                    label_empty_list.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onLoaderReset(loader: Loader<MutableList<NewsArticle>>) {
        mNewsAdapter.clear()
    }
}
