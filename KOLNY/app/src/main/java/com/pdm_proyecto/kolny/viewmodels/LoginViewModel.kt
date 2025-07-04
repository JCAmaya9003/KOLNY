package com.pdm_proyecto.kolny.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.pdm_proyecto.kolny.data.models.Usuario
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.pdm_proyecto.kolny.data.models.ResultadoAcceso
import com.pdm_proyecto.kolny.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    /*var email by mutableStateOf("")
    var password by mutableStateOf("")*/

    private val _estadoAcceso = MutableStateFlow<ResultadoAcceso>(ResultadoAcceso.Desconocido)
    val estadoAcceso: StateFlow<ResultadoAcceso> = _estadoAcceso

    fun loginConCredencialGoogle(cred: AuthCredential) = viewModelScope.launch {
        try {
            firebaseAuth.signInWithCredential(cred).await()
            val email = firebaseAuth.currentUser?.email
            Log.d("LOGIN", "Firebase email = $email")   // ← debería imprimirse
            _estadoAcceso.value = if (email != null)
                authRepo.loginWithGoogle(email)
            else ResultadoAcceso.Desconocido
        } catch (e: Exception) {
            Log.e("LOGIN", "signInWithCredential error", e)
            _estadoAcceso.value = ResultadoAcceso.Desconocido
        }
    }

    fun loginConEmail(correo: String, pass: String) = viewModelScope.launch {
        _estadoAcceso.value = authRepo.loginWithEmail(correo.lowercase(), pass)
    }

    /* ---------- mapeo cuando viene por Google ---------- */
    private fun mapearRol(u: Usuario?): ResultadoAcceso = when {
        u == null -> ResultadoAcceso.NoRegistrado
        !u.activo -> ResultadoAcceso.Inactivo
        u.rol == "Administrador" && u.tipoAdmin == "Desarrollador" -> ResultadoAcceso.AdminDesarrollador(u)
        u.rol == "Administrador" && u.tipoAdmin == "Directiva"     -> ResultadoAcceso.AdminDirectiva(u)
        u.rol == "Residente"   -> ResultadoAcceso.Residente(u)
        u.rol == "Vigilante"   -> ResultadoAcceso.Vigilante(u)
        else                   -> ResultadoAcceso.Desconocido
    }
}



