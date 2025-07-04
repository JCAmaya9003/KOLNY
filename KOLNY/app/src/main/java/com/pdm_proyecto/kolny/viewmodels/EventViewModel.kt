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

    private val _eventoSeleccionado = MutableStateFlow<Evento?>(null)
    val eventoSeleccionado: StateFlow<Evento?> = _eventoSeleccionado

    init {
        loadEventos()
    }

    fun loadEventos() {
        viewModelScope.launch {
            _eventos.value = repository.getEventos()
        }
    }

    fun enviarSolicitud(evento: Evento) {
        viewModelScope.launch {
            val success = repository.enviarSolicitud(evento)
            if (success) {
                loadEventos()
            }
        }
    }

    fun eliminarEvento(id: Int) {
        viewModelScope.launch {
            val success = repository.eliminarEvento(id)
            if (success) {
                loadEventos()
            }
        }
    }

    fun actualizarEvento(evento: Evento) {
        viewModelScope.launch {
            val success = repository.actualizarEvento(evento)
            if (success) {
                loadEventos()
            }
        }
    }

    fun obtenerEventosPorFecha(fecha: String, onResult: (List<Evento>) -> Unit) {
        viewModelScope.launch {
            val eventosPorFecha = repository.obtenerEventosPorFecha(fecha)
            onResult(eventosPorFecha)
        }
    }

    fun seleccionarEvento(evento: Evento) {
        _eventoSeleccionado.value = evento
    }

    fun limpiarEventoSeleccionado() {
        _eventoSeleccionado.value = null
    }

    fun existeTraslapeDeEvento(fecha: String, horaInicio: String, horaFin: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val traslape = repository.existeTraslape(fecha, horaInicio, horaFin)
            onResult(traslape)
        }
    }
}

