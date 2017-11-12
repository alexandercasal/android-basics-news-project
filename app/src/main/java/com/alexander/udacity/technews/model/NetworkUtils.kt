package com.alexander.udacity.technews.model

import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 *
 */
object NetworkUtils {

    private val TAG = NetworkUtils::class.java.simpleName

    /**
     * Initiate an HTTPS GET request. If the request was successful and provided a JSON response,
     * the JSONObject will be provided, otherwise null will be returned
     */
    fun MakeHttpsRequest(url: String): JSONObject? {
        val url = parseHttpsURL(url)
        val connection: HttpsURLConnection

        if (url != null) {
            try {
                connection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.connect()

                if (connection.responseCode == 200) {
                    val response = readInputStream(connection.inputStream)
                    return JSONObject(response)
            }
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }
        }

        return null
    }

    /**
     * Attempts to parse the url string into a URL. If the url string doesn't specify the
     * https protocol or is malformed, null will be returned.
     */
    private fun parseHttpsURL(url: String): URL? {
        if (url.startsWith("https://")) {
            try {
                return URL(url)
            } catch (e: MalformedURLException) {
                Log.e(TAG, e.message, e)
            }
        }

        return null
    }

    private fun readInputStream(inputStream: InputStream): String {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()

        var line = reader.readLine()
        while (line != null) {
            stringBuilder.append(line)
            line = reader.readLine()
        }

        return stringBuilder.toString()
    }
}