package com.pdm_proyecto.kolny.data.models

import com.pdm_proyecto.kolny.ui.components.DateSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import java.util.Date

@Serializable
data class UsuarioDB(
    @SerialName("dui") val dui: String,
    val activo: Boolean,
    val fotoperfil: ByteArray? = null,
    val nombre: String,
    val telefono: String? = null,
    val correo: String,
    val password: String,
    @SerialName("idrol") val idrol: Int,
    @Serializable(with = DateSerializer::class)
    val fecha_nacimiento: Date,
    val residente: ResidenteDB? = null
)