package com.example.deardairy.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

data class EmotionResponse(
    val emotion: String,
    val recommendation: String
)

class ApiClient {
    private val client = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val adapter = moshi.adapter(EmotionResponse::class.java)

    suspend fun postEmotion(note: String): EmotionResponse? {
        val mediaType = "application/json".toMediaTypeOrNull()
        val jsonObject = JSONObject().apply {
            put("note", note)
        }
        val requestBody = jsonObject.toString().toRequestBody(mediaType)
        Log.d("ApiClient", "Request Body: ${jsonObject.toString()}")


        val request = Request.Builder()
            .url("https://dear-diary-capstone.onrender.com/emotion")
            .post(requestBody)
            .build()

        return suspendCancellableCoroutine { continuation ->
            Log.d("ApiClient", "Before executing request. Request URL: ${request.url}")

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        continuation.resume(null)
                        Log.e("ApiClient", "Unsuccessful response. Code: ${response.code}, Message: ${response.message}")
                        return
                    }

                    val responseBody = response.body?.string()
                    Log.d("ApiClient", "Response Body: $responseBody")

                    responseBody?.let {
                        val emotionResponse = adapter.fromJson(it)
                        continuation.resume(emotionResponse)
                    } ?: run {
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                    Log.e("ApiClient", "Request failed. Error: ${e.message}")
                }
            })

            continuation.invokeOnCancellation {
                Log.d("ApiClient", "Request cancelled")
                client.dispatcher.cancelAll()
            }
        }
    }
}