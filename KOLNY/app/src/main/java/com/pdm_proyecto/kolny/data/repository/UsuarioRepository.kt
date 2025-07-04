//BORRAR CUANDO ESTE EL DAO
//SOLO ES TEMPORAL

package com.pdm_proyecto.kolny.data.repository

import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.data.models.UsuarioDB
import com.pdm_proyecto.kolny.data.models.toUsuarioDB
import com.pdm_proyecto.kolny.utils.createDate
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Array.set
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepository @Inject constructor(
    private val supabase: SupabaseClient
) {

    /* ---------- UTILIDADES ---------- */
    private fun UsuarioDB.toDomain(): Usuario = Usuario(
        dui = dui,
        nombre = nombre,
        telefono = telefono ?: "",
        fechaNacimiento = fecha_nacimiento,        // agrega tu columna real
        casa = "",                       // idem
        email = correo,
        password = "",                       // nunca expongas hash
        activo = activo,
        rol = when (idrol) {
            1 -> "RESIDENTE"
            2 -> "VIGILANTE"
            3 -> "ADMIN"
            else -> "DESCONOCIDO"
        },
        tipoAdmin = null                      // se carga aparte si hace falta
    )

    /* ---------- CRUD ---------- */

    suspend fun getAll(): List<Usuario> = withContext(Dispatchers.IO) {
        supabase.from("usuarios")
            .select {
                filter { eq("activo", true) }
            }
            .decodeList<UsuarioDB>()
            .map { it.toDomain() }//cambiarlo, para que guarden Usuarios y salga la casa
    }

    suspend fun add(/*u: UsuarioDB*/ u: Usuario): List<Usuario> = withContext(Dispatchers.IO) {

        val usuarioDB = u.toUsuarioDB()

        supabase.from("usuarios").insert(usuarioDB)

        if(u.rol == "RESIDENTE"){
            supabase.from("residentes").insert(
                mapOf(
                    "residentedui" to u.dui,
                    "numerocasa" to u.casa
                )
            )
        }else {
            supabase.from("vigilantes").insert(
                mapOf(
                "vigilantedui" to u.dui
            ))
        }
        getAll()
    }

    suspend fun update(/*u: UsuarioDB*/ u: Usuario): List<Usuario> = withContext(Dispatchers.IO) {
        val usuarioDB = u.toUsuarioDB()
        supabase.from("usuarios")
            .update(u) { filter { eq("dui", usuarioDB.dui) } }

        if(u.rol == "RESIDENTE"){
            supabase.from("residentes")
                .update(
                    mapOf(
                        "numerocasa" to u.casa
                    )
                ) { filter { eq("dui", u.dui) } }
        }
        getAll()//revisarlo por la serializacion
    }

    /** “Eliminar” = poner activo = false */
    suspend fun deactivate(dui: String): List<Usuario> = withContext(Dispatchers.IO) {
        supabase.from("usuarios")
            .update(
                mapOf("activo" to false)
            ) {
                filter { eq("dui", dui) }
            }
        getAll()
    }
}