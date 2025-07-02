package com.pdm_proyecto.kolny.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsuarioDB(
    @SerialName("idusuario") val idusuario: Int,
    val activo: Boolean,
    val fotoperfil: ByteArray? = null,
    val nombre: String,
    val dui: String,
    val telefono: String? = null,
    val correo: String,
    val password: String,
    @SerialName("idrol") val idrol: Int
)