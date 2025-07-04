// NoticiaDB.kt
package com.pdm_proyecto.kolny.data.models

import kotlinx.serialization.Serializable
@Serializable
data class NoticiaDB(
    val idnoticia: Int = 0,
    val titulo: String,
    val contenido: String,
    val fechapublicacion: String,
    val categoria: String,
    val autordui: String
)
