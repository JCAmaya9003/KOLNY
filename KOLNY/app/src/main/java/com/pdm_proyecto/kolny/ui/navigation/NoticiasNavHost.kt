package com.pdm_proyecto.kolny.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.pdm_proyecto.kolny.ui.screens.noticias.*
import com.pdm_proyecto.kolny.viewmodels.NoticiaViewModel

@Composable
fun NoticiasNavHost(navController: NavHostController) {
    val noticiaViewModel: NoticiaViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "noticias"
    ) {
        composable("noticias") {
            NoticiasScreen(
                navController = navController,
                noticiaViewModel = noticiaViewModel,
                rol = "ADMIN",
                onViewNoticia = {
                    noticiaViewModel.selectNoticia(it)
                    navController.navigate("detalleNoticia")
                }
            )
        }
        composable("noticiaForm") {
            NoticiaFormScreen(
                navController = navController,
                noticiaViewModel = noticiaViewModel
            )
        }
        composable("detalleNoticia") {
            val noticia = noticiaViewModel.selectedNoticia.collectAsState().value
            if (noticia != null) {
                DetalleNoticiaScreen(
                    noticia = noticia,
                    noticiaViewModel = noticiaViewModel,
                    navController = navController,
                    rol = "ADMIN",
                    onDone = {
                        noticiaViewModel.clearSelectedNoticia()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
