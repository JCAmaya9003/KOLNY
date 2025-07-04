package com.pdm_proyecto.kolny.data.models

import java.util.Date

data class Visita(
    val nombreVisita: String,
    val dui: String,
    val placa: String? = null,
    val motivo: String,
    val fechaVisita: Date
)