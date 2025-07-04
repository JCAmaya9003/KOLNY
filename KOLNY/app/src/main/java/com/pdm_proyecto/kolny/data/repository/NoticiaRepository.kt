package com.pdm_proyecto.kolny.data.repository

import com.pdm_proyecto.kolny.data.models.Noticia
import com.pdm_proyecto.kolny.data.models.NoticiaDB
import com.pdm_proyecto.kolny.data.models.toNoticia
import com.pdm_proyecto.kolny.data.models.toNoticiaDB
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject
import javax.inject.Singleton
import java.util.Date

@Singleton
class NoticiaRepository @Inject constructor(
    private val supabase: SupabaseClient
) {

    suspend fun getAllNoticias(): List<Noticia> = runCatching {
        val dbResult = supabase.from("noticias")
            .select()
            .decodeList<NoticiaDB>()
        println("🟢 getAllNoticias: Leídas de Supabase: $dbResult")
        dbResult.map { it.toNoticia() }
    }.getOrElse {
        println("🔴 Error en getAllNoticias: ${it.message}")
        it.printStackTrace()
        emptyList()
    }

    suspend fun addNoticia(noticia: Noticia): Boolean = runCatching {
        val noticiaDB = noticia.toNoticiaDB()
        println("🟡 Intentando insertar noticia en Supabase: $noticiaDB")
        val resp = supabase.from("noticias").insert(noticiaDB)
        println("🟢 Respuesta de insert: $resp")
        true
    }.getOrElse {
        println("🔴 Error insertando noticia: ${it.message}")
        it.printStackTrace()
        false
    }

    /** Inicializa con datos demo si la tabla está vacía */
    suspend fun inicializarDatosDemo() {
        println("🔵 Llamando a inicializarDatosDemo()")
        val existentes = getAllNoticias()
        println("🔵 Noticias existentes al iniciar demo: $existentes")
        if (existentes.isNotEmpty()) {
            println("🟤 Ya existen noticias, omito insertar demo.")
            return
        }

        val demo = listOf(
            Noticia(
                idnoticia = 0,
                titulo = "Mantenimiento de áreas verdes",
                contenido = "Se informa a los residentes que el jueves se realizará mantenimiento en las áreas verdes comunes. Favor no dejar objetos en los jardines.",
                fechapublicacion = Date(),
                categoria = "Aviso",
                idautor = "11111111-1"
            ),
            Noticia(
                idnoticia = 0,
                titulo = "Reunión de la directiva",
                contenido = "El sábado a las 10 a.m. en la casa comunal se realizará la reunión mensual de la directiva. ¡Participa!",
                fechapublicacion = Date(),
                categoria = "Comunidad",
                idautor = "22222222-2"
            ),
            Noticia(
                idnoticia = 0,
                titulo = "Falla de energía programada",
                contenido = "La compañía eléctrica ha notificado un corte programado para el martes de 9 a 11 a.m. Prevé con tiempo.",
                fechapublicacion = Date(),
                categoria = "Servicio",
                idautor = "33333333-3"
            )
        )
        demo.forEach {
            println("🟡 Insertando noticia demo: $it")
            val ok = addNoticia(it)
            println("🟢 ¿Insert demo exitosa? $ok")
        }
    }
}
