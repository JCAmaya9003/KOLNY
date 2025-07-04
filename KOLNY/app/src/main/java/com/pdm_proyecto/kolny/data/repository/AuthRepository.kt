package com.pdm_proyecto.kolny.data.repository

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.pdm_proyecto.kolny.data.models.*
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.tasks.await
import org.mindrot.jbcrypt.BCrypt
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val supabase: SupabaseClient,
    private val firebase: FirebaseAuth
) {

    /* ------------------  GOOGLE ------------------ */
    suspend fun loginWithGoogle(email: String): ResultadoAcceso = runCatching {
        val row = supabase
            .from("usuarios")
            .select { filter { eq("correo", email.lowercase()) }; single() }
            .decodeAs<UsuarioDB>()                 // OBJETO, no lista

        val (rol, tipoAdmin) = resolverRol(row)
        mapearResultado(row.toUsuario(rol, tipoAdmin))
    }.getOrElse {
        Log.e("LOGIN", "Supabase error Google", it)
        ResultadoAcceso.NoRegistrado
    }

    /* ------------------  EMAIL + PASS ------------------ */
    suspend fun loginWithEmail(email: String, plainPass: String): ResultadoAcceso =
        runCatching {
            Log.d("EMAIL", "/ PASS + $plainPass + EMAIL $email")

            val row = try {
                supabase.from("usuarios")
                    .select {
                        filter { eq("correo", email.lowercase()) }
                        single()                           // ← fuerza objeto
                    }
                    .decodeAs<UsuarioDB>()                 // ← OBJETO, no lista
            } catch (e: Exception) {
                Log.d("LOGIN", "No hay usuario con ese correo → ${e.localizedMessage}")
                return ResultadoAcceso.NoRegistrado
            }

            /* Validaciones */
            /*if (!row.activo)                      return ResultadoAcceso.Inactivo
            if (!BCrypt.checkpw(plainPass, row.password))
                return ResultadoAcceso.NoRegistrado*/
            val passValida = when {
                row.password.startsWith("$2") -> {
                    val ok = BCrypt.checkpw(plainPass, row.password)
                    Log.d("LOGIN", "BCrypt ok? $ok")
                    ok
                }
                else -> {
                    val ok = row.password == plainPass
                    Log.d("LOGIN", "Texto-plano ok? $ok  (DB='${row.password}')")
                    Log.d("FECHA NACIMIENTO", "fecha nac: ${row.fecha_nacimiento}')")
                    ok
                }
            }
            if (!passValida) return ResultadoAcceso.NoRegistrado

            /* Rol & mapping */
            val (rol, tipoAdmin) = resolverRol(row)
            Log.d("LOGIN", "TIPO ADMIN: $tipoAdmin + ROL: $rol")
            mapearResultado(row.toUsuario(rol, tipoAdmin))
        }.getOrElse { ResultadoAcceso.NoRegistrado }

    /* ------------ helpers privados ------------ */

    private suspend fun resolverRol(row: UsuarioDB): Pair<String, String?> = when (row.idrol) {
        1 -> "Residente"   to null
        2 -> "Vigilante"   to null
        3 -> "Administrador" to obtenerTipoAdmin(row.dui)
        else -> "Desconocido" to null
    }

    /** lee tabla `administradores` y devuelve "Desarrollador", "Directiva" o null */
    private suspend fun obtenerTipoAdmin(dui: String): String? = runCatching {
        supabase
            .from("administradores")
            .select {
                filter { eq("administradordui", dui) }
                single()                              // ← devuelve objeto JSON
            }
            .decodeAs<AdministradorDb>()              // ← ahora sí encuentra serializer
            .idtipoadmin
    }.getOrNull()                                     // null si no hay fila o error
        ?.let {
            when (it) {
                1 -> "Desarrollador"
                2 -> "Directiva"
                else -> null
            }
        }
    //poner un log para verificar que se  este haciendo bien, ya que la funcion del email cambio,
    // YA LO CAMBIE, LA CLAVE ES EL DECODEAS

    private fun UsuarioDB.toUsuario(rol: String, tipoAdmin: String?) = Usuario(
        dui       = dui,
        nombre    = nombre,
        telefono  = telefono ?: "",
        fechaNacimiento = Date(),   // pon la real si la añades
        casa      = "",
        email     = correo,
        password  = "",
        activo    = true,
        rol       = rol,
        tipoAdmin = tipoAdmin
    )

    private fun mapearResultado(u: Usuario): ResultadoAcceso = when (u.rol) {
        "Residente"     -> ResultadoAcceso.Residente(u)
        "Vigilante"     -> ResultadoAcceso.Vigilante(u)
        "Administrador" -> when (u.tipoAdmin) {
            "Desarrollador" -> ResultadoAcceso.AdminDesarrollador(u)
            "Directiva"     -> ResultadoAcceso.AdminDirectiva(u)
            else            -> ResultadoAcceso.Desconocido
        }
        else            -> ResultadoAcceso.Desconocido
    }
}
