package com.pdm_proyecto.kolny.ui.screens.events

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdm_proyecto.kolny.data.models.Evento
import com.pdm_proyecto.kolny.viewmodels.EventViewModel
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import java.util.*

@Composable
fun CreateEventScreen(
    esAdmin: Boolean = true,
    viewModel: EventViewModel,
    navController: NavController,
    onEventoGuardado: () -> Unit
) {
    val context = LocalContext.current
    val eventoSeleccionado = viewModel.eventoSeleccionado

    var titulo by remember { mutableStateOf(eventoSeleccionado?.titulo ?: "") }
    var descripcion by remember { mutableStateOf(eventoSeleccionado?.descripcion ?: "") }
    var lugar by remember { mutableStateOf(eventoSeleccionado?.lugar ?: "") }
    var fecha by remember { mutableStateOf(eventoSeleccionado?.fecha ?: "") }
    var horaInicio by remember { mutableStateOf(eventoSeleccionado?.horaInicio ?: "") }
    var horaFin by remember { mutableStateOf(eventoSeleccionado?.horaFin ?: "") }

    var mostrarDialogoError by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()

    fun showDatePicker(onFechaSelected: (String) -> Unit) {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                onFechaSelected(String.format("%04d-%02d-%02d", year, month + 1, day))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun showTimePicker(onHoraSelected: (String) -> Unit) {
        TimePickerDialog(
            context,
            { _: TimePicker, hour: Int, minute: Int ->
                onHoraSelected(String.format("%02d:%02d", hour, minute))
            },
            12, 0, true
        ).show()
    }

    Scaffold(
        topBar = {
            KolnyTopBar(rol = if (esAdmin) "ADMIN" else "USUARIO", navController = navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = lugar,
                onValueChange = { lugar = it },
                label = { Text("Lugar") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { showDatePicker { fecha = it } }) {
                    Text("Seleccionar fecha")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (fecha.isNotEmpty()) fecha else "Fecha no seleccionada")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { showTimePicker { horaInicio = it } }) {
                    Text("Hora inicio")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (horaInicio.isNotEmpty()) horaInicio else "No seleccionada")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { showTimePicker { horaFin = it } }) {
                    Text("Hora fin")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (horaFin.isNotEmpty()) horaFin else "No seleccionada")
            }

            Button(
                onClick = {
                    // Valida campos vacíos
                    if (titulo.isBlank() || descripcion.isBlank() || lugar.isBlank()
                        || fecha.isBlank() || horaInicio.isBlank() || horaFin.isBlank()
                    ) {
                        mensajeError = "Por favor, completa todos los campos antes de guardar."
                        mostrarDialogoError = true
                        return@Button
                    }

                    // Valida orden de horas
                    if (horaInicio >= horaFin) {
                        mensajeError = "La hora de inicio debe ser antes que la hora de fin."
                        mostrarDialogoError = true
                        return@Button
                    }

                    // Valida traslape
                    if (viewModel.existeTraslapeDeEvento(fecha, horaInicio, horaFin)) {
                        mensajeError = "Ya existe un evento en ese horario. Selecciona otra hora o fecha."
                        mostrarDialogoError = true
                        return@Button
                    }

                    // Crear evento
                    val evento = Evento(
                        titulo = titulo,
                        descripcion = descripcion,
                        lugar = lugar,
                        fecha = fecha,
                        horaInicio = horaInicio,
                        horaFin = horaFin,
                        creadoPor = if (esAdmin) "admin" else "usuario",
                        aprobado = esAdmin
                    )

                    if (esAdmin) {
                        if (eventoSeleccionado != null) {
                            viewModel.actualizarEvento(evento.copy(id = eventoSeleccionado.id))
                        } else {
                            viewModel.agregarEvento(evento)
                        }
                    } else {
                        viewModel.enviarSolicitud(evento)
                    }

                    viewModel.limpiarEventoSeleccionado()
                    onEventoGuardado()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(if (esAdmin) "Guardar" else "Enviar")
            }
        }

        if (mostrarDialogoError) {
            AlertDialog(
                onDismissRequest = { mostrarDialogoError = false },
                title = { Text("Error") },
                text = { Text(mensajeError) },
                confirmButton = {
                    Button(onClick = { mostrarDialogoError = false }) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}



