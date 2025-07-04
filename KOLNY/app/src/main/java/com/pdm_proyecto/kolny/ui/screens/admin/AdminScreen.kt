package com.pdm_proyecto.kolny.ui.screens.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar

import com.pdm_proyecto.kolny.ui.navigation.Route

@Composable
fun AdminScreen(navController: NavHostController) {

    Scaffold(
        topBar = { KolnyTopBar("ADMIN", navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                navController.navigate("admin_gestion_usuarios")
                navController.navigate(Route.GestionUsers.route)
            }) {
                Text(text = "Administrar usuarios")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                navController.navigate("admin_reportes")
                navController.navigate(Route.Visitas.route)
            }) {
                Text(text = "Administrar visitas")
            }
        }
    }
}