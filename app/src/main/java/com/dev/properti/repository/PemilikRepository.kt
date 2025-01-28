package com.dev.properti.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.dev.properti.model.Pemilik
import com.dev.properti.model.PemilikResponse
import com.dev.properti.model.PemilikResponseDetail
import com.dev.properti.model.PropertiResponseDetail
import com.dev.properti.service.PemilikService
import java.io.IOException

interface PemilikRepository{
    suspend fun insertPemilik(pemilik: Pemilik)

    suspend fun getAllPemilik(): PemilikResponse

    suspend fun getPemilikById(id_pemilik: Int): PemilikResponseDetail

    suspend fun updatePemilik(id_pemilik: Int, pemilik: Pemilik)

    suspend fun deletePemilik(id_pemilik: Int)


}

class NetworkPemilikRepository(
    private val pemilikApiService: PemilikService
) : PemilikRepository {
    override suspend fun insertPemilik(pemilik: Pemilik) {
        try {
            pemilikApiService.insertPemilik(pemilik)
        } catch (e: Exception) {
            throw IOException("Failed to insert pemilik", e)
        }
    }

    override suspend fun updatePemilik(id: Int, pemilik: Pemilik) {
        try {
            pemilikApiService.updatePemilik(id, pemilik)
        } catch (e: Exception) {
            throw IOException("Failed to update Pemilik", e)
        }
    }

    override suspend fun deletePemilik(id: Int) {
        try {
            pemilikApiService.deletePemilik(id)
        } catch (e: Exception) {
            throw IOException("Failed to delete pemilik", e)
        }
    }

    override suspend fun getAllPemilik(): PemilikResponse {
        return try {
            pemilikApiService.getAllPemilik()
        } catch (e: Exception) {
            throw IOException("Failed to fetch pemilik", e)
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getPemilikById(id_pemilik: Int): PemilikResponseDetail {
        return try {
            pemilikApiService.getPemilikById(id_pemilik)
        } catch (e: Exception) {
            throw IOException("Failed to fetch manajer by id", e)
        }
        catch (o: HttpException){
            throw HttpException("Failed to fetch manajer by id",o)
        }
    }

}