package com.pdm_proyecto.kolny.ui.navigation.admin

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.navigation.VisitNavigationEntries
import com.pdm_proyecto.kolny.ui.screens.admin.AdminScreen
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import com.pdm_proyecto.kolny.viewmodels.VisitaViewModel

@Composable
fun AdminNavigation(navController: NavHostController) {
    val usuarioViewModel: UsuarioViewModel = hiltViewModel()
    val visitaViewModel: VisitaViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "adminScreen") {
        composable("adminScreen") {
            AdminScreen(navController)
        }

        AdminUserNavigationEntries(
            navController = navController,
            usuarioViewModel = usuarioViewModel
        )

        VisitNavigationEntries(
            navController = navController,
            rol = "ADMIN",
            visitViewModel = visitaViewModel
        )
    }
}