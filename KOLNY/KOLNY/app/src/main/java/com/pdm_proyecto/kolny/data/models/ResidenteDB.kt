package com.pdm_proyecto.kolny.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResidenteDB(
    @SerialName("residentedui") val duiadministrador: String,
    @SerialName("numerocasa") val numeroCasa: String
)