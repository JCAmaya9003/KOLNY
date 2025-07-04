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
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import com.pdm_proyecto.kolny.viewmodels.EventViewModel
import com.pdm_proyecto.kolny.data.models.Evento
import com.pdm_proyecto.kolny.ui.navigation.Route
import java.util.*

@Composable
fun EventScreen(
    rol: String,
    viewModel: EventViewModel,
    navController: NavHostController,
    onNavigateToCreate: () -> Unit
) {
    val eventos by viewModel.eventos.collectAsState()

    var fechaSeleccionada by remember { mutableStateOf("") }
    var eventosFiltrados by remember { mutableStateOf<List<Evento>>(emptyList()) }
    val todosLosEventos by viewModel.eventos.collectAsState()

    LaunchedEffect(fechaSeleccionada, todosLosEventos) {
        if (fechaSeleccionada.isNotEmpty()) {
            viewModel.obtenerEventosPorFecha(fechaSeleccionada) {
                eventosFiltrados = it
            }
        } else {
            eventosFiltrados = todosLosEventos
        }
    }

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
            if (rol != "VIGILANTE") {
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
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Button(onClick = { datePicker.show() }) {
                        Text("Seleccionar fecha")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { fechaSeleccionada = "" }) {
                        Text("Todas las fechas")
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = if (fechaSeleccionada.isNotEmpty()) fechaSeleccionada else "Todas las fechas")

                if (rol == "ADMIN") {
                    IconButton(onClick = {
                        navController.navigate(Route.EventRequests.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.MailOutline,
                            contentDescription = "Solicitudes"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(eventosFiltrados) { evento ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
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
