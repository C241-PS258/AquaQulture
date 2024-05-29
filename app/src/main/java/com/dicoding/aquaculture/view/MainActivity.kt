package com.dicoding.aquaculture.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.aquaculture.R
import com.dicoding.aquaculture.databinding.ActivityMainBinding
import com.dicoding.aquaculture.databinding.ActivityWelcomeBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the welcome screen has been shown
        val sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val hasShownWelcome = sharedPref.getBoolean("hasShownWelcome", false)

        if (!hasShownWelcome) {
            // If the welcome screen hasn't been shown, start WelcomeActivity
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // Close MainActivity
        } else {
            // If the welcome screen has been shown, proceed with setting up MainActivity
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }
    }

}