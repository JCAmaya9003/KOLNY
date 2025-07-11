package com.pdm_proyecto.kolny.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm_proyecto.kolny.data.models.Noticia
import com.pdm_proyecto.kolny.data.models.Comentario
import com.pdm_proyecto.kolny.data.repository.NoticiaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoticiaViewModel @Inject constructor(
    private val repository: NoticiaRepository
): ViewModel() {

    private val _noticias = MutableStateFlow<List<Noticia>>(emptyList())
    val noticias: StateFlow<List<Noticia>> = _noticias

    private val _selectedNoticia = MutableStateFlow<Noticia?>(null)
    val selectedNoticia: StateFlow<Noticia?> = _selectedNoticia

    // --------- Comentarios ---------- (Se mantiene local, no Supabase)
    private val _comentarios = MutableStateFlow<List<Comentario>>(emptyList())
    val comentarios: StateFlow<List<Comentario>> = _comentarios

    init {
        // Inicializa la BD con ejemplos si quieres:
        viewModelScope.launch {
            repository.inicializarDatosDemo()
            loadNoticias()
        }
        // Comentarios de ejemplo (local)
        _comentarios.value = listOf(
            Comentario(
                idcomentario = 1,
                idnoticia = 1,
                idautor = "12345678-9",
                contenido = "¡Excelente noticia!",
                fechacomentario = Date(System.currentTimeMillis() - 20 * 60 * 1000)
            )
        )
    }

    fun loadNoticias() {
        viewModelScope.launch {
            _noticias.value = repository.getAllNoticias()
        }
    }

    fun selectNoticia(noticia: Noticia) {
        _selectedNoticia.value = noticia
    }

    fun clearSelectedNoticia() {
        _selectedNoticia.value = null
    }

    fun agregarNoticia(titulo: String, contenido: String, categoria: String, idautor: String) {
        viewModelScope.launch {
            repository.addNoticia(
                Noticia(
                    titulo = titulo,
                    contenido = contenido,
                    categoria = categoria,
                    idautor = idautor
                )
            )
            loadNoticias() // Actualiza la lista después de agregar
        }
    }

    fun agregarComentario(idnoticia: Int, idautor: String, contenido: String) {
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
