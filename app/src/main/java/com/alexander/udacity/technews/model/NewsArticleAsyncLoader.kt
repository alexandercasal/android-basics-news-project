package com.alexander.udacity.technews.model

import android.content.AsyncTaskLoader
import android.content.Context

/**
 * Loads news articles from the News API in a background thread
 */
class NewsArticleAsyncLoader(context: Context, val url: String) : AsyncTaskLoader<MutableList<NewsArticle>>(context) {

    override fun onStartLoading() {
        forceLoad()
    }

    override fun loadInBackground(): MutableList<NewsArticle> {
        return ArrayList<NewsArticle>()
    }

    override fun onStopLoading() {
        cancelLoad()
    }
}