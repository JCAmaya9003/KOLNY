package com.pdm_proyecto.kolny.data.models

import com.pdm_proyecto.kolny.ui.components.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable

data class Usuario(
    val fotoPerfil: String? = null,
    val dui: String,
    val nombre: String,
    val telefono: String,
    @Serializable(with = DateSerializer::class)
    val fechaNacimiento: Date,
    val casa: String? = null,
    val email: String,
    val password: String,
    val activo: Boolean = true,
    val rol: String = "USUARIO",
    val tipoAdmin: String? = null,
)