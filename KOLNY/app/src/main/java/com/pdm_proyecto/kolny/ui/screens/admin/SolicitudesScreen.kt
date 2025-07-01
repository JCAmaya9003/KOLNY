package com.pdm_proyecto.kolny.ui.screens.admin


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import com.pdm_proyecto.kolny.viewmodels.EventViewModel

@Composable
fun SolicitudesScreen(
    viewModel: EventViewModel,
    navController: NavController,
    onNavigateToCreate: () -> Unit
) {
    val solicitudes = viewModel.obtenerSolicitudes()

    Scaffold(
        topBar = { KolnyTopBar(rol = "ADMIN", navController = navController) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            items(solicitudes) { solicitud ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            viewModel.seleccionarEvento(solicitud)
                            onNavigateToCreate()
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📅 ${solicitud.titulo}", style = MaterialTheme.typography.titleLarge)
                        Text("📍 ${solicitud.lugar}")
                        Text("🕒 ${solicitud.fecha} — ${solicitud.horaInicio}")
                        Text("👤 ${solicitud.creadoPor}")

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = {
                                viewModel.seleccionarEvento(solicitud)
                                onNavigateToCreate()
                            }) {
                                Text("Aceptar")
                            }
                            Button(onClick = {
                                viewModel.eliminarSolicitud(solicitud.id)
                            }) {
                                Text("Rechazar")
                            }
                        }
                    }
                }
            }
        }
    }
}
