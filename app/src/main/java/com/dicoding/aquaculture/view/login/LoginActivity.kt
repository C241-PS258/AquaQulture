package com.dicoding.aquaculture.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dicoding.aquaculture.data.response.LoginResponse
import com.dicoding.aquaculture.databinding.ActivityLoginBinding
import com.dicoding.aquaculture.view.ViewModelFactory
import com.dicoding.aquaculture.view.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        supportActionBar?.hide()
        setupView()
        setButtonEnable()
        setupAction()
        playAnimation()

        setButtonEnable()
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        binding.emailEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.loginResult.observe(this, Observer { loginResponse ->
            loginResultHandler(loginResponse)
        })
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setButtonEnable() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        binding.loginBtn.isEnabled = email.isNotEmpty() && password.isNotEmpty()
    }

    private fun setupAction() {
        binding.loginBtn.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.login(email, password)
        }
        binding.iconBack.setOnClickListener {
            onBackPressedDispatcher
        }
    }

    private fun loginResultHandler(loginResponse: LoginResponse?) {
        loginResponse?.let {
            if (it.message == "success"){
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                showAlertDialog("Error", it.message ?: "Login Failed","OK",null)
            }
        }
    }

    private fun showAlertDialog(title: String, message: String, buttonText: String = "OK", action: (() -> Unit)?) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonText) { dialog, _ ->
                action?.invoke()
                dialog.dismiss()
            }
        if (action == null) {
            builder.setCancelable(true)
        }

        builder.show()
    }
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 7000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val tvTitle = ObjectAnimator.ofFloat(binding.titleTextLogin, View.ALPHA, 1f).setDuration(200)
        val tvMessage = ObjectAnimator.ofFloat(binding.messageTextLogin, View.ALPHA, 1f).setDuration(200)
        val tvEmail = ObjectAnimator.ofFloat(binding.emailTextLogin, View.ALPHA, 1f).setDuration(200)
        val etEmail = ObjectAnimator.ofFloat(binding.emailEditTextLogin, View.ALPHA, 1f).setDuration(200)
        val tvPassword = ObjectAnimator.ofFloat(binding.passwordTextLogin, View.ALPHA, 1f).setDuration(200)
        val etPassword = ObjectAnimator.ofFloat(binding.passwordEditTextLogin, View.ALPHA, 1f).setDuration(200)
        val login = ObjectAnimator.ofFloat(binding.loginBtn, View.ALPHA, 1f).setDuration(200)

        AnimatorSet().apply {
            playSequentially(tvTitle, tvMessage, tvEmail, etEmail, tvPassword, etPassword, login)
            startDelay = 100
            start()
        }
    }
}