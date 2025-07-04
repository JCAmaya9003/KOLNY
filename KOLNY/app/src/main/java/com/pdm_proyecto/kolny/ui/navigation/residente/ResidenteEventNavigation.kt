package com.pdm_proyecto.kolny.ui.navigation.residente

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.events.CreateEventScreen
import com.pdm_proyecto.kolny.ui.screens.events.EventScreen
import com.pdm_proyecto.kolny.viewmodels.EventViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import java.util.Date

fun NavGraphBuilder.ResidenteEventoNavigationEntries(
    navController: NavHostController,
    eventViewModel: EventViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    composable(Route.Eventos.route) {
        EventScreen(
            viewModel = eventViewModel,
            navController = navController,
            rol = "RESIDENTE",
            onNavigateToCreate = { navController.navigate(Route.CreateEvent.route) }
        )
    }

    composable(Route.CreateEvent.route) {
        val loggedUsuario = usuarioViewModel.loggedUser.collectAsState().value
        if (loggedUsuario != null) {
            CreateEventScreen(
                viewModel = eventViewModel,
                navController = navController,
                rol = "RESIDENTE",
                onEventoGuardado = { navController.popBackStack() },
                usuario = loggedUsuario

            )
        }
    }
}