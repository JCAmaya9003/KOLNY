package com.pdm_proyecto.kolny.ui.navigation.residente

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.events.CreateEventScreen
import com.pdm_proyecto.kolny.ui.screens.events.EventScreen
import com.pdm_proyecto.kolny.viewmodels.EventViewModel
import java.util.Date

fun NavGraphBuilder.ResidenteEventoNavigationEntries(navController: NavHostController, eventViewModel: EventViewModel) {
    composable(Route.Eventos.route) {
        EventScreen(
            viewModel = eventViewModel,
            navController = navController,
            rol = "RESIDENTE",
            onNavigateToCreate = { navController.navigate(Route.CreateEvent.route) }
        )
    }

    composable(Route.CreateEvent.route) {
        CreateEventScreen(
            viewModel = eventViewModel,
            navController = navController,
            rol = "RESIDENTE",
            onEventoGuardado = { navController.popBackStack() },
            usuario = Usuario(
                dui = "12345678-1",
                nombre = "RESIDENTE",
                telefono = "1234-5678",
                fechaNacimiento = Date(),
                casa = "Casa Residente",
                email = "residente@residente.residente",
                password = "root123",
                rol = "RESIDENTE"
            )
        )
    }
}