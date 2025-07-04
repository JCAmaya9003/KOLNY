package com.pdm_proyecto.kolny.ui.navigation.admin

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.admin.AdminScreen
import com.pdm_proyecto.kolny.viewmodels.EventViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import com.pdm_proyecto.kolny.viewmodels.VisitaViewModel

fun NavGraphBuilder.AdminNavigation(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    visitaViewModel: VisitaViewModel
    ) {

    composable(Route.AdminRoot.route) {
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