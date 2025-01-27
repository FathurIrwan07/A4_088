package com.dev.properti.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManajerProperti (
    val id_manajer: Int,
    val nama_manajer: String,
    val kontak_manajer: String
)

@Serializable
data class ManajerPropertiResponse(
    val status: Boolean,
    val message: String,
    val data: List<ManajerProperti>
)

@Serializable
data class ManajerPropertiResponseDetail(
    val status: Boolean,
    val message: String,
    val data: ManajerProperti
)