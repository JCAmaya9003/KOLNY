package com.pdm_proyecto.kolny.ui.navigation.admin

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.admin.AdminScreen
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel


fun NavGraphBuilder.AdminNavigation(navController: NavHostController, usuarioViewModel: UsuarioViewModel) {

    composable(Route.AdminRoot.route) {
        AdminScreen(navController)
    }

    AdminUserNavigationEntries(
        navController = navController,
        usuarioViewModel = usuarioViewModel
    )
}

