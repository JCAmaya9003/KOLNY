package com.pdm_proyecto.kolny.viewmodel.loginVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm_proyecto.kolny.data.dao.UsuarioDao
import com.pdm_proyecto.kolny.data.model.ResultadoAcceso
import com.pdm_proyecto.kolny.data.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val usuarioDao: UsuarioDao
) : ViewModel() {

    // Estado expuesto a la UI
    private val _estadoAcceso = MutableStateFlow<ResultadoAcceso>(ResultadoAcceso.Inactivo)
    val estadoAcceso: StateFlow<ResultadoAcceso> = _estadoAcceso

    fun verificarUsuarioPorCorreo(correo: String) {
        viewModelScope.launch {
            val usuario = usuarioDao.buscarUsuarioPorCorreo(correo)

            _estadoAcceso.value = when {
                usuario == null -> ResultadoAcceso.NoRegistrado
                !usuario.activo -> ResultadoAcceso.Inactivo
                usuario.rol == "Administrador" && usuario.tipoAdmin == "Desarrollador" -> ResultadoAcceso.AdminDesarrollador(usuario)
                usuario.rol == "Administrador" && usuario.tipoAdmin == "Directiva"     -> ResultadoAcceso.AdminDirectiva(usuario)
                usuario.rol == "Residente"     -> ResultadoAcceso.Residente(usuario)
                usuario.rol == "Vigilante"     -> ResultadoAcceso.Vigilante(usuario)
                else -> ResultadoAcceso.Desconocido
            }
        }
    }
}