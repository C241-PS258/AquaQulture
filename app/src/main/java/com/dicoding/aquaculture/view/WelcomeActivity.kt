package com.dicoding.aquaculture.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.aquaculture.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val hasShownWelcome = sharedPref.getBoolean("hasShownWelcome", false)

        if (hasShownWelcome) {
            startMainActivity()
        } else {
            binding = ActivityWelcomeBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.button.setOnClickListener {
                with(sharedPref.edit()) {
                    putBoolean("hasShownWelcome", true)
                    apply()
                }
                startMainActivity()
            }
            setupView()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}