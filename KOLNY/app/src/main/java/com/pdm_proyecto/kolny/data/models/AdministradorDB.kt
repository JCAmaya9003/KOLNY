package com.pdm_proyecto.kolny.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdministradorDb(
    @SerialName("administradordui") val duiadministrador: String,
    @SerialName("idtipoadmin")      val idtipoadmin: Int  // 1 = Desarrollador, 2 = Directiva
)
