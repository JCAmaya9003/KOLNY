package com.pdm_proyecto.kolny.data.models

import kotlinx.serialization.Serializable
import com.pdm_proyecto.kolny.data.models.toEventoDB
import com.pdm_proyecto.kolny.data.models.toEvento


@Serializable
data class EventoDB(
    val idevento: Int? = null,
    val titulo: String,
    val descripcion: String,
    val fechaevento: String,
    val ubicacion: String,
    val creadordui: String,
    val horainicio: String,
    val horafin: String

)

