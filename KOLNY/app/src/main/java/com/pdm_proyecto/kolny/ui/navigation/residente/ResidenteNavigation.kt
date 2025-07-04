package com.pdm_proyecto.kolny.ui.navigation.residente

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.viewmodels.NoticiaViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel

@Composable
fun ResidenteNavigation(
    navController: NavHostController,
) {
    val noticiaViewModel: NoticiaViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Route.Noticias.route) {
        ResidenteNoticiaNavigationEntries(
            navController = navController,
            noticiaViewModel = noticiaViewModel
        )
    }
}