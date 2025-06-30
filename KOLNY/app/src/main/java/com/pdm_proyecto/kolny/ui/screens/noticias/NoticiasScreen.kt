package com.pdm_proyecto.kolny.ui.screens.noticias

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import com.pdm_proyecto.kolny.viewmodels.NoticiaViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoticiasScreen(
    navController: NavHostController,
    noticiaViewModel: NoticiaViewModel,
    rol: String = "ADMIN"
){
    // Usa solo el ViewModel que recibes por parámetro
    val noticias by noticiaViewModel.noticias.collectAsState()

    Scaffold(
        topBar = { KolnyTopBar(rol = rol, navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título centrado con ícono a la izquierda
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Noticias y Publicaciones",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Noticias",
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón "Agregar Noticia"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = { navController.navigate("noticiaForm") },
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text("Agregar Noticia")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- LazyColumn con scroll ---
            Box(modifier = Modifier.weight(1f)) { // Esto permite que la lista tome el espacio restante y scrollee
                if (noticias.isEmpty()) {
                    Text("No hay publicaciones aún.")
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(noticias.reversed()) { noticia ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                elevation = CardDefaults.cardElevation(2.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(text = noticia.titulo, style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        text = noticia.categoria,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "Publicado: " + SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(noticia.fechapublicacion),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = noticia.contenido)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
