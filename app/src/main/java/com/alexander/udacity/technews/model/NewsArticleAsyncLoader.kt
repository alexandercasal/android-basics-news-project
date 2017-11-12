package com.alexander.udacity.technews.model

import android.content.AsyncTaskLoader
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import javax.net.ssl.HttpsURLConnection

/**
 * Loads news articles from the News API in a background thread
 */
class NewsArticleAsyncLoader(context: Context, val url: String) : AsyncTaskLoader<MutableList<NewsArticle>>(context) {

    private val TAG = NewsArticleAsyncLoader::class.java.simpleName

    override fun onStartLoading() {
        forceLoad()
    }

    override fun loadInBackground(): MutableList<NewsArticle> {
        var articles: MutableList<NewsArticle>
        val articlesJson = NetworkUtils.MakeHttpsRequest("$url?source=ars-technica&sortBy=top&apiKey=$API_KEY")

        if (articlesJson != null) {
            articles = extractArticlesFromJSON(articlesJson)
            return articles
        }

        return ArrayList<NewsArticle>()
    }

    override fun onStopLoading() {
        cancelLoad()
    }

    private fun extractArticlesFromJSON(json: JSONObject): MutableList<NewsArticle> {
        val articles = ArrayList<NewsArticle>()
        try {
            val articlesJSONArray = json.getJSONArray("articles")
            for (i in 0 until articlesJSONArray.length()) {
                val articleJSONObject = articlesJSONArray.getJSONObject(i)

                val author: String?
                if (!articleJSONObject.isNull("author")) {
                    author = articleJSONObject.getString("author")
                } else {
                    author = null
                }

                val title = articleJSONObject.getString("title")
                val description = articleJSONObject.getString("description")
                val articleURL = articleJSONObject.getString("url")
                val articleImageURL = articleJSONObject.getString("urlToImage")
                val publishedAt = articleJSONObject.getString("publishedAt")

                articles.add(NewsArticle(
                        title,
                        description,
                        author,
                        parseDate(publishedAt),
                        articleURL,
                        downloadBitmap(articleImageURL)
                ))
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
        }

        return articles
    }

    private fun downloadBitmap(url: String): Bitmap? {
        val url = URL(url)
        val connection = url.openConnection() as HttpsURLConnection
        connection.doInput = true
        connection.readTimeout = 10000
        connection.connectTimeout = 10000
        connection.connect()

        if (connection.responseCode == 200) {
            val inputStream = connection.inputStream
            return BitmapFactory.decodeStream(inputStream)
        }

        return null
    }

    private fun parseDate(date: String): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
        return formatter.parse(date)
    }
}