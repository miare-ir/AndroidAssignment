package ir.miare.androidcodechallenge.core.network.util

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {

            val jsonString = context.assets.open("data.json").bufferedReader().use { it.readText() }

            return Response.Builder()
                .code(200)
                .message("OK")
                .request(chain.request())
                .protocol(okhttp3.Protocol.HTTP_1_1)
                .body(jsonString.toResponseBody("application/json".toMediaType()))
                .addHeader("content-type", "application/json")
                .build()
        } catch (e: IOException) {

            return Response.Builder()
                .code(500)
                .message("Error reading mock file")
                .request(chain.request())
                .protocol(okhttp3.Protocol.HTTP_1_1)
                .body("{\"error\": \"Failed to load mock data\"}".toResponseBody("application/json".toMediaType()))
                .addHeader("content-type", "application/json")
                .build()
        }
    }
}