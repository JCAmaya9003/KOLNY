package com.pdm_proyecto.kolny

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.pdm_proyecto.kolny.ui.navigation.admin.AdminNavigation
import com.pdm_proyecto.kolny.ui.navigation.guard.GuardNavigation
import com.pdm_proyecto.kolny.ui.theme.KOLNYTheme
import dagger.hilt.android.AndroidEntryPoint


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
                AdminNavigation(navController = navController)
            }
        }
    }
}
