package com.dev.properti.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class JenisProperti (
    val id_jenis: Int,
    val nama_jenis: String,
    val deskripsi_jenis: String
)

@Serializable
data class JenisPropertiResponse(
    val status: Boolean,
    val message: String,
    val data: List<JenisProperti>
)

@Serializable
data class JenisPropertiResponseDetail(
    val status: Boolean,
    val message: String,
    val data: JenisProperti
)