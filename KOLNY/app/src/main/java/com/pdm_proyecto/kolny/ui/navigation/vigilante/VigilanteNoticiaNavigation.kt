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
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import java.util.Date

fun NavGraphBuilder.VigilanteNoticiaNavigationEntries(
    navController: NavHostController,
    noticiaViewModel: NoticiaViewModel,
    usuarioViewModel: UsuarioViewModel
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
        val loggedUsuario = usuarioViewModel.loggedUser.collectAsState().value
        if (noticia != null && loggedUsuario != null) {
            DetalleNoticiaScreen(
                noticia = noticia,
                noticiaViewModel = noticiaViewModel,
                navController = navController,
                rol = "VIGILANTE",
                usuarioLogueado = loggedUsuario,
            )
        }
    }
}