package com.pdm_proyecto.kolny.data.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun NoticiaDB.toNoticia(): Noticia {
    val formato = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return Noticia(
        idnoticia = idnoticia,
        titulo = titulo,
        contenido = contenido,
        fechapublicacion = try { formato.parse(fechapublicacion) ?: Date() } catch (e: Exception) { Date() },
        categoria = categoria,
        idautor = autordui    // ¡Ojo! El nombre en NoticiaDB es autordui, en Noticia es idautor
    )
}

fun Noticia.toNoticiaDB(): NoticiaDB {
    val formato = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return NoticiaDB(
        idnoticia = idnoticia,
        titulo = titulo,
        contenido = contenido,
        fechapublicacion = formato.format(fechapublicacion),
        categoria = categoria,
        autordui = idautor    // ¡Ojo! El nombre en NoticiaDB es autordui, en Noticia es idautor
    )
}
