package com.pdm_proyecto.kolny.viewmodels

import androidx.lifecycle.ViewModel
import com.pdm_proyecto.kolny.data.models.Noticia
import com.pdm_proyecto.kolny.data.models.Comentario
import com.pdm_proyecto.kolny.data.repository.NoticiaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class NoticiaViewModel : ViewModel() {
    private val repository = NoticiaRepository()
    val noticias: StateFlow<List<Noticia>> = repository.noticias

    // --------- Comentarios ----------
    private val _comentarios = MutableStateFlow<List<Comentario>>(emptyList())
    val comentarios: StateFlow<List<Comentario>> = _comentarios

    // Puedes inicializar con comentarios de ejemplo si quieres:
    init {
        _comentarios.value = listOf(
            Comentario(
                idcomentario = 1,
                idnoticia = 1,
                idautor = 123,
                contenido = "¡Excelente noticia!",
                fechacomentario = Date(System.currentTimeMillis() - 20 * 60 * 1000)
            )
        )
    }

    fun agregarNoticia(titulo: String, contenido: String, categoria: String, idautor: Int) {
        repository.addNoticia(
            Noticia(
                titulo = titulo,
                contenido = contenido,
                categoria = categoria,
                idautor = idautor
            )
        )
    }

    fun agregarComentario(idnoticia: Int, idautor: Int, contenido: String) {
        val nuevo = Comentario(
            idcomentario = (_comentarios.value.maxOfOrNull { it.idcomentario } ?: 0) + 1,
            idnoticia = idnoticia,
            idautor = idautor,
            contenido = contenido,
            fechacomentario = Date()
        )
        _comentarios.value = _comentarios.value + nuevo
    }

    fun comentariosDeNoticia(idnoticia: Int): List<Comentario> =
        _comentarios.value.filter { it.idnoticia == idnoticia }
}
