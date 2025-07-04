package com.pdm_proyecto.kolny.ui.navigation.admin

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.screens.admin.AdminAddUserScreen
import com.pdm_proyecto.kolny.ui.screens.admin.AdminEditUserScreen
import com.pdm_proyecto.kolny.ui.screens.admin.AdminUserScreen
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import androidx.navigation.NavGraphBuilder
import com.pdm_proyecto.kolny.ui.navigation.Route

//NAVEGACIÓN SOLO PARA MOVERSE ENTRE PANTALLAS DE CRUD DE USUARIO

fun NavGraphBuilder.AdminUserNavigationEntries(navController: NavHostController, usuarioViewModel: UsuarioViewModel) {
    composable(Route.GestionUsers.route) {
        AdminUserScreen(
            viewModel = usuarioViewModel,
            navController = navController,
            onAddUser = { navController.navigate(Route.AdminAddUser.route) },
            onEditUser = {
                usuarioViewModel.selectUsuario(it)
                navController.navigate(Route.AdminEditUser.route)
            }
        )
    }

    composable(Route.AdminAddUser.route) {
        AdminAddUserScreen(
            viewModel = usuarioViewModel,
            navController = navController
        )
    }

    composable(Route.AdminEditUser.route) {
        val usuario = usuarioViewModel.selectedUsuario.collectAsState().value
        if (usuario != null) {
            AdminEditUserScreen(
                viewModel = usuarioViewModel,
                navController = navController,
                usuario = usuario,
                onDone = {
                    usuarioViewModel.clearSelectedUsuario()
                    navController.popBackStack()
                }
            )
        } else {
            Log.d("AdminEditUserScreen", "no hay usuario seleccionado")
        }
    }
}


