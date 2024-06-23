package com.samplescroll.images.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samplescroll.models.ImagesModel
import com.samplescroll.repo.MainRepo
import com.samplescroll.util.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(private val repo: MainRepo) : ViewModel() {
    private val _imagesResponse: MutableSharedFlow<ApiResponse<List<ImagesModel>?>> =
        MutableSharedFlow()
    val imagesResponse: SharedFlow<ApiResponse<List<ImagesModel>?>> = _imagesResponse

    fun fetchQuotes() {
        viewModelScope.launch {
            val response = repo.fetchData()
            _imagesResponse.emit(response)
        }
    }
}