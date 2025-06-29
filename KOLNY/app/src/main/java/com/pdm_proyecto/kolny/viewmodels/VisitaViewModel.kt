package com.pdm_proyecto.kolny.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm_proyecto.kolny.data.models.Visita
import com.pdm_proyecto.kolny.data.repository.VisitaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisitaViewModel @Inject constructor(
    private val repository: VisitaRepository
) : ViewModel() {

    private val _visitas = MutableStateFlow<List<Visita>>(emptyList())
    val visitas: StateFlow<List<Visita>> = _visitas

    private val _selectedVisita = MutableStateFlow<Visita?>(null)
    val selectedVisita: StateFlow<Visita?> = _selectedVisita

    fun selectVisita(visita: Visita) {
        _selectedVisita.value = visita
    }

    fun clearSelectedVisita() {
        _selectedVisita.value = null
    }

    init {
        loadVisitas()
    }

    fun loadVisitas() {
        viewModelScope.launch {
            try {
                _visitas.value = repository.getAllVisitas().toList()
            } catch (e: Exception) {
                e.printStackTrace()
                _visitas.value = emptyList()
            }
        }
    }

    fun addVisita(visita: Visita) {
        viewModelScope.launch {
            try {
                _visitas.value = repository.addVisita(visita).toList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun editVisita(visita: Visita) {
        viewModelScope.launch {
            try {
                _visitas.value = repository.updateVisita(visita).toList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteVisita(visita: Visita) {
        viewModelScope.launch {
            try {
                _visitas.value = repository.deleteVisita(visita).toList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //función para eliminar después de alguna fecha??
}