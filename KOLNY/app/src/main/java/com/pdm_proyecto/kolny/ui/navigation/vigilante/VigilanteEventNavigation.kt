package com.pdm_proyecto.kolny.ui.navigation.vigilante

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.events.EventScreen
import com.pdm_proyecto.kolny.viewmodels.EventViewModel

fun NavGraphBuilder.VigilanteEventoNavigationEntries(navController: NavHostController, eventViewModel: EventViewModel) {
    composable(Route.Eventos.route) {
        EventScreen(
            rol = "VIGILANTE",
            navController = navController,
            viewModel = eventViewModel,
            onNavigateToCreate = {}
        )
    }
}