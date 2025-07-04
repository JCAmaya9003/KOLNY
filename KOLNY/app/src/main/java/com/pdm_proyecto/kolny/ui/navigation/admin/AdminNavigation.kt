package com.pdm_proyecto.kolny.ui.navigation.admin

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.viewmodels.NoticiaViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import androidx.navigation.compose.NavHost


@Composable
fun AdminNaivgation(
    navController: NavHostController,
) {
    val usuarioViewModel: UsuarioViewModel = hiltViewModel()
    val noticiaViewModel: NoticiaViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Route.GestionUsers.route) {
        AdminUserNavigationEntries(
            navController = navController,
            usuarioViewModel = usuarioViewModel
        )

        AdminNoticiaNavigationEntries(
            navController = navController,
            noticiaViewModel = noticiaViewModel
        )
    }
}