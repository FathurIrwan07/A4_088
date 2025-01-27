package com.dev.properti.service

import com.dev.properti.model.Pemilik
import com.dev.properti.model.PemilikResponse
import com.dev.properti.model.PemilikResponseDetail
import retrofit2.Response
import retrofit2.http.*

interface PemilikService {

    @POST("pemilik/store")
    suspend fun insertPemilik(@Body pemilik: Pemilik)

    @GET("pemilik")
    suspend fun getAllPemilik(): PemilikResponse

    @GET("pemilik/{id_pemilik}")
    suspend fun getPemilikById(@Path("id_pemilik") id: Int): PemilikResponseDetail

    @PUT("pemilik/{id_pemilik}")
    suspend fun updatePemilik(@Path("id_pemilik") id: Int, @Body pemilik: Pemilik)

    @DELETE("pemilik/{id_pemilik}")
    suspend fun deletePemilik(@Path("id_pemilik") id: Int): Response<Void>
}
