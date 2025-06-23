package com.pdm_proyecto.kolny.ui.screens.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
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
import com.pdm_proyecto.kolny.R
import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.utils.formatDate
import androidx.compose.material3.IconButton
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUserScreen(
    viewModel: UsuarioViewModel,
    navController: NavHostController,
    onAddUser: () -> Unit = {},
    onEditUser: (Usuario) -> Unit = {}
) {

    val usuarios by viewModel.usuarios.collectAsState()

    Scaffold (
        topBar = { KolnyTopBar(rol = "ADMIN", navController = navController) }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Registro de usuarios",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.Person, contentDescription = "Usuario")
            }
            Button(
                onClick = { onAddUser() },
            ) {
                Text("Agregar Usuario")
            }
            LazyColumn {
                items(usuarios.size) { index ->
                    UserCard(
                        usuario = usuarios[index],
                        onDelete = { viewModel.deleteUsuario(usuarios[index]) },
                        onEditUser = { onEditUser(usuarios[index]) }
                    )
                }
            }
        }
    }
}

@Composable
fun UserCard(
    usuario: Usuario,
    onDelete: () -> Unit = {},
    onEditUser: (Usuario) -> Unit = {}
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
        Column(
            modifier = Modifier
                .padding(4.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Usuario",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
            ) {
                UserInfo(label = "Nombre:", value = usuario.nombre)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    UserInfo(
                        label = "DUI:",
                        value = usuario.dui,
                        modifier = Modifier.weight(1f)
                    )
                    UserInfo(
                        label = "Número de casa:",
                        value = usuario.casa,
                        modifier = Modifier.weight(1.4f)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    UserInfo(
                        label = "Teléfono:",
                        value = usuario.telefono,
                        modifier = Modifier.weight(1f)
                    )
                    UserInfo(
                        label = "Fecha de nacimiento:",
                        value = formatDate(usuario.fechaNacimiento),
                        modifier = Modifier.weight(1.4f)
                    )
                }
                UserInfo(label = "Correo Electrónico:", value = usuario.email)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(onClick = { onEditUser(usuario) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDelete) {
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
fun UserInfo(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(4.dp)
    ) {
        Text(
            text = label,
            fontStyle = FontStyle.Italic,
            color = Color.Gray,
        )
        Text( text = value )
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Preview
@Composable
fun AdminUserPreview() {
    AdminUserScreen(viewModel = UsuarioViewModel(UsuarioRepository()), navController = NavHostController(LocalContext.current))
}