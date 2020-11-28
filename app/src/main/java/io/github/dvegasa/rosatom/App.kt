package io.github.dvegasa.rosatom

import android.app.Application
import android.util.Log
import cafe.adriel.androidaudioconverter.AndroidAudioConverter
import cafe.adriel.androidaudioconverter.callback.ILoadCallback


/**
 * Created by Ed Khalturin @DVegasa
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidAudioConverter.load(this, object : ILoadCallback {
            override fun onSuccess() {
                Log.d("ed__", "FFMPEG installed")
            }

            override fun onFailure(error: Exception) {
                Log.e("ed__", "FFMPEG not supported")
            }
        })
    }
}