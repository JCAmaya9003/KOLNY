package com.pdm_proyecto.kolny.ui.navigation.residente

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.viewmodels.EventViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.events.EventScreen


@Composable
fun ResidenteNavigation(navController: NavHostController, eventViewModel: EventViewModel) {
    NavHost(navController, startDestination = Route.Eventos.route) {
        ResidenteNavigationEntries(
            navController = navController,
            eventViewModel = eventViewModel
        )
    }
}