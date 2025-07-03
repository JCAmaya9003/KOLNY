package com.pdm_proyecto.kolny.ui.screens.noticias

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.data.models.Noticia
import com.pdm_proyecto.kolny.viewmodels.NoticiaViewModel
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DetalleNoticiaScreen(
    noticia: Noticia,
    noticiaViewModel: NoticiaViewModel,
    navController: NavHostController,
    rol: String = "ADMIN",
    idautorActual: Int = 123 // Simula el usuario logueado
) {
    val comentarios by noticiaViewModel.comentarios.collectAsState()
    var nuevoComentario by remember { mutableStateOf("") }
    val listaComentarios = comentarios.filter { it.idnoticia == noticia.idnoticia }

    Scaffold(
        topBar = { KolnyTopBar(rol = rol, navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 16.dp)
                .fillMaxSize()
        ) {
            // Noticia principal
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(noticia.titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(noticia.contenido)
                    Text(
                        "Publicado: " + SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(noticia.fechapublicacion),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }

            Divider()

            Text(
                text = "Comentarios (${listaComentarios.size}):",
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
            if (listaComentarios.isEmpty()) {
                Text(
                    "Sin comentarios aún.",
                    modifier = Modifier.padding(start = 16.dp)
                )
            } else {
                listaComentarios.forEach { comentario ->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF323232))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color.DarkGray)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Usuario #${comentario.idautor}", fontWeight = FontWeight.Bold, color = Color.White)
                                Text(comentario.contenido, color = Color.White)
                                Text(
                                    "Hace ${obtenerTiempo(comentario.fechacomentario)}",
                                    fontSize = 11.sp,
                                    color = Color.LightGray
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Divider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                OutlinedTextField(
                    value = nuevoComentario,
                    onValueChange = { nuevoComentario = it },
                    placeholder = { Text("Escribe un comentario...") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (nuevoComentario.isNotBlank()) {
                            noticiaViewModel.agregarComentario(
                                idnoticia = noticia.idnoticia,
                                idautor = idautorActual,
                                contenido = nuevoComentario
                            )
                            nuevoComentario = ""
                        }
                    },
                    enabled = nuevoComentario.isNotBlank()
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Enviar")
                }
            }
        }
    }
}

fun obtenerTiempo(fecha: Date): String {
    val ahora = Date()
    val diferencia = ahora.time - fecha.time
    val minutos = diferencia / 60000
    val horas = minutos / 60
    return when {
        horas > 0 -> "Hace $horas hora(s)"
        minutos > 0 -> "Hace $minutos minuto(s)"
        else -> "Ahora"
    }
}
