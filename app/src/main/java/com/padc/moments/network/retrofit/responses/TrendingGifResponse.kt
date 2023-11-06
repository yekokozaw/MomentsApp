package com.padc.moments.network.retrofit.responses

import com.padc.moments.data.vos.giphy.Data
import com.google.gson.annotations.SerializedName

data class TrendingGifResponse(
    @SerializedName("data")
    val data: List<Data>?
)