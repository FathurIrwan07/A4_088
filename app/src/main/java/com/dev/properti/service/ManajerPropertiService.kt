package com.dev.properti.service

import com.dev.properti.model.ManajerProperti
import com.dev.properti.model.ManajerPropertiResponse
import com.dev.properti.model.ManajerPropertiResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ManajerPropertiService {


    @GET("manajerproperti")
    suspend fun getAllManajer(): ManajerPropertiResponse

    @POST("manajerproperti/store")
    suspend fun insertManajer(@Body manajerProperti: ManajerProperti)


    @GET("manajerproperti/{id_manajer}")
    suspend fun getManajerById(@Path("id_manajer") id: Int): ManajerPropertiResponseDetail

    @PUT("manajerproperti/{id_manajer}")
    suspend fun updateManajer(@Path("id_manajer") id: Int, @Body manajerProperti: ManajerProperti)

    @DELETE("manajerproperti/{id_manajer}")
    suspend fun deleteManajer(@Path("id_manajer") id: Int): Response<Void>

}