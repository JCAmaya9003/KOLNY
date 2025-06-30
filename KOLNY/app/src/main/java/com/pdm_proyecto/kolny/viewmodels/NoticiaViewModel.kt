package com.pdm_proyecto.kolny.viewmodels

import androidx.lifecycle.ViewModel
import com.pdm_proyecto.kolny.data.models.Noticia
import com.pdm_proyecto.kolny.data.repository.NoticiaRepository
import kotlinx.coroutines.flow.StateFlow

class NoticiaViewModel : ViewModel() {
    private val repository = NoticiaRepository()
    val noticias: StateFlow<List<Noticia>> = repository.noticias

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
}
