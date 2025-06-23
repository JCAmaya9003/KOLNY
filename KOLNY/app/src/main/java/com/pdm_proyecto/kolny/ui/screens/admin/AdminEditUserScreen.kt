package com.pdm_proyecto.kolny.ui.screens.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import com.pdm_proyecto.kolny.ui.components.admin.UserForm

@Composable
fun AdminEditUserScreen(
    viewModel: UsuarioViewModel,
    navController: NavHostController,
    usuario: Usuario,
    onDone: () -> Unit)
{

    Scaffold(
        topBar = { KolnyTopBar(rol = "ADMIN", navController = navController) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 16.dp)
                .fillMaxSize(),
        ) {
            UserForm(
                usuarioViewModel = viewModel,
                initialData = usuario,
                onSubmitSuccess = { onDone() }
            )
        }
    }
}

