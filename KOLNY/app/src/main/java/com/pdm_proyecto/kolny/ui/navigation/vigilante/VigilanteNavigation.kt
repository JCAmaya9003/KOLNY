package com.pdm_proyecto.kolny.ui.navigation.vigilante

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.viewmodels.VisitaViewModel

@Composable
fun VigilanteNavigation(navController: NavHostController) {
    val visitaViewModel: VisitaViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Route.Visitas.route) {
        VigilanteVisitNavigationEntries(
            visitaViewModel = visitaViewModel,
            navController = navController
        )
    }
}