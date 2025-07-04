package com.pdm_proyecto.kolny.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm_proyecto.kolny.data.models.Evento
import com.pdm_proyecto.kolny.data.repository.EventoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: EventoRepository
) : ViewModel() {

    private val _eventos = MutableStateFlow<List<Evento>>(emptyList())
    val eventos: StateFlow<List<Evento>> = _eventos

    private val _solicitudes = MutableStateFlow<List<Evento>>(emptyList())
    val solicitudes: StateFlow<List<Evento>> = _solicitudes

    private val _eventoSeleccionado = MutableStateFlow<Evento?>(null)
    val eventoSeleccionado: StateFlow<Evento?> = _eventoSeleccionado

    init {
        loadEventos()
    }

    fun loadEventos() {
        viewModelScope.launch {
            _eventos.value = repository.getEventos().toList()
            _solicitudes.value = repository.getSolicitudes().toList()
        }
    }

    fun agregarEvento(evento: Evento) {
        viewModelScope.launch {
            _eventos.value = repository.agregarEvento(evento).toList()
        }
    }

    fun enviarSolicitud(evento: Evento) {
        viewModelScope.launch {
            _solicitudes.value = repository.enviarSolicitud(evento).toList()
        }
    }

    fun aprobarSolicitud(evento: Evento) {
        viewModelScope.launch {
            _solicitudes.value = repository.eliminarSolicitud(evento.id).toList()
            _eventos.value = repository.aprobarSolicitud(evento).toList()
        }
    }

    fun eliminarEvento(id: Int) {
        viewModelScope.launch {
            _eventos.value = repository.eliminarEvento(id).toList()
        }
    }

    fun actualizarEvento(evento: Evento) {
        viewModelScope.launch {
            _eventos.value = repository.actualizarEvento(evento).toList()
        }
    }

    fun eliminarSolicitud(id: Int) {
        viewModelScope.launch {
            _solicitudes.value = repository.eliminarSolicitud(id).toList()
        }
    }

    fun obtenerEventosPorFecha(fecha: String): List<Evento> {
        return repository.obtenerEventosPorFecha(fecha)
    }

    fun seleccionarEvento(evento: Evento) {
        _eventoSeleccionado.value = evento
    }

    fun limpiarEventoSeleccionado() {
        _eventoSeleccionado.value = null
    }

    fun existeTraslapeDeEvento(fecha: String, horaInicio: String, horaFin: String): Boolean {
        val idIgnorado = _eventoSeleccionado.value?.id
        return repository.existeTraslape(fecha, horaInicio, horaFin, idIgnorado)
    }
}
