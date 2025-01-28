package com.dev.properti.model

import kotlinx.serialization.Serializable

@Serializable
data class Properti (

    val id_properti: Int,
    val nama_properti: String,
    val deskripsi_properti: String,
    val lokasi: String,
    val harga: String,
    val status_properti: String,
    val id_jenis: Int?,
    val id_pemilik: Int?,
    val id_manajer: Int?
)

@Serializable
data class PropertiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Properti>
)

@Serializable
data class PropertiResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Properti
)