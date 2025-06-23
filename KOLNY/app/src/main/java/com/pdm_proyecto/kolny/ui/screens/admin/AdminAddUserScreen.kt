package com.pdm_proyecto.kolny.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pdm_proyecto.kolny.data.repository.UsuarioRepository
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.ui.components.admin.UserForm

@Composable
fun AdminAddUserScreen(viewModel: UsuarioViewModel, navController: NavHostController) {

    Scaffold(
        topBar = { KolnyTopBar(rol = "ADMIN", navController = navController) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 16.dp)
                .fillMaxSize(),
        ){
            UserForm(
                usuarioViewModel = viewModel,
                onSubmitSuccess = { navController.popBackStack() }
            )
        }
    }
}

@Preview
@Composable
fun FormScreenPreview() {
    AdminAddUserScreen(viewModel = UsuarioViewModel(UsuarioRepository()), navController = NavHostController(LocalContext.current))
}