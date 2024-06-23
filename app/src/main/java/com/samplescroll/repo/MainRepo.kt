package com.samplescroll.repo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.samplescroll.models.ImagesModel
import com.samplescroll.remote.ApiInterface
import com.samplescroll.util.ApiResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MainRepo @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiInterface: ApiInterface
) {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
    private val gson = Gson()

    suspend fun fetchData(): ApiResponse<List<ImagesModel>?> {
        try {
            val response = apiInterface.fetchData()
            return if (response.isSuccessful) {
                saveData(response.body()!!)
                ApiResponse.Success(response.body()!!)
            } else {
                ApiResponse.Error(response.message())
            }
        } catch (e: Exception) {
            return ApiResponse.Success(getData())
        }
    }

    private fun saveData(data: List<ImagesModel>) {
        val jsonData = gson.toJson(data)
        val editor = sharedPreferences.edit()
        editor.putString("data", jsonData)
        editor.apply()
    }

    private fun getData(): List<ImagesModel>? {
        val jsonData = sharedPreferences.getString("data", null) ?: return null
        val type = object : TypeToken<List<ImagesModel>>() {}.type
        return gson.fromJson(jsonData, type)
    }
}