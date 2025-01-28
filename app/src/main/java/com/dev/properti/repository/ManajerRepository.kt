package com.dev.properti.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.dev.properti.model.ManajerProperti
import com.dev.properti.model.ManajerPropertiResponse
import com.dev.properti.model.ManajerPropertiResponseDetail
import com.dev.properti.service.ManajerPropertiService
import java.io.IOException

interface ManajerRepository{
    suspend fun insertManajer(manajerProperti: ManajerProperti)

    suspend fun getAllManajer(): ManajerPropertiResponse

    suspend fun getManajerById(id_manajer: Int): ManajerPropertiResponseDetail

    suspend fun updateManajer(id_manajer: Int, manajerProperti: ManajerProperti)

    suspend fun deleteManajer(id_manajer: Int)
}

class NetworkManajerRepository(
    private val manajerApiService: ManajerPropertiService
) : ManajerRepository {
    override suspend fun insertManajer(manajerProperti: ManajerProperti) {
        try {
            manajerApiService.insertManajer(manajerProperti)
        } catch (e: Exception) {
            throw IOException("Failed to insert manajer", e)
        }
    }

    override suspend fun updateManajer(id: Int, manajerProperti: ManajerProperti) {
        try {
            manajerApiService.updateManajer(id, manajerProperti)
        } catch (e: Exception) {
            throw IOException("Failed to update Manajer", e)
        }
    }

    override suspend fun deleteManajer(id: Int) {
        try {
            manajerApiService.deleteManajer(id)
        } catch (e: Exception) {
            throw IOException("Failed to delete manajer", e)
        }
    }

    override suspend fun getAllManajer(): ManajerPropertiResponse {
        return try {
            manajerApiService.getAllManajer()
        } catch (e: Exception) {
            throw IOException("Failed to fetch manajer", e)
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getManajerById(id_manajer: Int): ManajerPropertiResponseDetail {
        return try {
            manajerApiService.getManajerById(id_manajer)
        } catch (e: Exception) {
            throw IOException("Failed to fetch manajer by id", e)
        }
        catch (o: HttpException){
            throw HttpException("Failed to fetch manajer by id",o)
        }
    }

}