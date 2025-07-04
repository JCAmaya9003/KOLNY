package com.pdm_proyecto.kolny.ui.navigation.admin

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.admin.AdminScreen
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import com.pdm_proyecto.kolny.viewmodels.VisitaViewModel

@Composable
fun AdminNavigation(navController: NavHostController) {
    val usuarioViewModel: UsuarioViewModel = hiltViewModel()
    val visitaViewModel: VisitaViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Route.AdminHome.route) {
        composable(Route.AdminHome.route) {
            AdminScreen(navController)
        }

        AdminUserNavigationEntries(
            navController = navController,
            usuarioViewModel = usuarioViewModel
        )

        AdminVisitNavigationEntries(
            navController = navController,
            visitaViewModel = visitaViewModel
        )
    }
}