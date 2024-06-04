package com.dicoding.aquaculture.view.scan

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.aquaculture.databinding.ActivityScanDetailsBinding

class ScanDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScanDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
        }

//        val detectedText = intent.getStringExtra(EXTRA_RESULT)
//        binding.resultText.text = detectedText

    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }
}