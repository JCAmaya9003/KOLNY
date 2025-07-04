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
import com.pdm_proyecto.kolny.ui.navigation.admin.AdminNavigation
import com.pdm_proyecto.kolny.ui.navigation.residente.ResidenteNavigation
import com.pdm_proyecto.kolny.ui.navigation.vigilante.VigilanteNavigation
import com.pdm_proyecto.kolny.ui.screens.admin.AdminAddUserScreen
import com.pdm_proyecto.kolny.ui.screens.events.EventScreen
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

                /*AdminNavigation(
                    navController = navController,
                    usuarioViewModel = usuarioViewModel,
                    eventViewModel = eventViewModel
                )*/

                /*ResidenteNavigation(
                    navController = navController,
                    eventViewModel = eventViewModel
                )*/

                VigilanteNavigation(
                    navController = navController,
                    eventViewModel = eventViewModel
                )
            }
        }
    }
}


