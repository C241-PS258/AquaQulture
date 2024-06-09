package com.dicoding.aquaculture.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dicoding.aquaculture.R
import com.dicoding.aquaculture.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root


        binding.tvNama.text = getString(R.string.name_user)
        binding.tvEmail.text = getString(R.string.email)

        binding.btnLogout.setOnClickListener{
            onLogoutClicked()
        }


    }

    private fun onLogoutClicked() {
        Toast.makeText(context, "Logout clicked", Toast.LENGTH_SHORT).show()
        // Navigate to LogoutFragment
        findNavController().navigate(R.id.btn_logout)
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}