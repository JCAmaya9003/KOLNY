package com.pdm_proyecto.kolny.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pdm_proyecto.kolny.data.repository.UsuarioRepository
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm_proyecto.kolny.R
import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.ui.utils.formatDate
import androidx.compose.material3.IconButton
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUserScreen(viewModel: UsuarioViewModel) {

    val usuarios by viewModel.usuarios.collectAsState()

    Scaffold (
        topBar = { KolnyTopBar(rol = "ADMIN") }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    /*ir a la screen para agregar usuarios*/
                },
                modifier = Modifier
                    .padding(8.dp),
            ) {
                Text("Agregar usuarios")
            }
            LazyColumn {
                items(usuarios.size) { index ->
                    UserCard(usuario = usuarios[index])
                }
            }
        }
    }
}

@Composable
fun UserCard(
    usuario: Usuario
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            //esta va a ser la imagen del usuario
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Usuario",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                UserInfoRow(label = "Nombre:", value = usuario.nombre)
                UserInfoRow(label = "Teléfono:", value = usuario.telefono)
                UserInfoRow(label = "Fecha de\nnacimiento:", value = formatDate(usuario.fechaNacimiento))
                UserInfoRow(label = "Correo\nElectrónico:", value = usuario.email)
                UserInfoRow(label = "DUI:", value = usuario.dui)
                UserInfoRow(label = "Número de\ncasa:", value = usuario.casa)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(onClick = { /*ir a la screen de editar*/ }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = { /*eliminar*/ } ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun UserInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            fontStyle = FontStyle.Italic,
            color = Color.Gray,
            modifier = Modifier.width(80.dp)
        )
        Text( text = value )
    }
}

@Preview
@Composable
fun AdminUserPreview() {
    AdminUserScreen(viewModel = UsuarioViewModel(UsuarioRepository()))
}