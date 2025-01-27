package com.dev.properti.repository

import com.dev.properti.model.JenisProperti
import com.dev.properti.model.JenisPropertiResponse
import com.dev.properti.model.JenisPropertiResponseDetail
import com.dev.properti.model.Pemilik
import com.dev.properti.model.PemilikResponse
import com.dev.properti.service.JenisPropertiService
import java.io.IOException

interface JenisRepository {
    suspend fun insertJenis(jenisProperti: JenisProperti)

    suspend fun getAllJenis(): JenisPropertiResponse

    suspend fun getJenisById(id_jenis: Int): JenisProperti

    suspend fun updateJenis(id_jenis: Int, jenisProperti: JenisProperti)

    suspend fun deleteJenis(id_jenis: Int)
}

class NetworkJenisRepository(
    private val jenisApiService: JenisPropertiService
) : JenisRepository {
    override suspend fun insertJenis(jenisProperti: JenisProperti) {
        try {
            jenisApiService.insertJenis(jenisProperti)
        } catch (e: Exception) {
            throw IOException("Failed to insert jenis", e)
        }
    }

    override suspend fun updateJenis(id: Int, jenisProperti: JenisProperti) {
        try {
            jenisApiService.updateJenis(id, jenisProperti)
        } catch (e: Exception) {
            throw IOException("Failed to update jenis", e)
        }
    }

    override suspend fun deleteJenis(id: Int) {
        try {
            jenisApiService.deleteJenis(id)
        } catch (e: Exception) {
            throw IOException("Failed to delete jenis", e)
        }
    }

    override suspend fun getAllJenis(): JenisPropertiResponse {
        return try {
            jenisApiService.getAllJenis()
        } catch (e: Exception) {
            throw IOException("Failed to fetch jenis", e)
        }
    }

    override suspend fun getJenisById(id: Int): JenisProperti {
        return try {
            jenisApiService.getJenisById(id).data
        } catch (e: Exception) {
            throw IOException("Failed to fetch jenis by id", e)
        }
    }

}
