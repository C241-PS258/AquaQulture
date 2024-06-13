package com.dicoding.aquaculture.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dicoding.aquaculture.R
import com.dicoding.aquaculture.databinding.ActivityMainBinding
import com.dicoding.aquaculture.view.ViewModelFactory
import com.dicoding.aquaculture.view.register.RegisterActivity
import com.dicoding.aquaculture.view.welcome.WelcomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showWelcomeActivity()
        sessionObserver()
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
            val navView: BottomNavigationView = binding.navView

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
            val navController = navHostFragment.navController

            navView.setupWithNavController(navController)
        }
    }

    private fun sessionObserver() {
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, RegisterActivity::class.java))
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                finish()
            }
        }
    }

    fun refreshHomeFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
        if (fragment is HomeFragment) {
            fragment.refreshData()
        }
    }
}