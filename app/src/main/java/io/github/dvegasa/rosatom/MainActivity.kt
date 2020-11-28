package io.github.dvegasa.rosatom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = intent.getStringExtra("username");

        if (username == "dvegasa") {
            // Показать экран рабочего
        } else {
            // Показать экран начальника
        }
    }
}