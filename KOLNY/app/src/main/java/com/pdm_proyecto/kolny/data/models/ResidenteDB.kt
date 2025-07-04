package com.pdm_proyecto.kolny.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import java.util.Date

@Serializable
data class ResidenteDB(
    @SerialName("residentedui") val dui: String,
    @SerialName("numerocasa") val numerocasa: String
)