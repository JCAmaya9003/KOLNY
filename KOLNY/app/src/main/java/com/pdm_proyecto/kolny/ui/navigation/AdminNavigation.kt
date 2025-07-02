package com.pdm_proyecto.kolny.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.ui.screens.admin.AdminScreen
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


fun NavGraphBuilder.AdminNavigation(navController: NavHostController, usuarioViewModel: UsuarioViewModel) {
    //val usuarioViewModel: UsuarioViewModel = hiltViewModel()

    composable("adminScreen") {
        AdminScreen(navController)
    }

    AdminUserNavigationEntries(
        navController = navController,
        usuarioViewModel = usuarioViewModel
        )
}
