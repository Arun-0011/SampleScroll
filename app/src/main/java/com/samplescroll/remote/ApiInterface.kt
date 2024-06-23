package com.samplescroll.remote

import com.samplescroll.models.ImagesModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("v2/list")
    suspend fun fetchData(): Response<List<ImagesModel>>
}