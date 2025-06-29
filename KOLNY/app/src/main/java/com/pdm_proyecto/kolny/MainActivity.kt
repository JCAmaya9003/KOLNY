package com.pdm_proyecto.kolny

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pdm_proyecto.kolny.ui.navigation.Routes
import com.pdm_proyecto.kolny.ui.screens.admin.AdminAddUserScreen
import com.pdm_proyecto.kolny.ui.screens.admin.EventScreen
import com.pdm_proyecto.kolny.ui.screens.admin.SolicitudesEventosScreen
import com.pdm_proyecto.kolny.ui.screens.events.CreateEventScreen
import com.pdm_proyecto.kolny.ui.theme.KOLNYTheme
import com.pdm_proyecto.kolny.viewmodels.EventViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val usuarioViewModel: UsuarioViewModel by viewModels {
            UsuarioViewModelFactory()
        }
        enableEdgeToEdge()
        setContent {
            KOLNYTheme {
                val navController = rememberNavController()
                val eventViewModel: EventViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = Routes.EVENT_SCREEN
                ) {
                    composable(Routes.EVENT_SCREEN) {
                        EventScreen(
                            esAdmin = true,
                            viewModel = eventViewModel,
                            navController = navController,
                            onNavigateToCreate = {
                                navController.navigate(Routes.CREATE_EVENT_SCREEN)
                            }
                        )
                    }

                    composable(Routes.CREATE_EVENT_SCREEN) {
                        CreateEventScreen(
                            esAdmin = true,
                            viewModel = eventViewModel,
                            navController = navController,
                            onEventoGuardado = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(Routes.USERS_SCREEN) {
                        AdminAddUserScreen(
                            usuarioViewModel = usuarioViewModel,
                            navController = navController
                        )
                    }

                    composable(Routes.EVENT_REQUESTS_SCREEN) {
                        SolicitudesEventosScreen(
                            navController = navController,
                            viewModel = eventViewModel
                        )
                    }


                }
            }
        }
    }
}


