// BORRAR CUANDO ESTE EL DAO
// SOLO ES TEMPORAL

package com.pdm_proyecto.kolny.data.repository

import com.pdm_proyecto.kolny.data.models.Noticia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date
import javax.inject.Inject

class NoticiaRepository @Inject constructor() {
    private val _noticias = MutableStateFlow<List<Noticia>>(emptyList())
    val noticias: StateFlow<List<Noticia>> = _noticias
    private var autoIncrementId = 1

    init {
        _noticias.value = listOf(
            Noticia(
                idnoticia = autoIncrementId++,
                titulo = "Mantenimiento de áreas verdes",
                contenido = "Se informa a los residentes que el jueves se realizará mantenimiento en las áreas verdes comunes. Favor no dejar objetos en los jardines.",
                fechapublicacion = Date(),
                categoria = "Aviso",
                idautor = 1
            ),
            Noticia(
                idnoticia = autoIncrementId++,
                titulo = "Reunión de la directiva",
                contenido = "El sábado a las 10 a.m. en la casa comunal se realizará la reunión mensual de la directiva. ¡Participa!",
                fechapublicacion = Date(),
                categoria = "Comunidad",
                idautor = 2
            ),
            Noticia(
                idnoticia = autoIncrementId++,
                titulo = "Falla de energía programada",
                contenido = "La compañía eléctrica ha notificado un corte programado para el martes de 9 a 11 a.m. Prevé con tiempo.",
                fechapublicacion = Date(),
                categoria = "Servicio",
                idautor = 3
            )
        )
    }

    fun addNoticia(noticia: Noticia) {
        val noticiaConId = noticia.copy(
            idnoticia = autoIncrementId++,
            fechapublicacion = Date()
        )
        _noticias.value = _noticias.value + noticiaConId
    }
}
