package com.pdm_proyecto.kolny.ui.navigation

// ui/navigation/AdminGraph.kt

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.pdm_proyecto.kolny.ui.screens.admin.*
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel

fun NavGraphBuilder.adminGraph(
    rootNav: NavHostController,
    userVM: UsuarioViewModel,
) {
    navigation(
        startDestination = Route.AdminHome.route,   // "admin_home"
        route = Route.AdminRoot.route               // "admin_root"
    ) {

        composable(Route.AdminHome.route) {
            AdminScreen(rootNav)
        }

        composable(Route.GestionUsers.route) {
            AdminUserScreen(
                viewModel = userVM,
                navController = rootNav,
                onAddUser = { rootNav.navigate(Route.AdminAdd.route) },
                onEditUser = {
                    userVM.selectUsuario(it)
                    rootNav.navigate(Route.AdminEdit.route)
                }
            )
        }

        composable(Route.AdminAdd.route) {
            AdminAddUserScreen(
                viewModel = userVM,
                navController = rootNav
            )
        }

        composable(Route.AdminEdit.route) {
            userVM.selectedUsuario.collectAsState().value?.let { u ->
                AdminEditUserScreen(
                    viewModel = userVM,
                    navController = rootNav,
                    usuario = u,
                    onDone = {
                        userVM.clearSelectedUsuario()
                        rootNav.popBackStack()
                    }
                )
            }
        }
    }
}
