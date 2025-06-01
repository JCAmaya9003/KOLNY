package com.pdm_proyecto.kolny

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pdm_proyecto.kolny.screens.login.LoginScreen
import com.pdm_proyecto.kolny.ui.theme.KOLNYTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KOLNYTheme {
                LoginScreen(
                    onLoginSuccess = {
                        // Aquí luego redirigiremos por rol
                    }
                )
            }
        }
    }
}
