package com.padc.moments.data.models

import com.padc.moments.data.vos.giphy.Data
import io.reactivex.rxjava3.core.Observable

interface GiphyModel {

    fun getAllTrendingGifs(
        onSuccess:(List<Data>) -> Unit,
        onFailure:(String) -> Unit
    )

    fun searchGifs(
        query:String
    ) : Observable<List<Data>>
}