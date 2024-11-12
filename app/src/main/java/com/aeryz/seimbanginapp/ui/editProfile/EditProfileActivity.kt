package com.aeryz.seimbanginapp.ui.editProfile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.data.network.model.profile.ProfileData
import com.aeryz.seimbanginapp.databinding.ActivityEditProfileBinding
import com.aeryz.seimbanginapp.utils.exception.ApiException
import com.aeryz.seimbanginapp.utils.proceedWhen
import com.aeryz.seimbanginapp.utils.uriToFile
import com.shashank.sony.fancytoastlib.FancyToast
import com.yalantis.ucrop.UCrop
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private val viewModel: EditProfileViewModel by viewModel()

    private var isEditMode = false

    private var editMenuItem: MenuItem? = null

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            startCrop(uri)
        } else {
            Log.d("Photo picker", "No media selected")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar()
        updateEditMode()
        getProfileData()
        observeProfileData()
        observeUpdateProfileResult()
        setOnClickListener()
        showImage()
        observeUploadProfileImageResult()
    }

    private fun setupToolBar() {
        val toolbar = binding.toolBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.financial_profile_menu, menu)
        editMenuItem = menu?.findItem(R.id.action_edit)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                isEditMode = !isEditMode
                updateEditMode()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateEditMode() {
        if (isEditMode) {
            editMenuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_edit_off_24)
            binding.etFullName.isEnabled = true
            binding.ivEditPhoto.isVisible = true
            binding.tvUploadPhoto.isVisible = true
            binding.btnUpdate.isEnabled = true
        } else {
            editMenuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_edit_on_24)
            binding.etFullName.isEnabled = false
            binding.ivEditPhoto.isVisible = false
            binding.tvUploadPhoto.isVisible = false
            binding.btnUpdate.isEnabled = false
        }
    }

    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCrop(uri: Uri) {
        val destinationUri =
            Uri.fromFile(File(cacheDir, "cropped_image_${System.currentTimeMillis()}.jpg"))
        val uCrop = UCrop.of(uri, destinationUri)
        uCrop.withAspectRatio(1f, 1f)
        uCrop.withMaxResultSize(800, 800)
        uCrop.start(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            val resultUri = UCrop.getOutput(data!!)
            resultUri?.let {
                viewModel.currentImageUri = it
                showImage()
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            Toast.makeText(this, "Crop Error: ${cropError?.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getProfileData() {
        viewModel.getProfileData()
    }

    private fun observeProfileData() {
        viewModel.profileData.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.contentState.root.isVisible = false
                    binding.llForm.isVisible = true
                    it.payload?.let { response ->
                        setupForm(response.profileData)
                    }
                },
                doOnLoading = {
                    binding.contentState.root.isVisible = true
                    binding.contentState.pbLoading.isVisible = true
                    binding.contentState.tvError.isVisible = false
                    binding.llForm.isVisible = false
                },
                doOnError = {
                    binding.contentState.root.isVisible = true
                    binding.contentState.pbLoading.isVisible = false
                    binding.contentState.tvError.isVisible = true
                    binding.llForm.isVisible = false
                    if (it.exception is ApiException) {
                        binding.contentState.tvError.text = it.exception.getParsedError()?.message
                    }
                }
            )
        }
    }

    private fun showImage() {
        viewModel.currentImageUri.let {
            binding.profileImage.load(it) {
                crossfade(true)
                placeholder(R.drawable.profile_image)
                error(R.drawable.profile_image)
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun setupForm(profileData: ProfileData?) {
        profileData?.let {
            viewModel.currentImageUri = profileData.profilePicture?.toUri()
            showImage()
            binding.etFullName.setText(profileData.fullName)
        }
    }

    private fun observeUpdateProfileResult() {
        viewModel.editProfileResult.observe(this) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnUpdate.isVisible = true
                    FancyToast.makeText(
                        this,
                        getString(R.string.text_update_profile_success),
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                    getProfileData()
                    isEditMode = false
                    updateEditMode()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnUpdate.isVisible = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnUpdate.isVisible = true
                    if (it.exception is ApiException) {
                        FancyToast.makeText(
                            this,
                            it.exception.getParsedError()?.message,
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }
                }
            )
        }
    }

    private fun observeUploadProfileImageResult() {
        viewModel.uploadProfileImageResult.observe(this) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnSuccess = {
                    binding.pbUploadLoading.isVisible = false
                    binding.tvUploadPhoto.isVisible = true
                    FancyToast.makeText(
                        this,
                        getString(R.string.text_update_profile_image_success),
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                    getProfileData()
                    isEditMode = false
                    updateEditMode()
                },
                doOnLoading = {
                    binding.pbUploadLoading.isVisible = true
                    binding.tvUploadPhoto.isVisible = false
                },
                doOnError = {
                    binding.pbUploadLoading.isVisible = false
                    binding.tvUploadPhoto.isVisible = true
                    if (it.exception is ApiException) {
                        FancyToast.makeText(
                            this,
                            it.exception.getParsedError()?.message,
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }
                }
            )
        }
    }

    private fun setOnClickListener() {
        binding.btnUpdate.setOnClickListener {
            updateProfile()
        }
        binding.ivEditPhoto.setOnClickListener {
            startGallery()
        }
        binding.tvUploadPhoto.setOnClickListener {
            uploadProfileImage()
        }
    }

    private fun uploadProfileImage() {
        viewModel.currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this)
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multiPartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
            viewModel.uploadProfileImage(multiPartBody)
        }
    }

    private fun updateProfile() {
        val fullName = binding.etFullName.text.toString().trim()
        if (checkFullNameValidation(fullName)) {
            viewModel.editProfile(fullName)
        }
    }

    private fun checkFullNameValidation(fullName: String): Boolean {
        return if (fullName.isEmpty()) {
            binding.tilFullName.isErrorEnabled = true
            binding.tilFullName.error = getString(R.string.text_hint_full_name_empty)
            false
        } else {
            binding.tilFullName.isErrorEnabled = false
            true
        }
    }
}