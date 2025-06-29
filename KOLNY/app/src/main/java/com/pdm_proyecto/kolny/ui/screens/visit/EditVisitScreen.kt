package com.pdm_proyecto.kolny.ui.screens.visit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.data.models.Visita
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import com.pdm_proyecto.kolny.ui.components.visit.VisitForm
import com.pdm_proyecto.kolny.viewmodels.VisitaViewModel

@Composable
fun EditVisitScreen(
    rol: String,
    viewModel: VisitaViewModel,
    navController: NavHostController,
    visita: Visita,
    onDone: () -> Unit
) {

    Scaffold(
        topBar = { KolnyTopBar(rol = rol, navController = navController) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 16.dp)
                .fillMaxSize(),
        ){
            VisitForm(
                visitaViewModel = viewModel,
                initialData = visita,
                onSubmitSuccess = { onDone() }
            )
        }
    }
}