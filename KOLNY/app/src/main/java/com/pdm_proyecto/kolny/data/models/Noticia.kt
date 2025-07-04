package com.pdm_proyecto.kolny.data.models

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class Noticia(
    val id: String,
    val titulo: String,
    val contenido: String,
    val fechaPublicacion: LocalDateTime,
    val autorId: String  // Relación con Usuario

)
