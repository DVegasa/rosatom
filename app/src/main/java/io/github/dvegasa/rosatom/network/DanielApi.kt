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

interface DanielApi {
    companion object {
        fun create(): DanielApi {

            val client = OkHttpClient.Builder()

            client.addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })

            val retrofit = Retrofit.Builder()
                .baseUrl("http://172.27.146.210/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
            return retrofit.create(DanielApi::class.java)
        }
    }

    @POST("task")
    fun uploadTask(@Body task: Task): Call<ResponseBody>

    @GET("task")
    fun getTasks(): Call<ArrayList<Task>>

}