package io.github.dvegasa.rosatom.features.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.github.dvegasa.rosatom.features.main.MainActivity
import io.github.dvegasa.rosatom.R
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        btnLogin.setOnClickListener {
            val username = etLogin.text.toString()
            when {
                username.toLowerCase() == "dvegasa" -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("username", "dvegasa")
                    startActivity(intent)
                }
                username.toLowerCase() == "nikita" -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("username", "nikita")
                    startActivity(intent)
                }
                else -> Toast.makeText(this, "Данные не заполнены", Toast.LENGTH_SHORT).show()
            }
        }
    }
}