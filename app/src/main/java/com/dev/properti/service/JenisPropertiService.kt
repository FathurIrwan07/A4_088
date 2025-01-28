package com.dev.properti.service

import com.dev.properti.model.JenisProperti
import com.dev.properti.model.JenisPropertiResponse
import com.dev.properti.model.JenisPropertiResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JenisPropertiService {


    @GET("jenisproperti")
    suspend fun getAllJenis(): JenisPropertiResponse

    @POST("jenisproperti/store")
    suspend fun insertJenis(@Body jenisProperti: JenisProperti)


    @GET("jenisproperti/{id_jenis}")
    suspend fun getJenisById(@Path("id_jenis") id: Int): JenisPropertiResponseDetail

    @PUT("jenisproperti/{id_jenis}")
    suspend fun updateJenis(@Path("id_jenis") id: Int, @Body jenisProperti: JenisProperti)

    @DELETE("jenisproperti/{id_jenis}")
    suspend fun deleteJenis(@Path("id_jenis") id: Int): Response<Void>
}