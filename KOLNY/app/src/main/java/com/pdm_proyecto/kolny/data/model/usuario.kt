package com.pdm_proyecto.kolny.data.model

data class Usuario(
    val id: Int,
    val nombre: String,
    val activo: Boolean,
    val rol: String,
    val tipoAdmin: String? // puede ser null si no es administrador
)
