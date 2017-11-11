package com.alexander.udacity.technews.model

import android.graphics.Bitmap
import java.util.Date

/**
 * Container for a single news article item
 */
class NewsArticle(val articleTitle: String, val articleDescription: String, val articleAuthor: String,
                  val publishDate: Date, val articleURL: String, val articleImage: Bitmap)