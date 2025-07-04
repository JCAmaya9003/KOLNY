package com.pdm_proyecto.kolny.ui.navigation.admin

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

fun NavGraphBuilder.AdminVisitNavigationEntries(
    navController: NavHostController,
    visitaViewModel: VisitaViewModel
){
    composable(Route.Visitas.route) {
        VisitScreen(
            rol = "ADMIN",
            viewModel = visitaViewModel,
            navController = navController,
            onAddVisit = {},
            onEditVisit = {
                visitaViewModel.selectVisita(it)
                navController.navigate(Route.EditVisita.route)
            }
        )
    }

    composable(Route.EditVisita.route) {
        val visita = visitaViewModel.selectedVisita.collectAsState().value
        if (visita != null) {
            EditVisitScreen(
                rol = "ADMIN",
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