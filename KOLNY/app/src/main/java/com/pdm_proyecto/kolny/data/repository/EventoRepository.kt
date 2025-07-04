package com.pdm_proyecto.kolny.data.repository

import com.pdm_proyecto.kolny.data.models.Evento
import javax.inject.Inject

class EventoRepository @Inject constructor() {

    private val eventos = mutableListOf<Evento>()
    private val solicitudes = mutableListOf<Evento>()

    init {
        enviarSolicitud(
            Evento(
                id = generarId(),
                titulo = "Torneo de fútbol",
                descripcion = "Competencia entre colonias",
                lugar = "Cancha principal",
                fecha = "2025-07-05",
                horaInicio = "08:00",
                horaFin = "12:00",
                creadoPor = "Carlos Gómez"
            )
        )

        enviarSolicitud(
            Evento(
                id = generarId(),
                titulo = "Clase de yoga",
                descripcion = "Clase al aire libre para adultos",
                lugar = "Parque central",
                fecha = "2025-07-06",
                horaInicio = "07:00",
                horaFin = "08:00",
                creadoPor = "Ana Ramos"
            )
        )

        agregarEvento(
            Evento(
                titulo = "Reunión de vecinos",
                descripcion = "Discusión sobre seguridad y mejoras",
                lugar = "Casa comunal",
                fecha = "2025-06-20",
                horaInicio = "18:00",
                horaFin = "19:30",
                creadoPor = "admin"
            )
        )
        agregarEvento(
            Evento(
                titulo = "Tarde de juegos",
                descripcion = "Juegos para niños en la cancha",
                lugar = "Cancha principal",
                fecha = "2025-06-21",
                horaInicio = "15:00",
                horaFin = "17:00",
                creadoPor = "admin"
            )
        )
    }

    fun getEventos(): List<Evento> = eventos
    fun getSolicitudes(): List<Evento> = solicitudes

    fun agregarEvento(evento: Evento): List<Evento> {
        val id = if (evento.id == 0) generarId() else evento.id
        eventos.add(evento.copy(id = id, aprobado = true))
        return eventos.toList()
    }

    fun enviarSolicitud(evento: Evento): List<Evento> {
        val id = if (evento.id == 0) generarId() else evento.id
        solicitudes.add(evento.copy(id = id, aprobado = false))
        return solicitudes.toList()
    }

    fun eliminarEvento(eventoId: Int): List<Evento> {
        eventos.removeIf { it.id == eventoId }
        return eventos.toList()
    }

    fun actualizarEvento(evento: Evento): List<Evento> {
        val index = eventos.indexOfFirst { it.id == evento.id }
        if (index != -1) eventos[index] = evento
        return eventos.toList()
    }

    fun aprobarSolicitud(evento: Evento): List<Evento> {
        eliminarSolicitud(evento.id)
        return agregarEvento(evento.copy(aprobado = true))
    }

    fun eliminarSolicitud(eventoId: Int): List<Evento> {
        solicitudes.removeIf { it.id == eventoId }
        return solicitudes.toList()
    }

    fun obtenerEventosPorFecha(fecha: String): List<Evento> {
        return eventos.filter { it.fecha == fecha }
    }

    fun existeTraslape(fecha: String, inicio: String, fin: String, ignorarId: Int? = null): Boolean {
        return eventos.any { evento ->
            if (ignorarId != null && evento.id == ignorarId) return@any false
            if (evento.fecha != fecha) return@any false
            val inicioConflicto = inicio < evento.horaFin
            val finConflicto = fin > evento.horaInicio
            inicioConflicto && finConflicto
        }
    }

    private fun generarId(): Int {
        val ids = (eventos.map { it.id } + solicitudes.map { it.id }).toSet()
        var id = 1
        while (ids.contains(id)) id++
        return id
    }
}
