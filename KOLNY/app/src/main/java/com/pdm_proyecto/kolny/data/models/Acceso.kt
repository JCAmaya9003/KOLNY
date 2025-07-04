package com.pdm_proyecto.kolny.data.models

sealed class ResultadoAcceso {

    data class AdminDesarrollador(val usuario: Usuario): ResultadoAcceso()
    data class AdminDirectiva(val usuario: Usuario): ResultadoAcceso()
    data class Residente(val usuario: Usuario): ResultadoAcceso()
    data class Vigilante(val usuario: Usuario): ResultadoAcceso()
    object NoRegistrado : ResultadoAcceso()
    object Inactivo : ResultadoAcceso()
    object Desconocido : ResultadoAcceso()
    // NUEVO: error con mensaje (se quitara en un futuro ya que solo sirve para ver errores de la base)
    data class ErrorBd(val mensaje: String)       : ResultadoAcceso()
}
