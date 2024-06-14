package com.dicoding.aquaculture.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aquaculture.R
import com.dicoding.aquaculture.databinding.FragmentHomeBinding
import com.dicoding.aquaculture.view.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val fragmentScope = CoroutineScope(Dispatchers.Main)

    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.historyData.observe(viewLifecycleOwner, Observer { historyList ->
            adapter.setItems(historyList)
            binding.rvHistory.visibility = if (historyList.isEmpty()) View.GONE else View.VISIBLE
            binding.emptyState.visibility = if (historyList.isEmpty()) View.VISIBLE else View.GONE
            binding.cardHistory.visibility = if (historyList.isEmpty()) View.GONE else View.VISIBLE
            adapter.notifyDataSetChanged()
            binding.apply {
                cardHistory.animate().alpha(1f).setDuration(250).start()
                rvHistory.animate().alpha(1f).setDuration(250).start()
            }
        })

        viewModel.getUserName().observe(viewLifecycleOwner, Observer { userName ->
            binding.nameUser.text = getString(R.string.name_user) + " " + userName
            binding.nameUser.animate().alpha(1f).setDuration(250).start()
        })

        viewModel.historyMessage.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter(fragmentScope)
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchHistoryData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun refreshData() {
        viewModel.fetchHistoryData()
    }
}
