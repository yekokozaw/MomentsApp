package com.padc.moments.network.retrofit

import com.padc.moments.data.vos.fcm.FCMBody
import com.padc.moments.network.retrofit.responses.FCMResponse
import com.padc.moments.network.retrofit.responses.TrendingGifResponse
import com.padc.moments.utils.API_GET_GIPHY_SEARCH
import com.padc.moments.utils.API_GET_GIPHY_TRENDING
import com.padc.moments.utils.API_POST_FCM
import com.padc.moments.utils.FCM_AUTH_KEY
import com.padc.moments.utils.GIPHY_API_KEY
import com.padc.moments.utils.HEADER_AUTH
import com.padc.moments.utils.PARAM_API_KEY
import com.padc.moments.utils.PARAM_API_KEY_SEARCH
import com.padc.moments.utils.PARAM_API_LIMIT
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TheMomentApi {

    @GET(API_GET_GIPHY_TRENDING)
    fun getAllTrendingGifs(
        @Query(PARAM_API_KEY) api_key:String = GIPHY_API_KEY,
        @Query(PARAM_API_LIMIT) limit:String = "10"
    ) : Observable<TrendingGifResponse>

    @GET(API_GET_GIPHY_SEARCH)
    fun searchGifs(
        @Query(PARAM_API_KEY) api_key:String = GIPHY_API_KEY,
        @Query(PARAM_API_KEY_SEARCH) q:String = "trending",
        @Query(PARAM_API_LIMIT) limit:String = "5"
    ) : Observable<TrendingGifResponse>

    @POST(API_POST_FCM)
    fun sendFCMNotification(
        @Header(HEADER_AUTH) authorization:String = FCM_AUTH_KEY,
        @Body fcmBody:FCMBody
    ) : Call<FCMResponse>
}