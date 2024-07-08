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

data class NoteCoverResponse(
    val imageUrl: String,
    val imageId: String
)

data class NoteTitleResponse(
    val title: String
)

class ApiClient {
    private val client = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val adapter = moshi.adapter(EmotionResponse::class.java)
    private val coverAdapter = moshi.adapter(NoteCoverResponse::class.java)
    private val titleAdapter = moshi.adapter(NoteTitleResponse::class.java)


    suspend fun saveNoteCover(requestBody: String): NoteCoverResponse? {
        val mediaType = "application/json".toMediaTypeOrNull()
        val jsonObject = if (requestBody.isNotBlank()) {
            JSONObject().apply {
                Log.d("ApiClient", "here")
                put("image_id", requestBody.trim())
            }
        } else {
            JSONObject()
        }

        val requestBody = jsonObject.toString().toRequestBody(mediaType)
        Log.d("ApiClient", "Request Body: ${jsonObject.toString()}")

        val request = Request.Builder()
            .url("https://dear-diary-capstone.onrender.com/noteCover")
            .put(requestBody)
            .build()

        return suspendCancellableCoroutine { continuation ->
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
                        val noteCoverResponse = coverAdapter.fromJson(it)
                        continuation.resume(noteCoverResponse)
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

    suspend fun getNoteTitle(note: String): NoteTitleResponse? {
        val mediaType = "application/json".toMediaTypeOrNull()
        val jsonObject = JSONObject().apply {
            put("note", note)
        }
        val requestBody = jsonObject.toString().toRequestBody(mediaType)
        Log.d("ApiClient", "Request Body: ${jsonObject.toString()}")

        val request = Request.Builder()
            .url("https://dear-diary-capstone.onrender.com/noteTitle")
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
                        val noteTitleResponse = titleAdapter.fromJson(it)
                        continuation.resume(noteTitleResponse)
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