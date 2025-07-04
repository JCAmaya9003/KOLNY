package com.pdm_proyecto.kolny.ui.screens.noticias

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import com.pdm_proyecto.kolny.viewmodels.NoticiaViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm_proyecto.kolny.data.models.Usuario

@Composable
fun NoticiaFormScreen(
    navController: NavHostController,
    noticiaViewModel: NoticiaViewModel,
    rol: String,
    usuarioLogueado: Usuario
) {
    Scaffold(
        topBar = { KolnyTopBar(rol = rol, navController = navController) }
    ) { innerPadding ->
        var titulo by remember { mutableStateOf("") }
        var contenido by remember { mutableStateOf("") }
        var categoria by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Nueva Noticia", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = contenido,
                onValueChange = { contenido = it },
                label = { Text("Contenido") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    noticiaViewModel.agregarNoticia(
                        titulo = titulo,
                        contenido = contenido,
                        categoria = categoria,
                        idautor = usuarioLogueado.dui// puedes poner aquí el usuario logueado
                    )
                    navController.popBackStack() // regresa a la lista
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = titulo.isNotBlank() && contenido.isNotBlank() && categoria.isNotBlank()
            ) {
                Text("Publicar noticia")
            }
        }
    }
}
