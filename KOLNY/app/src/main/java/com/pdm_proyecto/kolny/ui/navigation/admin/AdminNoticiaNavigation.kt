package com.pdm_proyecto.kolny.ui.navigation.admin

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.ui.navigation.Route
import com.pdm_proyecto.kolny.ui.screens.noticias.DetalleNoticiaScreen
import com.pdm_proyecto.kolny.ui.screens.noticias.NoticiaFormScreen
import com.pdm_proyecto.kolny.ui.screens.noticias.NoticiasScreen
import com.pdm_proyecto.kolny.viewmodels.NoticiaViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import java.util.Date

fun NavGraphBuilder.AdminNoticiaNavigationEntries(
    navController: NavHostController,
    noticiaViewModel: NoticiaViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    composable(Route.Noticias.route) {
        NoticiasScreen(
            navController = navController,
            noticiaViewModel = noticiaViewModel,
            rol = "ADMIN",
            onViewNoticia = {
                noticiaViewModel.selectNoticia(it)
                navController.navigate(Route.ViewNoticia.route)
            }
        )
    }

    composable(Route.ViewNoticia.route) {
        val noticia = noticiaViewModel.selectedNoticia.collectAsState().value
        val loggedUser = usuarioViewModel.loggedUser.collectAsState().value
        if (noticia != null && loggedUser != null) {
            DetalleNoticiaScreen(
                noticia = noticia,
                noticiaViewModel = noticiaViewModel,
                navController = navController,
                rol = "ADMIN",
                usuarioLogueado = loggedUser
            )
        }
    }

    composable(Route.CreateNoticia.route) {
        val loggedUser = usuarioViewModel.loggedUser.collectAsState().value
        if (loggedUser != null) {
            NoticiaFormScreen(
                navController = navController,
                noticiaViewModel = noticiaViewModel,
                rol = "ADMIN",
                usuarioLogueado = loggedUser
            )
        }
    }
}