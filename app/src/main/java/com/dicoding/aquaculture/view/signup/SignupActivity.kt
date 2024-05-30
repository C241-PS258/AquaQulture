package com.dicoding.aquaculture.view.signup

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.aquaculture.R
import com.dicoding.aquaculture.databinding.ActivitySignupBinding
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.dicoding.aquaculture.view.main.MainActivity


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
//    private val viewModel by viewModels<SignupViewModel> {
//        ViewModelFactory.getInstance(this)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupView()
        setupAction()
        playAnimation()


        setMyButtonEnable()
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, after: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        }

        binding.apply {
            nameEditText.addTextChangedListener(textWatcher)
            emailEditText.addTextChangedListener(textWatcher)
            passwordEditText.addTextChangedListener(textWatcher)
        }

//        viewModel.isLoading.observe(this) { isLoading ->
//            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//        }
//
//        viewModel.registerResult.observe(this, Observer { registerResponse ->
//            handleRegisterResult(registerResponse)
//        })
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

    private fun setMyButtonEnable() {
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        binding.signupBtn.isEnabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
    }

    private fun setupAction() {
        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
//            val name = binding.nameEditText.text.toString()
//            val email = binding.emailEditText.text.toString()
//            val password = binding.passwordEditText.text.toString()

//            Log.d("TAG", "Tes1$name ")
//            viewModel.register(name, email, password)
        }
        binding.iconBack.setOnClickListener {
            onBackPressed()
        }
    }

//    private fun handleRegisterResult(registerResponse: RegisterResponse) {
//        registerResponse?.let {
//            if (it.message == "User created") {
//                showAlertDialog("Success", "Your account has been created. Next, please login!", "OK") {
//                    finish()
//                }
//            } else {
//                Log.d("TAG", "TesError ")
//                showAlertDialog("Error", it.message?:"Your account has not been created. Please try again!","OK", null)
//            }
//        }
//    }
//
//    private fun showAlertDialog(title: String, message: String, buttonText: String, action: (() -> Unit )?) {
//        val builder = AlertDialog.Builder(this)
//            .setTitle(title)
//            .setMessage(message)
//            .setPositiveButton(buttonText) { dialog, _ ->
//                action?.invoke()
//                dialog.dismiss()
//            }
//        if (action == null) {
//            builder.setCancelable(true)
//        }
//        builder.show()
//    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageSignup, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 7000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val tvTitle = ObjectAnimator.ofFloat(binding.titleTextSignup, View.ALPHA, 1f).setDuration(200)
        val tvName = ObjectAnimator.ofFloat(binding.nameTextSignup, View.ALPHA, 1f).setDuration(200)
        val etName = ObjectAnimator.ofFloat(binding.nameEditTextSignup, View.ALPHA, 1f).setDuration(200)
        val tvEmail = ObjectAnimator.ofFloat(binding.emailTextSignup, View.ALPHA, 1f).setDuration(200)
        val etEmail = ObjectAnimator.ofFloat(binding.emailEditTextSignup, View.ALPHA, 1f).setDuration(200)
        val tvPassword = ObjectAnimator.ofFloat(binding.passwordTextSignup, View.ALPHA, 1f).setDuration(200)
        val etPassword = ObjectAnimator.ofFloat(binding.passwordEditTextSignup, View.ALPHA, 1f).setDuration(200)
        val signup = ObjectAnimator.ofFloat(binding.signupBtn, View.ALPHA, 1f).setDuration(200)

        AnimatorSet().apply {
            playSequentially(
                tvTitle,
                tvName,
                etName,
                tvEmail,
                etEmail,
                tvPassword,
                etPassword,
                signup
            )
            start()
        }

    }

}