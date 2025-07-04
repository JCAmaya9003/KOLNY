package com.pdm_proyecto.kolny.data.repository

import com.pdm_proyecto.kolny.data.models.Evento
import com.pdm_proyecto.kolny.data.models.EventoDB
import com.pdm_proyecto.kolny.data.models.toEvento
import com.pdm_proyecto.kolny.data.models.toEventoDB
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class EventoRepository @Inject constructor(
    private val supabase: SupabaseClient
) {

    suspend fun getEventos(): List<Evento> {
        return try {
            supabase.from("eventos")
                .select()
                .decodeList<EventoDB>()
                .map { it.toEvento() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun enviarSolicitud(evento: Evento): Boolean {
        return try {
            val response = supabase.from("eventos")
                .insert(evento.toEventoDB()) {
                    select() // Esto hace que Supabase te devuelva el objeto insertado
                }

            println("✅ Evento insertado: $response")
            true
        } catch (e: Exception) {
            println("❌ Error al insertar evento: ${e.message}")
            false
        }
    }


    suspend fun eliminarEvento(eventoId: Int): Boolean {
        return try {
            supabase.from("eventos")
                .delete {
                    filter {
                        eq("idevento", eventoId)
                    }
                }
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun actualizarEvento(evento: Evento): Boolean {
        return try {
            supabase.from("eventos")
                .update(evento.toEventoDB()) {
                    filter {
                        eq("idevento", evento.id)
                    }
                }
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun obtenerEventosPorFecha(fecha: String): List<Evento> {
        return try {
            supabase.from("eventos")
                .select {
                    filter {
                        ilike("fechaevento", "$fecha%")
                    }
                }
                .decodeList<EventoDB>()
                .map { it.toEvento() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun existeTraslape(fecha: String, inicio: String, fin: String): Boolean {
        val eventosDelDia = obtenerEventosPorFecha(fecha)
        return eventosDelDia.any { evento ->
            val inicioConflicto = inicio < evento.horaFin
            val finConflicto = fin > evento.horaInicio
            inicioConflicto && finConflicto
        }
    }
}

