package com.pdm_proyecto.kolny.ui.navigation

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.screens.visit.AddVisitScreen
import com.pdm_proyecto.kolny.ui.screens.visit.EditVisitScreen
import com.pdm_proyecto.kolny.ui.screens.visit.VisitScreen
import com.pdm_proyecto.kolny.viewmodels.VisitaViewModel

fun NavGraphBuilder.VisitNavigationEntries(
    navController: NavHostController,
    rol: String,
    visitViewModel: VisitaViewModel
) {
    composable("visitScreen") {
        VisitScreen(
            rol = rol,
            viewModel = visitViewModel,
            navController = navController,
            onAddVisit = { navController.navigate("addVisitScreen") },
            onEditVisit = {
                visitViewModel.selectVisita(it)
                navController.navigate("editVisitScreen")
            }
        )
    }

    composable("addVisitScreen") {
        AddVisitScreen(
            rol = rol,
            viewModel = visitViewModel,
            navController = navController
        )
    }

    composable("editVisitScreen") {
        val visita = visitViewModel.selectedVisita.collectAsState().value
        if (visita != null) {
            EditVisitScreen(
                rol = rol,
                viewModel = visitViewModel,
                navController = navController,
                visita = visita,
                onDone = {
                    visitViewModel.clearSelectedVisita()
                    navController.popBackStack()
                }
            )
        } else {
            Log.d("EditVisitScreen", "no hay visita seleccionada")
        }
    }
}