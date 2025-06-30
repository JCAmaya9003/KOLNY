package com.pdm_proyecto.kolny.data.models

import java.util.Date

data class Noticia(
    val idnoticia: Int = 0, // Puedes dejarlo en 0 o null cuando sea auto-incremental
    val titulo: String,
    val contenido: String,
    val fechapublicacion: Date = Date(),
    val categoria: String,
    val idautor: Int
)
