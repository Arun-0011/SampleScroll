package com.samplescroll.models

data class ImagesModel(
    var id: String? = null,
    var author: String? = null,
    var width: Int = 0,
    var height: Int = 0,
    var url: String? = null,
    var download_url: String? = null
)