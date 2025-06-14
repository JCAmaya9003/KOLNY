package com.pdm_proyecto.kolny

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.pdm_proyecto.kolny.ui.screens.admin.AdminAddUserScreen
import com.pdm_proyecto.kolny.ui.theme.KOLNYTheme
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModelFactory

//ESTA ASÍ PARA PRUEBAS
//FALTA NAVIGATION
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val usuarioViewModel: UsuarioViewModel by viewModels {
            UsuarioViewModelFactory()
        }
        enableEdgeToEdge()
        setContent {
            KOLNYTheme {
                AdminAddUserScreen(usuarioViewModel = usuarioViewModel)
            }
        }
    }
}
