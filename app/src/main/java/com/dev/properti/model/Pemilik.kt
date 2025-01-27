package com.dev.properti.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pemilik (

    val id_pemilik: Int,
    val nama_pemilik: String,
    val kontak_pemilik: String
)

@Serializable
data class PemilikResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pemilik>
)

@Serializable
data class PemilikResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Pemilik
)