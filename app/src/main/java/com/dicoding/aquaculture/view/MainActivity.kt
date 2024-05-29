package com.dicoding.aquaculture.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.aquaculture.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showWelcomeActivity()
    }

    private fun showWelcomeActivity() {
        val sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val hasShownWelcome = sharedPref.getBoolean("hasShownWelcome", false)

        if (!hasShownWelcome) {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        } else {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }
    }

}