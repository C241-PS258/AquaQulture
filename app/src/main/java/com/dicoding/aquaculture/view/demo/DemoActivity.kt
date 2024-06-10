package com.dicoding.aquaculture.view.demo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.dicoding.aquaculture.data.utils.getImageUri
import com.dicoding.aquaculture.data.utils.uriToFile
import com.dicoding.aquaculture.databinding.ActivityDemoBinding
import com.dicoding.aquaculture.view.ViewModelFactory
import com.dicoding.aquaculture.view.scan.ScanDetailsActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DemoActivity : AppCompatActivity() {
    private lateinit var viewModel: DemoViewModel
    private lateinit var binding: ActivityDemoBinding

    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(DemoViewModel::class.java)

        viewModel.resetPredictResult()
        viewModel.resetErrorMessage()

        // If there is a saved instance, restore the image URI
        savedInstanceState?.let {
            currentImageUri = it.getParcelable(KEY_IMAGE_URI)
            currentImageUri?.let { showImage() }
        }

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.uploadButton.setOnClickListener { uploadImage() }

        viewModel.isLoading.observe(this, Observer { isLoading ->
            showLoading(isLoading)
        })
        viewModel.predictResult.observe(this, Observer { result ->
            result?.let {
                val intent = Intent(this, ScanDetailsActivity::class.java)
                intent.putExtra(ScanDetailsActivity.EXTRA_IMAGE_URI, currentImageUri.toString())
                intent.putExtra(ScanDetailsActivity.EXTRA_JENIS_IKAN, result.jenisIkan)
                intent.putExtra(ScanDetailsActivity.EXTRA_PAKAN, result.pakan)
                intent.putExtra(ScanDetailsActivity.EXTRA_PEMELIHARAAN, result.pemeliharaan)
                startActivity(intent)
            }
        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                showToast(errorMessage)
            }
        })
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this) // Get File from Uri
            val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageFile)
            val multipartBody = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

            viewModel.predictFish(multipartBody)
        } ?: showToast("Please select an image first")
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_IMAGE_URI, currentImageUri)
    }
}
