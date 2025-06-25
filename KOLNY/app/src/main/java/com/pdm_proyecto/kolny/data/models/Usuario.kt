package com.pdm_proyecto.kolny.data.models

import java.util.Date

data class Usuario(
    val fotoPerfil: String? = null,
    val dui: String,
    val nombre: String,
    val telefono: String,
    val fechaNacimiento: Date,
    val casa: String,
    val email: String,
    val password: String,
    val activo: Boolean = true,
    val rol: String = "USUARIO",
    val tipoAdmin: String? = null,
)