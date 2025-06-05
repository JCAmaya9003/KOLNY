package com.pdm_proyecto.kolny.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/*en lugar del repository va a ir el dao*/
class UsuarioViewModel(private val repository: UsuarioRepository) : ViewModel() {

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios

    init {
        loadUsuarios()
    }

    fun loadUsuarios() {
        viewModelScope.launch {
            try {
                _usuarios.value = repository.getAllUsuarios()
            }
            catch (e: Exception) {
                e.printStackTrace()
                _usuarios.value = emptyList()
            }
        }
    }

    fun addUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                /*porner aquí el dao para agregar*/
                repository.addUsuario(usuario)
                _usuarios.value = repository.getAllUsuarios()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun editUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                val currentList = _usuarios.value.toMutableList()
                val index = currentList.indexOfFirst { it.dui == usuario.dui }
                if (index != -1) {
                    currentList.removeAt(index)
                    currentList.add(index, usuario)
                    _usuarios.value = currentList
                    /*poner aquí el dao para editar*/
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                val updatedList = _usuarios.value.filterNot { it.dui == usuario.dui }
                _usuarios.value = updatedList
                /*poner aquí el dao para eliminar*/
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}