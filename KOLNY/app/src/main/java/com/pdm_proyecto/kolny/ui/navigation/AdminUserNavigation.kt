package com.pdm_proyecto.kolny.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.screens.admin.AdminAddUserScreen
import com.pdm_proyecto.kolny.ui.screens.admin.AdminEditUserScreen
import com.pdm_proyecto.kolny.ui.screens.admin.AdminUserScreen
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel

// NAVEGACIÓN SOLO PARA MOVERSE ENTRE PANTALLAS DE CRUD DE USUARIO
@Composable
fun AdminUserNavigation(navController: NavHostController) {
    val usuarioViewModel: UsuarioViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "adminUserScreen"
    ) {
        composable("adminUserScreen") {
            AdminUserScreen(
                viewModel = usuarioViewModel,
                navController = navController,
                onAddUser = { navController.navigate("adminAddUser") },
                onEditUser = {
                    usuarioViewModel.selectUsuario(it)
                    navController.navigate("adminEditUser")
                }
            )
        }

        composable("adminAddUser") {
            AdminAddUserScreen(viewModel = usuarioViewModel, navController = navController)
        }

        composable("adminEditUser") {
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
}
