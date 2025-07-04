package com.pdm_proyecto.kolny.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.data.models.Evento
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import com.pdm_proyecto.kolny.ui.components.SolicitudCard
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.viewmodels.EventViewModel

@Composable
fun SolicitudesEventosScreen(
    navController: NavHostController,
    viewModel: EventViewModel,
    usuarioAdmin: String
) {
    val eventos by viewModel.eventos.collectAsState()

    val solicitudes = eventos.filter {
        it.creadoPor != usuarioAdmin
    }

    Scaffold(
        topBar = {
            KolnyTopBar(rol = "ADMIN", navController = navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Solicitudes de eventos",
                style = MaterialTheme.typography.titleLarge
            )

            if (solicitudes.isEmpty()) {
                Text(
                    text = "No hay solicitudes pendientes.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(solicitudes) { evento ->
                        SolicitudCard(
                            evento = evento,
                            onAceptar = {
                                viewModel.actualizarEvento(evento.copy(creadoPor = usuarioAdmin))
                            },
                            onRechazar = {
                                viewModel.eliminarEvento(evento.id)
                            },
                            onVerMas = {
                                viewModel.seleccionarEvento(evento)
                                navController.navigate(Route.CreateEvent.route)
                            }
                        )
                    }
                }
            }
        }
    }
}

