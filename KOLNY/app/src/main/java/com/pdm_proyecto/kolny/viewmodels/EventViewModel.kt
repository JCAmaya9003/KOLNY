package com.pdm_proyecto.kolny.viewmodels


import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.pdm_proyecto.kolny.data.models.Evento
import java.util.*

class EventViewModel : ViewModel() {

    var eventoSeleccionado by mutableStateOf<Evento?>(null)
        private set

    private val _eventos = mutableStateListOf<Evento>()
    val eventos: List<Evento> = _eventos

    private val _solicitudes = mutableStateListOf<Evento>()
    val solicitudes: List<Evento> = _solicitudes

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



    fun agregarEvento(evento: Evento) {
        val idAsignado = if (evento.id == 0) generarId() else evento.id
        _eventos.add(evento.copy(id = idAsignado, aprobado = true))
    }


    fun enviarSolicitud(evento: Evento) {
        val idAsignado = if (evento.id == 0) generarId() else evento.id
        _solicitudes.add(evento.copy(id = idAsignado, aprobado = false))
    }

    fun eliminarEvento(eventoId: Int) {
        _eventos.removeIf { it.id == eventoId }
    }

    fun actualizarEvento(eventoActualizado: Evento) {
        val index = _eventos.indexOfFirst { it.id == eventoActualizado.id }
        if (index != -1) {
            _eventos[index] = eventoActualizado
        }
    }

    fun obtenerEventosPorFecha(fecha: String): List<Evento> {
        return _eventos.filter { it.fecha == fecha }
    }

    fun aprobarSolicitud(evento: Evento) {
        val eventoAprobado = evento.copy(aprobado = true)
        eliminarSolicitud(evento.id)
        agregarEvento(eventoAprobado)
    }

    fun eliminarSolicitud(eventoId: Int) {
        _solicitudes.removeIf { it.id == eventoId }
    }

    fun seleccionarEvento(evento: Evento) {
        eventoSeleccionado = evento
    }

    fun limpiarEventoSeleccionado() {
        eventoSeleccionado = null
    }

    fun obtenerSolicitudes(): List<Evento> {
        return solicitudes
    }

    fun existeTraslapeDeEvento(fecha: String, horaInicio: String, horaFin: String): Boolean {
        return eventos.any { evento ->

            if (eventoSeleccionado != null && evento.id == eventoSeleccionado!!.id) {
                return@any false
            }

            if (evento.fecha != fecha) return@any false

            val inicioConflicto = horaInicio < evento.horaFin
            val finConflicto = horaFin > evento.horaInicio

            inicioConflicto && finConflicto
        }
    }


    private fun generarId(): Int {
        var nuevoId = 1
        val idsExistentes = (_eventos.map { it.id } + _solicitudes.map { it.id }).toSet()
        while (idsExistentes.contains(nuevoId)) {
            nuevoId++
        }
        return nuevoId
    }


}
