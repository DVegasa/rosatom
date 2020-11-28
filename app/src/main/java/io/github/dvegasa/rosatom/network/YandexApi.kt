package io.github.dvegasa.rosatom.network

import io.github.dvegasa.rosatom.Tokens
import io.github.dvegasa.rosatom.features.main.boss.YandexStt
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

/**
 * Created by Ed Khalturin @DVegasa
 */

interface YandexApi {
    companion object {
        fun create(): YandexApi {

            val client = OkHttpClient.Builder()

            client.connectTimeout(20, TimeUnit.SECONDS)

            client.addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })

            client.addInterceptor { chain ->
                val orig = chain.request()
                val req = orig.newBuilder()
                    .header("Authorization", "Bearer ${Tokens.IAM}")
                    .build()
                chain.proceed(req)
            }

            val retrofit = Retrofit.Builder()
                .baseUrl("https://stt.api.cloud.yandex.net/speech/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
            return retrofit.create(YandexApi::class.java)
        }
    }

    @Multipart
    @POST("./stt:recognize")
    fun stt(
        @Part file: MultipartBody.Part,
        @Query("format") format: String = "oggopus",
        @Query("sampleRateHertz") hz: Int = 48000,
        @Query("folderId") folderId: String = Tokens.catalog
    ): Call<YandexStt>
}