package com.pdm_proyecto.kolny.ui.navigation.admin

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.admin.AdminAddUserScreen
import com.pdm_proyecto.kolny.ui.screens.admin.AdminUserScreen
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel

fun NavGraphBuilder.AdminUserNavigationEntries(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel
) {
    composable(Route.GestionUsers.route) {
        AdminUserScreen(
            navController = navController,
            viewModel = usuarioViewModel
        )
    }

    composable(Route.AdminAddUser.route) {
        AdminAddUserScreen(
            navController = navController,
            usuarioViewModel = usuarioViewModel
        )
    }

}