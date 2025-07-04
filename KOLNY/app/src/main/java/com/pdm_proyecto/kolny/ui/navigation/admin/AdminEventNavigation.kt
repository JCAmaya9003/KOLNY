package com.pdm_proyecto.kolny.ui.navigation.admin

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.admin.SolicitudesEventosScreen
import com.pdm_proyecto.kolny.ui.screens.events.CreateEventScreen
import com.pdm_proyecto.kolny.ui.screens.events.EventScreen
import com.pdm_proyecto.kolny.viewmodels.EventViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import java.util.Date

fun NavGraphBuilder.AdminEventNavigationEntries(
    navController: NavHostController,
    eventViewModel: EventViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    val adminUsuario = Usuario(
        dui = "12345678-9",
        nombre = "ADMIN",
        telefono = "1234-5678",
        fechaNacimiento = Date(),
        casa = "Casa Admin",
        email = "admin@admin.admin",
        password = "root123",
        rol = "ADMIN"
    )

    composable(Route.Eventos.route) {
        EventScreen(
            rol = "ADMIN",
            navController = navController,
            viewModel = eventViewModel,
            onNavigateToCreate = {
                navController.navigate(Route.CreateEvent.route)
            }
        )
    }

    composable(Route.CreateEvent.route) 

        val loggedUser = usuarioViewModel.loggedUser.collectAsState().value
        if (loggedUser != null) {
            CreateEventScreen(
                rol = "ADMIN",
                navController = navController,
                viewModel = eventViewModel,
                onEventoGuardado = {
                    navController.popBackStack()
                },
                usuario = loggedUser
            )
        }

    }

    composable(Route.EventRequests.route) {
        SolicitudesEventosScreen(
            navController = navController,
            viewModel = eventViewModel,
            usuarioAdmin = adminUsuario.nombre // 👈 nuevo parámetro
        )
    }
}
