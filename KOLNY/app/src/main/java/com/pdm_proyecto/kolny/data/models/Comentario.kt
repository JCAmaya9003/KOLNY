package com.pdm_proyecto.kolny.data.models

import java.util.Date

data class Comentario(
    val idcomentario: Int = 0,
    val idnoticia: Int,
    val idautor: String,
    val contenido: String,
    val fechacomentario: Date = Date()
)
