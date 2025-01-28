package com.dev.properti.di

import com.dev.properti.repository.JenisRepository
import com.dev.properti.repository.ManajerRepository
import com.dev.properti.repository.NetworkJenisRepository
import com.dev.properti.repository.NetworkManajerRepository
import com.dev.properti.repository.NetworkPemilikRepository
import com.dev.properti.repository.NetworkPropertiRepository
import com.dev.properti.repository.PemilikRepository
import com.dev.properti.repository.PropertiRepository
import com.dev.properti.service.JenisPropertiService
import com.dev.properti.service.ManajerPropertiService
import com.dev.properti.service.PemilikService
import com.dev.properti.service.PropertiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pemilikRepository: PemilikRepository
    val jenisRepository: JenisRepository
    val manajerRepository: ManajerRepository
    val propertiRepository: PropertiRepository
}

class PropertiContainer : AppContainer {

    private val baseUrl = "http://192.168.1.8:3000/api/" // localhost dengan garis miring di akhir
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // Pemilik Service dan Repository
    private val pemilikService: PemilikService by lazy {
        retrofit.create(PemilikService::class.java)
    }

    override val pemilikRepository: PemilikRepository by lazy {
        NetworkPemilikRepository(pemilikService)
    }

    // Jenis Service dan Repository
    private val jenisPropertiService: JenisPropertiService by lazy {
        retrofit.create(JenisPropertiService::class.java)
    }

    override val jenisRepository: JenisRepository by lazy {
        NetworkJenisRepository(jenisPropertiService)
    }

    // Manajer Service dan Repository
    private val manajerPropertiService: ManajerPropertiService by lazy {
        retrofit.create(ManajerPropertiService::class.java)
    }

    override val manajerRepository: ManajerRepository by lazy {
        NetworkManajerRepository(manajerPropertiService)
    }

    private val propertiService: PropertiService by lazy {
        retrofit.create(PropertiService::class.java)
    }

    override val propertiRepository: PropertiRepository by lazy {
        NetworkPropertiRepository(propertiService)
    }
}
