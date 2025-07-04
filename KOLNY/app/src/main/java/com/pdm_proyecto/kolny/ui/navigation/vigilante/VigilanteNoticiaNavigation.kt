package com.pdm_proyecto.kolny.ui.navigation.vigilante

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.noticias.DetalleNoticiaScreen
import com.pdm_proyecto.kolny.ui.screens.noticias.NoticiasScreen
import com.pdm_proyecto.kolny.viewmodels.NoticiaViewModel
import java.util.Date

fun NavGraphBuilder.VigilanteNoticiaNavigationEntries(
    navController: NavHostController,
    noticiaViewModel: NoticiaViewModel
) {
    composable(Route.Noticias.route) {
        NoticiasScreen(
            navController = navController,
            noticiaViewModel = noticiaViewModel,
            rol = "VIGILANTE",
            onViewNoticia = {
                noticiaViewModel.selectNoticia(it)
                navController.navigate(Route.ViewNoticia.route)
            }
        )
    }

    composable(Route.ViewNoticia.route) {
        val noticia = noticiaViewModel.selectedNoticia.collectAsState().value
        if (noticia != null) {
            DetalleNoticiaScreen(
                noticia = noticia,
                noticiaViewModel = noticiaViewModel,
                navController = navController,
                rol = "VIGILANTE",
                usuarioLogueado = Usuario(
                    dui = "12345678-2",
                    nombre = "VIGILANTE",
                    telefono = "1234-5678",
                    fechaNacimiento = Date(),
                    casa = "casa",
                    email = "email@email.com",
                    password = "password",
                    rol = "VIGILANTE"
                ),
                onDone = {
                    noticiaViewModel.clearSelectedNoticia()
                    navController.popBackStack()
                }
            )
        }
    }
}