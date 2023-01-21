package com.polware.newsapi2compose.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {

    private const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "bc4caee230db45218cee7be3d1ca098b"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val logging = HttpLoggingInterceptor()
    // Client with Http Header
    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor {
                chain ->
                val builder = chain.request().newBuilder()
                builder.header("X-Api-Key", API_KEY)
                return@Interceptor chain.proceed(builder.build())
            }
        )
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)
    }.build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    val retrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }

}