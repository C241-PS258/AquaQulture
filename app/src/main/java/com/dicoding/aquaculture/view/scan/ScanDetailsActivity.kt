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

    override fun onBackPressed() {
        setResult(RESULT_OK)
        super.onBackPressed()
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_JENIS_IKAN = "extra_jenis_ikan"
        const val EXTRA_PAKAN = "extra_pakan"
        const val EXTRA_PEMELIHARAAN = "extra_pemeliharaan"
    }
}