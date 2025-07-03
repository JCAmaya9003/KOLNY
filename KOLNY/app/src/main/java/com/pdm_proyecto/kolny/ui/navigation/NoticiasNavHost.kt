package com.pdm_proyecto.kolny.ui.navigation

import androidx.compose.runtime.Composable
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
    val noticiaViewModel: NoticiaViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "noticias"
    ) {
        composable("noticias") {
            NoticiasScreen(
                navController = navController,
                noticiaViewModel = noticiaViewModel,
                rol = "ADMIN"
            )
        }
        composable("noticiaForm") {
            NoticiaFormScreen(
                navController = navController,
                noticiaViewModel = noticiaViewModel
            )
        }
        composable(
            "detalleNoticia/{idnoticia}",
            arguments = listOf(navArgument("idnoticia") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("idnoticia") ?: 0
            val noticia = noticiaViewModel.noticias.value.find { it.idnoticia == id }
            if (noticia != null) {
                DetalleNoticiaScreen(
                    noticia = noticia,
                    noticiaViewModel = noticiaViewModel,
                    navController = navController,
                    rol = "ADMIN"
                )
            }
        }
    }
}
