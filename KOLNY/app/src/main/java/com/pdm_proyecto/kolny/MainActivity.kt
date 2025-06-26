package com.pdm_proyecto.kolny

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.pdm_proyecto.kolny.ui.navigation.AdminUserNavigation
import com.pdm_proyecto.kolny.ui.screens.admin.AdminAddUserScreen
import com.pdm_proyecto.kolny.screens.login.LoginScreen
import com.pdm_proyecto.kolny.ui.theme.KOLNYTheme
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModelFactory

//ESTA ASÍ PARA PRUEBAS
//Navigation MUY básica
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KOLNYTheme {
                val navController = rememberNavController()
                AdminUserNavigation(navController = navController)
                LoginScreen(
                    onLoginSuccess = {
                        // Aquí luego redirigiremos por rol
                    }
                )
            }
        }
    }
}
