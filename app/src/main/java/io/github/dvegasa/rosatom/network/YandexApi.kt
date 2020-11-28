package io.github.dvegasa.rosatom.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Ed Khalturin @DVegasa
 */

interface YandexApi {
    companion object {
        fun create(): YandexApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(YandexApi::class.java)
        }
    }

}