package com.cc.t820.net

import com.cc.t820.data.DataBean

import com.cc.t820.data.PoetryBean

import retrofit2.http.GET
import retrofit2.http.Header

interface MyApi {

    @GET("info")
    suspend fun info(): DataBean


    @GET("sentence")
    suspend fun sentence(@Header("X-User-Token") token: String): PoetryBean
}