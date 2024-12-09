package com.aeryz.seimbanginapp.ui.ocr

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.network.model.ocr.OcrResponse
import com.aeryz.seimbanginapp.data.repository.TransactionRepository
import com.aeryz.seimbanginapp.utils.ResultWrapper
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class OcrViewModel(private val repository: TransactionRepository) : ViewModel() {
    private val _scanReceiptResult = MutableLiveData<ResultWrapper<OcrResponse>>()
    val scanReceiptResult: LiveData<ResultWrapper<OcrResponse>> = _scanReceiptResult

    private val _isUploadEnabled = MutableLiveData(false)
    val isUploadEnabled: LiveData<Boolean> = _isUploadEnabled

    var currentImageUri: Uri? = null
        set(value) {
            field = value
            _isUploadEnabled.postValue(value != null)
        }

    fun scanReceipt(image: MultipartBody.Part) {
        viewModelScope.launch {
            repository.scanReceipt(image).collect {
                _scanReceiptResult.postValue(it)
            }
        }
    }
}