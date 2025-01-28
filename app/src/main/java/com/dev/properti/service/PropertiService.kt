package com.dev.properti.service

import com.dev.properti.model.Pemilik
import com.dev.properti.model.PemilikResponse
import com.dev.properti.model.PemilikResponseDetail
import com.dev.properti.model.Properti
import com.dev.properti.model.PropertiResponse
import com.dev.properti.model.PropertiResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PropertiService {
    @POST("properti/store")
    suspend fun insertProperti(@Body properti: Properti)

    @GET("properti")
    suspend fun getAllProperti(): PropertiResponse

    @GET("properti/{id_properti}")
    suspend fun getPropertiById(@Path("id_properti") id: Int): PropertiResponseDetail

    @PUT("properti/{id_properti}")
    suspend fun updateProperti(@Path("id_properti") id: Int, @Body properti: Properti)

    @DELETE("properti/{id_properti}")
    suspend fun deleteProperti(@Path("id_properti") id: Int): Response<Void>
}