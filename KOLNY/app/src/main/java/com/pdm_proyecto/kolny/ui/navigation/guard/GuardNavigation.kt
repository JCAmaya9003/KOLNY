package com.pdm_proyecto.kolny.ui.navigation.guard

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pdm_proyecto.kolny.ui.navigation.VisitNavigationEntries
import com.pdm_proyecto.kolny.viewmodels.VisitaViewModel

@Composable
fun GuardNavigation(navController: NavHostController) {
    val visitViewModel: VisitaViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "visitScreen"){
        VisitNavigationEntries(
            navController = navController,
            rol = "VIGILANTE",
            visitViewModel = visitViewModel
        )
    }
}