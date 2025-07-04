package com.pdm_proyecto.kolny.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pdm_proyecto.kolny.R
import com.pdm_proyecto.kolny.ui.navigation.Route

//SOLO PARA PROBAR
//luego mejorar lógica(para filtro de roles) y logos
//logos, cambiarlos en el drawable
@Composable
fun KolnyTopBar(
    rol: String,
    navController: NavHostController
) {
    Surface(
        color = Color(0xFFD0F1FF),
        shadowElevation = 4.dp,
    ) {

        val firebase = remember { com.google.firebase.auth.FirebaseAuth.getInstance() }

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                //esta va a ser la imagen del usuario
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Usuario",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                Text(
                    text = "KOLNY",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = {
                    /* 1. Firebase logout */
                    firebase.signOut()

                    /* 2. Navegar a login limpiando el backstack */
                    navController.navigate(Route.Login.route) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                })
                {
                    Icon(Icons.Default.Close, contentDescription= "Cerrar sesión")
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                when (rol) {
                    "ADMIN" -> {
                        IconButton(onClick = {
                            navController.navigate(Route.AdminRoot.route)
                        }) {
                            Icon(Icons.Default.Person, contentDescription = "Usuarios")
                        }
                    }
                    "VIGILANTE" -> {
                        IconButton(onClick = {
                            navController.navigate(Route.Visitas.route)
                        }) {
                            Icon(Icons.Default.Face, contentDescription = "Visitas")
                        }
                    }
                }
                IconButton(onClick = { navController.navigate(Route.Noticias.route) }) {
                    Icon(Icons.Default.Home, contentDescription = "Noticias")
                }

                IconButton(onClick = {
                    navController.navigate(Route.Eventos.route)
                }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Eventos")
                }
            }
        }
    }
}
