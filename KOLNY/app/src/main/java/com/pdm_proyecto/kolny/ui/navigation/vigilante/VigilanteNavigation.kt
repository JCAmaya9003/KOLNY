package com.pdm_proyecto.kolny.ui.navigation.vigilante

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.viewmodels.EventViewModel

@Composable
fun VigilanteNavigation(navController: NavHostController, eventViewModel: EventViewModel) {
    NavHost(navController = navController, startDestination = Route.Eventos.route) {
        VigilanteNavigationEntries(
            navController = navController,
            eventViewModel = eventViewModel
        )
    }
}