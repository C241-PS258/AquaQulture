package com.dicoding.aquaculture.view.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.aquaculture.R
import com.dicoding.aquaculture.data.response.HistoryResponse
import com.dicoding.aquaculture.data.utils.loadBitmapFromUrl
import com.dicoding.aquaculture.databinding.ItemHistoryBinding
import com.dicoding.aquaculture.view.scan.HistoryDetailsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class HistoryAdapter(private val coroutineScope: CoroutineScope) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val items = mutableListOf<HistoryResponse>()

    fun setItems(newItems: List<HistoryResponse>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryBinding.bind(itemView)

        fun bind(history: HistoryResponse) {
            binding.nameFish.text = history.nameFish

            val formattedDate = formatDate(history.timestamp)
            binding.timestamp.text = formattedDate

            binding.harvestPredictions.text = itemView.context.getString(R.string.prediksi_panen, history.harvestPredictions)

            coroutineScope.launch {
                val bitmap = withContext(Dispatchers.IO) {
                    loadBitmapFromUrl(history.image)
                }
                bitmap?.let {
                    binding.imageHistory.setImageBitmap(it)
                }
            }

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, HistoryDetailsActivity::class.java).apply {
                    putExtra(HistoryDetailsActivity.EXTRA_HISTORY, history)
                }
                context.startActivity(intent)
            }
        }

        private fun formatDate(timestamp: String?): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            outputFormat.timeZone = TimeZone.getTimeZone("GMT+7")

            return try {
                val date: Date = inputFormat.parse(timestamp) ?: Date()
                outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                "Unknown Date"
            }
        }
    }
}
