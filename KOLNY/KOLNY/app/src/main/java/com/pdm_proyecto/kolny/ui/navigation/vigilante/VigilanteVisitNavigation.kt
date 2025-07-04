package com.pdm_proyecto.kolny.ui.navigation.vigilante

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.visit.AddVisitScreen
import com.pdm_proyecto.kolny.ui.screens.visit.EditVisitScreen
import com.pdm_proyecto.kolny.ui.screens.visit.VisitScreen
import com.pdm_proyecto.kolny.viewmodels.VisitaViewModel

fun NavGraphBuilder.VigilanteVisitNavigationEntries(
    visitaViewModel: VisitaViewModel,
    navController: NavHostController
){
    composable(Route.Visitas.route) {
        VisitScreen(
            rol = "VIGILANTE",
            viewModel = visitaViewModel,
            navController = navController,
            onAddVisit = { navController.navigate(Route.AddVisita.route) },
            onEditVisit = {
                visitaViewModel.selectVisita(it)
                navController.navigate(Route.EditVisita.route)
            }
        )
    }

    composable(Route.AddVisita.route) {
        AddVisitScreen(
            rol = "VIGILANTE",
            viewModel = visitaViewModel,
            navController = navController
        )
    }

    composable(Route.EditVisita.route) {
        val visita = visitaViewModel.selectedVisita.collectAsState().value
        if (visita != null) {
            EditVisitScreen(
                rol = "VIGILANTE",
                viewModel = visitaViewModel,
                navController = navController,
                visita = visita,
                onDone = {
                    visitaViewModel.clearSelectedVisita()
                    navController.popBackStack()
                }
            )
        } else {
            Log.d("EditVisitScreen", "no hay visita seleccionada")
        }
    }
}