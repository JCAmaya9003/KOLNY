package com.pdm_proyecto.kolny.ui.screens.admin

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.ui.components.admin.UserForm

@Composable
fun AdminAddUserScreen(viewModel: UsuarioViewModel, navController: NavHostController) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = { KolnyTopBar(rol = "ADMIN", navController = navController) },
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
            UserForm(
                usuarioViewModel = viewModel,
                onSubmitSuccess = { navController.popBackStack() }
            )
        }
    }
}

