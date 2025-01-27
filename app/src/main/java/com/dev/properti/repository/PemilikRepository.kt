package com.dev.properti.repository

import com.dev.properti.model.Pemilik
import com.dev.properti.model.PemilikResponse
import com.dev.properti.service.PemilikService
import java.io.IOException

interface PemilikRepository{
    suspend fun insertPemilik(pemilik: Pemilik)

    suspend fun getAllPemilik(): PemilikResponse

    suspend fun getPemilikById(id_pemilik: Int): Pemilik

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

    override suspend fun getPemilikById(id: Int): Pemilik {
        return try {
            pemilikApiService.getPemilikById(id).data
        } catch (e: Exception) {
            throw IOException("Failed to fetch pemilik by id", e)
        }
    }

}