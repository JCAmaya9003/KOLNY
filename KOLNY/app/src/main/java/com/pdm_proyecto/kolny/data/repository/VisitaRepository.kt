package com.pdm_proyecto.kolny.data.repository

import com.pdm_proyecto.kolny.data.models.Visita
import com.pdm_proyecto.kolny.utils.createDate
import javax.inject.Inject

class VisitaRepository @Inject constructor() {
    private val visitas = mutableListOf<Visita>()

    init {
        visitas.addAll(
            listOf(
                Visita(
                    nombreVisita = "Carlos Méndez",
                    dui = "12345678-9",
                    placa = "P-123456",
                    motivo = "Reunión con el residente",
                    fechaVisita = createDate(2025, 8, 10)
                ),
                Visita(
                    nombreVisita = "Lucía Morales",
                    dui = "23456789-0",
                    motivo = "Entrega de productos",
                    fechaVisita = createDate(2025, 8, 12)
                ),
                Visita(
                    nombreVisita = "José Hernández",
                    dui = "34567890-1",
                    motivo = "Visita familiar",
                    fechaVisita = createDate(2025, 8, 15)
                ),
                Visita(
                    nombreVisita = "Andrea Castillo",
                    dui = "45678901-2",
                    placa = "MB-45678",
                    motivo = "Inspección de mantenimiento",
                    fechaVisita = createDate(2025, 8, 18)
                )
            )
        )
    }

    fun getAllVisitas(): List<Visita> = visitas

    fun addVisita(visita: Visita): List<Visita> {
        visitas.add(visita)
        return visitas.toList() // que el DAO devuelva la lista de visitas actualizada
    }

    fun updateVisita(visitaActualizada: Visita): List<Visita> {
        val index = visitas.indexOfFirst { it.dui == visitaActualizada.dui }
        if (index != -1) {
            visitas[index] = visitaActualizada
        }
        return visitas.toList()
    }

    fun deleteVisita(visita: Visita): List<Visita> {
        visitas.removeAll { it.dui == visita.dui }
        return visitas.toList()
    }

    fun getVisitaByDui(dui: String): Visita? = visitas.find { it.dui == dui }
}