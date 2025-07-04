package com.pdm_proyecto.kolny.ui.screens.events

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import com.pdm_proyecto.kolny.viewmodels.EventViewModel
import com.pdm_proyecto.kolny.data.models.Evento
import com.pdm_proyecto.kolny.ui.navigation.Route
import java.util.*

@Composable
fun EventScreen(
    rol: String,
    viewModel: EventViewModel,
    navController: NavController,
    onNavigateToCreate: () -> Unit
) {
    var fechaSeleccionada by remember { mutableStateOf("") }
    val eventos = if (fechaSeleccionada.isNotEmpty())
        viewModel.obtenerEventosPorFecha(fechaSeleccionada)
    else
        viewModel.eventos

    val solicitudesPendientesCount = viewModel.solicitudes.size

    val contexto = LocalContext.current
    val calendario = Calendar.getInstance()

    val datePicker = DatePickerDialog(
        contexto,
        { _, year, month, dayOfMonth ->
            fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
        },
        calendario.get(Calendar.YEAR),
        calendario.get(Calendar.MONTH),
        calendario.get(Calendar.DAY_OF_MONTH)
    )

    var mostrarDialogo by remember { mutableStateOf(false) }
    var eventoAEliminar by remember { mutableStateOf<Evento?>(null) }

    Scaffold(
        topBar = {
            KolnyTopBar(rol = rol, navController = navController)
        },
        floatingActionButton = {
            if (rol == "VIGILANTE") {
                Spacer(modifier = Modifier.width(0.dp))
            } else {
                FloatingActionButton(onClick = onNavigateToCreate) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar evento")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { datePicker.show() }) {
                        Text("Seleccionar fecha")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (fechaSeleccionada.isNotEmpty()) fechaSeleccionada else "Todas las fechas")
                }

                if (rol == "ADMIN") {
                    Box {
                        IconButton(onClick = {
                            navController.navigate(Route.EventRequests.route)
                        }) {
                            Icon(
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = "Solicitudes"
                            )
                        }

                        if (solicitudesPendientesCount > 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 4.dp, end = 4.dp)
                                    .size(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Surface(
                                    shape = MaterialTheme.shapes.small,
                                    color = MaterialTheme.colorScheme.error,
                                    shadowElevation = 4.dp
                                ) {
                                    Text(
                                        text = solicitudesPendientesCount.toString(),
                                        color = MaterialTheme.colorScheme.onError,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(eventos) { evento ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Evento: ${evento.titulo}")
                            Text("Descripción: ${evento.descripcion}")
                            Text("Lugar: ${evento.lugar}")
                            Text("Fecha: ${evento.fecha}")
                            if (evento.horaInicio.isNotEmpty() && evento.horaFin.isNotEmpty()) {
                                Text("Hora: ${evento.horaInicio} - ${evento.horaFin}")
                            }

                            if (rol == "ADMIN") {
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    IconButton(onClick = {
                                        viewModel.seleccionarEvento(evento)
                                        onNavigateToCreate()
                                    }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                                    }
                                    IconButton(onClick = {
                                        eventoAEliminar = evento
                                        mostrarDialogo = true
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    if (mostrarDialogo && eventoAEliminar != null) {
        AlertDialog(
            onDismissRequest = {
                mostrarDialogo = false
                eventoAEliminar = null
            },
            title = { Text("¿Eliminar evento?") },
            text = {
                Text("¿Estás seguro de que deseas eliminar el evento \"${eventoAEliminar?.titulo}\"? Esta acción no se puede deshacer.")
            },
            confirmButton = {
                Button(onClick = {
                    eventoAEliminar?.let { viewModel.eliminarEvento(it.id) }
                    mostrarDialogo = false
                    eventoAEliminar = null
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                Button(onClick = {
                    mostrarDialogo = false
                    eventoAEliminar = null
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
