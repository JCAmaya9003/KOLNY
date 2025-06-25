package com.pdm_proyecto.kolny.ui.components.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.ui.components.DatePickerInput
import com.pdm_proyecto.kolny.ui.components.FormInput
import com.pdm_proyecto.kolny.ui.components.ImagePickerInput

import com.pdm_proyecto.kolny.viewmodels.FormViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel

@Composable
fun UserForm(
    usuarioViewModel: UsuarioViewModel,
    initialData: Usuario? = null,
    onSubmitSuccess: () -> Unit = {}
) {
    val isEditMode = initialData != null
    val formViewModel: FormViewModel = viewModel(
        //para cuando usen el formViewModel, solo cambien la key
        key = if (isEditMode) "screen_admin_edit_user" else "screen_admin_add_user"
    )

    LaunchedEffect(initialData) {
        initialData?.let { usuario ->
            formViewModel.setInitialImageUri("fotoPerfil", usuario.fotoPerfil)
            formViewModel.setInitialTextValue("nombre", usuario.nombre)
            formViewModel.setInitialFormattedValue("telefono", TextFieldValue(usuario.telefono))
            formViewModel.setInitialDateValue("fechaNacimiento", usuario.fechaNacimiento)
            formViewModel.setInitialTextValue("correo", usuario.email)
            formViewModel.setInitialFormattedValue("dui", TextFieldValue(usuario.dui))
            formViewModel.setInitialTextValue("password", "")
            formViewModel.setInitialTextValue("casa", usuario.casa)
        }
    }


    LazyColumn {
        item {
            ImagePickerInput(
                fieldKey = "fotoPerfil",
                viewModel = formViewModel,
                defaultContent = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Ícono de perfil",
                        modifier = Modifier
                            .size(40.dp)
                    )
                },
                shape = CircleShape,
                showPreview = true
            )
        }
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
                    label = if (isEditMode) "Crear nueva contraseña:" else "Contraseña:",
                    placeHolder = if (isEditMode) "(dejar en blanco si no es necesario)" else "root1234",
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
                val requiredFields = mutableListOf("nombre", "telefono", "fechaNacimiento", "correo", "dui", "casa")

                var optionalFields = emptyList<String>()

                if (isEditMode) {
                    optionalFields = listOf("password")
                } else {
                    requiredFields += "password"
                }

                val isValid = formViewModel.validate(fields = requiredFields + optionalFields, optionalFields = optionalFields)

                if (isValid) {
                    val usuario = Usuario(
                        fotoPerfil = formViewModel.imageFields["fotoPerfil"],
                        dui = formViewModel.formattedTextFields["dui"]?.text ?: return@Button,
                        nombre = formViewModel.textFields["nombre"] ?: return@Button,
                        telefono = formViewModel.formattedTextFields["telefono"]?.text ?: return@Button,
                        fechaNacimiento = formViewModel.dateFields["fechaNacimiento"] ?: return@Button,
                        email = formViewModel.textFields["correo"] ?: return@Button,
                        casa = formViewModel.textFields["casa"] ?: return@Button,
                        password = formViewModel.textFields["password"]
                            ?.takeIf { it.isNotBlank() }
                            ?: initialData?.password
                            ?: return@Button
                    )

                    if (isEditMode) {
                        usuarioViewModel.editUsuario(usuario)
                    } else {
                        usuarioViewModel.addUsuario(usuario)
                    }

                    formViewModel.clearAllFields()
                    onSubmitSuccess()
                }
            }) {
                Text(if (isEditMode) "Actualizar Usuario" else "Guardar Usuario")
            }
        }
    }
}