package com.pdm_proyecto.kolny.data.model

sealed class ResultadoAcceso {

    data class AdminDesarrollador(val usuario: Usuario): ResultadoAcceso()
    data class AdminDirectiva(val usuario: Usuario): ResultadoAcceso()
    data class Residente(val usuario: Usuario): ResultadoAcceso()
    data class Vigilante(val usuario: Usuario): ResultadoAcceso()
    object NoRegistrado : ResultadoAcceso()
    object Inactivo : ResultadoAcceso()
    object Desconocido : ResultadoAcceso()
}
