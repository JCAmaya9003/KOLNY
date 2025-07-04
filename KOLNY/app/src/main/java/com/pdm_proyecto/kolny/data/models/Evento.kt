package com.pdm_proyecto.kolny.data.models

import kotlinx.serialization.Serializable
import com.pdm_proyecto.kolny.data.models.toEventoDB
import com.pdm_proyecto.kolny.data.models.toEvento

data class Evento (
    val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val lugar: String,
    val fecha: String,
    val horaInicio: String = "",
    val horaFin: String = "",
    val creadoPor: String = "",
    val aprobado: Boolean = false
)