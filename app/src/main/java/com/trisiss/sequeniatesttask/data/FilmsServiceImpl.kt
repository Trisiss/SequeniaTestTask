package com.trisiss.sequeniatesttask.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by trisiss on 8/8/2021.
 */
class FilmsServiceImpl : FilmsService {
    val SERVER = "https://s3-eu-west-1.amazonaws.com/"
    lateinit var retrofit: Retrofit
    lateinit var filmsApi: FilmsApi

    init {
        retrofit = initRetrofit()
        filmsApi = retrofit.create(FilmsApi::class.java)
    }

    private fun initRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            networkInterceptors().add(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            })
            addInterceptor(interceptor)
        }
        return Retrofit.Builder().baseUrl(SERVER)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client.build())
            .build()

    }

    override suspend fun load() = filmsApi.getListFilms()
}