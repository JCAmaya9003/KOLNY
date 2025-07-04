package com.pdm_proyecto.kolny.ui.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdm_proyecto.kolny.data.models.ResultadoAcceso
import com.pdm_proyecto.kolny.ui.navigation.admin.AdminNavigation
import com.pdm_proyecto.kolny.ui.navigation.residente.ResidenteNavigation
import com.pdm_proyecto.kolny.ui.navigation.vigilante.VigilanteNavigation
import com.pdm_proyecto.kolny.ui.screens.login.LoginScreen
import com.pdm_proyecto.kolny.viewmodels.EventViewModel
import com.pdm_proyecto.kolny.viewmodels.NoticiaViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import com.pdm_proyecto.kolny.viewmodels.VisitaViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(navController: NavHostController) {

    val ctx = LocalContext.current

    val usuarioViewModel: UsuarioViewModel = hiltViewModel()
    val visitaViewModel: VisitaViewModel = hiltViewModel()
    val eventViewModel: EventViewModel = hiltViewModel()
    val noticiaViewModel: NoticiaViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Route.Login.route
    ) {

        /* ------------- LOGIN ------------- */
        composable(Route.Login.route) {
            LoginScreen(
                //navController = navController,
                onLoginSuccess = { res ->
                    when (res) {
                        is ResultadoAcceso.AdminDesarrollador,
                        is ResultadoAcceso.AdminDirectiva -> {
                            navController.navigate(Route.AdminRoot.route) {
                                popUpTo(Route.Login.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                        /*is ResultadoAcceso.Vigilante -> {
                            navController.navigate(Route.VigilanteRoot.route) {
                                popUpTo(Route.Login.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                        is ResultadoAcceso.Residente -> {
                            navController.navigate(Route.ResidenteRoot.route) {
                                popUpTo(Route.Login.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }*/
                        is ResultadoAcceso.Vigilante,
                        is ResultadoAcceso.Residente -> {
                            Toast.makeText(ctx, "Módulo en construcción", Toast.LENGTH_LONG).show()
                        }
                        ResultadoAcceso.NoRegistrado -> toast(ctx, "Correo no registrado")
                        ResultadoAcceso.Inactivo    -> toast(ctx, "Cuenta inactiva")
                        ResultadoAcceso.Desconocido -> toast(ctx, "Error desconocido")
                        is ResultadoAcceso.ErrorBd -> TODO()
                    }
                },
                usuarioViewModel = usuarioViewModel
            )
        }

        /* ----------- SUB-GRÁFICOS ----------- */
        AdminNavigation(
            navController = navController,
            usuarioViewModel = usuarioViewModel,
            visitaViewModel = visitaViewModel,
            eventViewModel = eventViewModel,
            noticiaViewModel = noticiaViewModel
        )

        VigilanteNavigation(
            navController = navController,
            visitaViewModel = visitaViewModel,
            noticiaViewModel = noticiaViewModel,
            eventViewModel = eventViewModel,
            usuarioViewModel = usuarioViewModel
        )

        ResidenteNavigation(
            navController = navController,
            noticiaViewModel = noticiaViewModel,
            eventViewModel = eventViewModel,
            usuarioViewModel = usuarioViewModel
        )
    }
}

/* Helper Toast */
private fun toast(ctx: android.content.Context, msg: String) =
    Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()