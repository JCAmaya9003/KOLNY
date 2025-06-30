package com.pdm_proyecto.kolny.ui.screens.visit

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
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
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = { KolnyTopBar(rol = rol, navController = navController) },
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
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