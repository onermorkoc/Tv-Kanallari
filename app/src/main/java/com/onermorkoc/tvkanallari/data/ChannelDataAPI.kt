package com.onermorkoc.tvkanallari.data

import retrofit2.Response
import retrofit2.http.GET

interface ChannelDataAPI {

    @GET("onermorkoc/Tv-List/main/tv_list.json")
    suspend fun getData(): Response<ArrayList<ChannelData>>
}