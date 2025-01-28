package com.dev.properti.repository


import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.dev.properti.model.ManajerPropertiResponseDetail
import com.dev.properti.model.Properti
import com.dev.properti.model.PropertiResponse
import com.dev.properti.model.PropertiResponseDetail
import com.dev.properti.service.PropertiService
import java.io.IOException

interface PropertiRepository {
    suspend fun insertProperti(properti: Properti)

    suspend fun getAllProperti(): PropertiResponse

    suspend fun getPropertiById(id_properti: Int): PropertiResponseDetail

    suspend fun updateProperti(id_properti: Int, properti: Properti)

    suspend fun deleteProperti(id_properti: Int)
}

class NetworkPropertiRepository(
    private val propertiApiService: PropertiService
) : PropertiRepository {

    override suspend fun insertProperti(properti: Properti) {
        try {
            propertiApiService.insertProperti(properti)
        } catch (e: Exception) {
            throw IOException("Failed to insert properti", e)
        }
    }

    override suspend fun updateProperti(id: Int, properti: Properti) {
        try {
            propertiApiService.updateProperti(id, properti)
        } catch (e: Exception) {
            throw IOException("Failed to update properti", e)
        }
    }

    override suspend fun deleteProperti(id: Int) {
        try {
            propertiApiService.deleteProperti(id)
        } catch (e: Exception) {
            throw IOException("Failed to delete properti", e)
        }
    }

    override suspend fun getAllProperti(): PropertiResponse {
        return try {
            propertiApiService.getAllProperti()
        } catch (e: Exception) {
            throw IOException("Failed to fetch all properti", e)
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getPropertiById(id_properti: Int): PropertiResponseDetail {
        return try {
            propertiApiService.getPropertiById(id_properti)
        } catch (e: Exception) {
            throw IOException("Failed to fetch manajer by id", e)
        }
        catch (o: HttpException){
            throw HttpException("Failed to fetch manajer by id",o)
        }
    }
}
