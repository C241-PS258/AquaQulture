package com.dicoding.aquaculture.view.demo

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.aquaculture.databinding.ActivityScanDetailsDemoBinding
import com.dicoding.aquaculture.view.register.RegisterActivity

class ScanDetailsDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanDetailsDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScanDetailsDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
        }

        val jenisIkan = intent.getStringExtra(EXTRA_JENIS_IKAN)
        val pakan = intent.getStringExtra(EXTRA_PAKAN)
        val pemeliharaan = intent.getStringExtra(EXTRA_PEMELIHARAAN)

        binding.jenisIkan.text = jenisIkan
        pakan?.let {
            binding.pakan.setItems(it.split(",").map { point -> point.trim() })
        }
        pemeliharaan?.let {
            binding.pemeliharaan.setItems(it.split(". ").map { point -> point.trim() })
        }
    }

    @Deprecated("We don't want it return to scan activity")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        showAlertDialog(
            title = "Ayo!",
            message = "Tunggu apa lagi, yuk daftar sekarang!",
            buttonText = "OK"
        ) {
            val sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putBoolean(HIDE_DEMO_BUTTON, true)
                apply()
            }
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
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

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_JENIS_IKAN = "extra_jenis_ikan"
        const val EXTRA_PAKAN = "extra_pakan"
        const val EXTRA_PEMELIHARAAN = "extra_pemeliharaan"
        const val HIDE_DEMO_BUTTON = "hide_demo_button"
        const val PREFERENCES_NAME = "com.dicoding.aquaculture.PREFERENCES"
    }
}
