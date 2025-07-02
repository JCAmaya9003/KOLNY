package com.pdm_proyecto.kolny.data.models

import kotlinx.serialization.Serializable

@Serializable
data class AdministradorDb(
    val idtipoadmin: Int          // 1 = Desarrollador, 2 = Directiva
)
