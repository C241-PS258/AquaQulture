package com.dicoding.aquaculture.view.scan

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.aquaculture.data.response.HistoryResponse
import com.dicoding.aquaculture.data.utils.loadBitmapFromUrl
import com.dicoding.aquaculture.databinding.ActivityHistoryDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val history = intent.getParcelableExtra<HistoryResponse>(EXTRA_HISTORY)
        history?.let { displayHistoryDetails(it) }
    }

    private fun displayHistoryDetails(history: HistoryResponse) {
        binding.jenisIkan.text = history.nameFish
        Log.d("HistoryDetailsActivity", "Jenis Ikan: ${history.nameFish}")

        history.fish?.let { fish ->
            fish.pakan?.let {
                binding.pakan.setItems(it.split(",").map { point -> point.trim() })
            }

            fish.pemeliharaan?.let {
                binding.pemeliharaan.setItems(it.split(". ").map { point -> point.trim() })
            }
        }

        lifecycleScope.launch {
            val bitmap = withContext(Dispatchers.IO) {
                loadBitmapFromUrl(history.image)
            }
            bitmap?.let {
                binding.resultImage.setImageBitmap(it)
            }
        }
    }

    companion object {
        const val EXTRA_HISTORY = "extra_history"
    }
}
