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

    private val _selectedUsuario = MutableStateFlow<Usuario?>(null)
    val selectedUsuario: StateFlow<Usuario?> = _selectedUsuario

    fun selectUsuario(usuario: Usuario) {
        _selectedUsuario.value = usuario
    }

    fun clearSelectedUsuario() {
        _selectedUsuario.value = null
    }

    init {
        loadUsuarios()
    }

    fun loadUsuarios() {
        viewModelScope.launch {
            try {
                /*poner aquí el dao para cargar los usuarios*/
                _usuarios.value = repository.getAllUsuarios().toList()
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
                //aquí poner el dao, para agregar el usuario
                _usuarios.value = repository.addUsuario(usuario).toList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun editUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                _usuarios.value = repository.updateUsuario(usuario).toList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                _usuarios.value = repository.deleteUsuario(usuario).toList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}