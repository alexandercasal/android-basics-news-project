package com.alexander.udacity.technews.model

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.alexander.udacity.technews.R
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Adapter to display news articles
 */
class NewsRecyclerAdapter(context: Context, val articles: MutableList<NewsArticle>,
                          val readMoreClickListener: OnReadMoreClickListener)
    : RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

    interface OnReadMoreClickListener {
        fun onClickReadMore(articleURL: String)
    }

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val articleView = mLayoutInflater.inflate(R.layout.list_item_news, parent, false)
        return NewsViewHolder(articleView, readMoreClickListener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindArticle(articles[position])
    }

    /**
     * Removes all articles from the adapter
     */
    fun clear() {
        articles.clear()
        notifyDataSetChanged()
    }

    /**
     * Appends the provided list of articles to the current list of articles
     */
    fun addAll(articles: MutableList<NewsArticle>) {
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    class NewsViewHolder(itemView: View, val readMoreClickListener: OnReadMoreClickListener)
        : RecyclerView.ViewHolder(itemView) {
        val articleImage = itemView.findViewById<ImageView>(R.id.image_article)
        val articleAuthor = itemView.findViewById<TextView>(R.id.label_author)
        val articlePublishDate = itemView.findViewById<TextView>(R.id.label_publish_time)
        val articleTitle = itemView.findViewById<TextView>(R.id.label_article_title)
        val articleDescription = itemView.findViewById<TextView>(R.id.label_article_description)
        val buttonReadMore = itemView.findViewById<Button>(R.id.button_read_more)

        fun bindArticle(article: NewsArticle) {
            if (article.articleImage != null) {
                articleImage.setImageBitmap(article.articleImage)
            }
            articleAuthor.text = article.articleAuthor?.let {
                "$it - "
            } ?: ""
            articlePublishDate.text = formatTimestamp(article.publishDate)
            articleTitle.text = article.articleTitle
            articleDescription.text = article.articleDescription
            buttonReadMore.setOnClickListener { view ->
                readMoreClickListener.onClickReadMore(article.articleURL)
            }
        }

        private fun formatTimestamp(date: Date): String {
            val formatter = SimpleDateFormat("MM/dd/yyyy, h:mm a")
            return formatter.format(date)
        }
    }
}