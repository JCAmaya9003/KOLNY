package com.pdm_proyecto.kolny.ui.navigation.admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.admin.AdminUserScreen
import com.pdm_proyecto.kolny.ui.screens.events.EventScreen
import com.pdm_proyecto.kolny.viewmodels.EventViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel

//por ahora así
@Composable
fun AdminNavigation(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    eventViewModel: EventViewModel
) {

    NavHost(navController = navController, startDestination = Route.GestionUsers.route) {
        AdminUserNavigationEntries(
            navController = navController,
            usuarioViewModel = usuarioViewModel
        )

        AdminEventNavigationEntries(
            navController = navController,
            eventViewModel = eventViewModel
        )
    }
}