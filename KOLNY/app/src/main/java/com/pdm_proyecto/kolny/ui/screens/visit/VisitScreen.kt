package com.pdm_proyecto.kolny.ui.screens.visit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.data.models.Visita
import com.pdm_proyecto.kolny.viewmodels.VisitaViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm_proyecto.kolny.ui.components.CardInfo
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import com.pdm_proyecto.kolny.utils.formatDate

@Composable
fun VisitScreen(
    rol: String,
    viewModel: VisitaViewModel,
    navController: NavHostController,
    onAddVisit: () -> Unit = {},
    onEditVisit: (Visita) -> Unit = {}
) {
    val visitas by viewModel.visitas.collectAsState()

    Scaffold(
        topBar = { KolnyTopBar(rol = rol, navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Registro de visitas",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.Face, contentDescription = "Visitas")
            }
            if (rol === "VIGILANTE") {
                Button(
                    onClick = { onAddVisit() },
                ) {
                    Text("Agregar Visita")
                }
            }
            LazyColumn {
                items(visitas.size) { index ->
                    VisitCard(
                        visita = visitas[index],
                        onDelete = { viewModel.deleteVisita(visitas[index]) },
                        onEditVisit = { onEditVisit(visitas[index]) }
                    )
                }
            }
        }
    }
}

@Composable
fun VisitCard(
    visita: Visita,
    onDelete: () -> Unit = {},
    onEditVisit: (Visita) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
        ) {
            CardInfo(label = "Visitante:", value = visita.nombreVisita)
            Row(modifier = Modifier.fillMaxWidth()){
                CardInfo(
                    label = "DUI:",
                    value = visita.dui,
                    modifier = Modifier.weight(1f)
                )
                CardInfo(
                    label = "Fecha:",
                    value = formatDate(visita.fechaVisita),
                    modifier = Modifier.weight(1f)
                )
            }
            if (visita.placa != null) {
                CardInfo(label = "Placa:", value = visita.placa)
            }
            CardInfo(label = "Motivo:", value = visita.motivo)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(onClick = { onEditVisit(visita) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}