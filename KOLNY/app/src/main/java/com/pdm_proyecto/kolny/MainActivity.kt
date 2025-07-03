package com.pdm_proyecto.kolny

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.pdm_proyecto.kolny.ui.navigation.AdminUserNavigation
import com.pdm_proyecto.kolny.ui.screens.admin.AdminAddUserScreen
import com.pdm_proyecto.kolny.ui.theme.KOLNYTheme
import dagger.hilt.android.AndroidEntryPoint
import com.pdm_proyecto.kolny.ui.navigation.NoticiasNavHost


//ESTA ASÍ PARA PRUEBAS
//Navigation MUY básica
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KOLNYTheme {
                val navController = rememberNavController()
                NoticiasNavHost(navController = navController)
            }
        }
    }
}
