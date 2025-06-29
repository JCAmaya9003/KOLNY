package com.pdm_proyecto.kolny.ui.screens.admin

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.data.repository.UsuarioRepository
import com.pdm_proyecto.kolny.ui.components.DatePickerInput
import com.pdm_proyecto.kolny.ui.components.FormInput
import com.pdm_proyecto.kolny.ui.components.KolnyTopBar
import com.pdm_proyecto.kolny.viewmodels.FormViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AdminAddUserScreen(
    usuarioViewModel: UsuarioViewModel,
    navController: NavController
) {

    //para cuando usen el formViewModel, solo cambien la key
    val formViewModel: FormViewModel = viewModel(key = "screen_admin_add_user")
    val usuarios by usuarioViewModel.usuarios.collectAsState()

    Scaffold(
        topBar = { KolnyTopBar(rol = "ADMIN", navController = navController) },
        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 16.dp)
                .fillMaxSize(),
        ){
            LazyColumn {
                item {
                    FormInput(
                        fieldKey = "nombre",
                        label = "Nombre del residente:",
                        placeHolder = "ej. Jacobo Perez",
                        viewModel = formViewModel
                    )
                }
                item {
                    FormInput(
                        fieldKey = "telefono",
                        label = "Numero de telefono:",
                        placeHolder = "+503335432301",
                        viewModel = formViewModel,
                        inputType = KeyboardType.Number,
                        isFormatted = true
                    )
                }
                item {
                    DatePickerInput(
                        fieldKey = "fechaNacimiento",
                        label = "Fecha de Nacimiento:",
                        placeHolder = "dd/mm/YYYY",
                        viewModel = formViewModel
                    )
                }
                item {
                    FormInput(
                        fieldKey = "correo",
                        label = "Correo Electronico:",
                        placeHolder = "example@gmail.com",
                        viewModel = formViewModel,
                        inputType = KeyboardType.Email
                    )
                }
                item {
                    FormInput(
                        fieldKey = "dui",
                        label = "DUI:",
                        placeHolder = "12345678-9",
                        viewModel = formViewModel,
                        inputType = KeyboardType.Number,
                        isFormatted = true
                    )
                }
                item {
                    FormInput(
                        fieldKey = "password",
                        label = "Contraseña:",
                        placeHolder = "root1234",
                        viewModel = formViewModel,
                        inputType = KeyboardType.Password,
                        isPassword = true
                    )
                }
                item {
                    FormInput(
                        fieldKey = "casa",
                        label = "Numero de casa:",
                        placeHolder = "ej. 46D",
                        viewModel = formViewModel
                    )
                }
                item {
                    Button(onClick = {
                        val fields = listOf(
                            "nombre",
                            "telefono",
                            "fechaNacimiento",
                            "correo",
                            "dui",
                            "password",
                            "casa"
                        )
                        val isValid = formViewModel.validate(fields = fields)
                        Log.d("FORMULARIO", "¿Formulario válido? $isValid")

                        if (isValid) {
                            val dui = formViewModel.formattedTextFields["dui"]?.text ?: return@Button
                            val nombre = formViewModel.textFields["nombre"] ?: return@Button
                            val telefono = formViewModel.formattedTextFields["telefono"]?.text ?: return@Button
                            val fechaNacimiento = formViewModel.dateFields["fechaNacimiento"] ?: return@Button
                            val casa = formViewModel.textFields["casa"] ?: return@Button
                            val correo = formViewModel.textFields["correo"] ?: return@Button
                            val password = formViewModel.textFields["password"] ?: return@Button

                            val usuario = Usuario(
                                dui = dui,
                                nombre = nombre,
                                telefono = telefono,
                                fechaNacimiento = fechaNacimiento,
                                casa = casa,
                                email = correo,
                                password = password
                            )
                            usuarioViewModel.addUsuario(usuario)
                            formViewModel.clearAllFields()
                        }
                    }) {
                        Text(text = "Guardar Usuario")
                    }
                }
                items(usuarios.size) { index ->
                    Text(text = usuarios[index].nombre)
                }
            }
        }
    }
}

@Preview
@Composable
fun FormScreenPreview() {
    val fakeNavController = rememberNavController() // Esto solo funciona en previews simples
    AdminAddUserScreen(
        usuarioViewModel = UsuarioViewModel(UsuarioRepository()),
        navController = fakeNavController
    )
}