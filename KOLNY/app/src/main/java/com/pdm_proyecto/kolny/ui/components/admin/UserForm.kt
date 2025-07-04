package com.pdm_proyecto.kolny.ui.components.admin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm_proyecto.kolny.data.models.Usuario
import com.pdm_proyecto.kolny.data.models.toUsuarioDB
import com.pdm_proyecto.kolny.ui.components.DatePickerInput
import com.pdm_proyecto.kolny.ui.components.FormInput
import com.pdm_proyecto.kolny.ui.components.ImagePickerInput
import com.pdm_proyecto.kolny.viewmodels.FormViewModel
import com.pdm_proyecto.kolny.viewmodels.UsuarioViewModel
import kotlinx.coroutines.launch
import kotlin.text.isNotBlank

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
    var selectedRol by remember { mutableStateOf("RESIDENTE") }

    LaunchedEffect(initialData) {
        initialData?.let { usuario ->
            formViewModel.setInitialImageUri("fotoPerfil", usuario.fotoPerfil)
            formViewModel.setInitialTextValue("nombre", TextFieldValue(usuario.nombre))
            formViewModel.setInitialFormattedValue("telefono", TextFieldValue(usuario.telefono))
            formViewModel.setInitialDateValue("fechaNacimiento", usuario.fechaNacimiento)
            formViewModel.setInitialTextValue("correo", TextFieldValue(usuario.email))
            formViewModel.setInitialFormattedValue("dui", TextFieldValue(usuario.dui))
            formViewModel.setInitialTextValue("password", TextFieldValue())
            usuario.casa?.let { formViewModel.setInitialTextValue("casa", TextFieldValue(it)) }
        }
    }

    val nombreFocusRequester = remember { FocusRequester() }
    val datePickerFocusRequester = remember { FocusRequester() }

    val roles = listOf("RESIDENTE", "VIGILANTE")
    var expanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            )
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                ImagePickerInput(
                    fieldKey = "fotoPerfil",
                    viewModel = formViewModel,
                    defaultContent = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Ícono de perfil",
                            modifier = Modifier.size(48.dp)
                        )
                    },
                    shape = CircleShape,
                    showPreview = true,
                    onImeAction = {
                        nombreFocusRequester.requestFocus()
                    }
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { expanded = true },
                    shape = CircleShape,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("Rol: $selectedRol")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    roles.forEach { rol ->
                        DropdownMenuItem(
                            text = { Text(rol) },
                            onClick = {
                                selectedRol = rol
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        item {
            FormInput(
                fieldKey = "nombre",
                label = "Nombre completo:",
                placeHolder = "ej. Jacobo Perez",
                viewModel = formViewModel,
                inputCapitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next,
                modifier = Modifier.focusRequester(nombreFocusRequester)
            )
        }

        item {
            FormInput(
                fieldKey = "telefono",
                label = "Número de teléfono:",
                placeHolder = "+503335432301",
                viewModel = formViewModel,
                inputType = KeyboardType.Number,
                isFormatted = true,
                imeAction = ImeAction.Next,
                onImeAction = { datePickerFocusRequester.requestFocus() }
            )
        }

        item {
            DatePickerInput(
                fieldKey = "fechaNacimiento",
                label = "Fecha de nacimiento:",
                placeHolder = "dd/mm/YYYY",
                viewModel = formViewModel,
                modifier = Modifier.focusRequester(datePickerFocusRequester),
                imeAction = ImeAction.Next
            )
        }

        item {
            FormInput(
                fieldKey = "correo",
                label = "Correo electrónico:",
                placeHolder = "example@gmail.com",
                viewModel = formViewModel,
                inputType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        }

        item {
            FormInput(
                fieldKey = "dui",
                label = "DUI:",
                placeHolder = "12345678-9",
                viewModel = formViewModel,
                inputType = KeyboardType.Number,
                isFormatted = true,
                imeAction = ImeAction.Next
            )
        }

        item {
            FormInput(
                fieldKey = "password",
                label = if (isEditMode) "Crear nueva contraseña:" else "Contraseña:",
                placeHolder = if (isEditMode) "(dejar en blanco si no es necesario)" else "root1234",
                viewModel = formViewModel,
                inputType = KeyboardType.Password,
                isPassword = true,
                imeAction = ImeAction.Next
            )
        }

        if (selectedRol == "RESIDENTE") {
            item {
                FormInput(
                    fieldKey = "casa",
                    label = "Número de casa:",
                    placeHolder = "ej. 46D",
                    viewModel = formViewModel,
                    imeAction = ImeAction.Done,
                    onImeAction = {
                        sendUserForm(
                            formViewModel,
                            usuarioViewModel,
                            initialData,
                            isEditMode,
                            selectedRol,
                            onSubmitSuccess
                        )
                    }
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    sendUserForm(
                        formViewModel,
                        usuarioViewModel,
                        initialData,
                        isEditMode,
                        selectedRol,
                        onSubmitSuccess
                    )
                }) {
                    Text(if (isEditMode) "Actualizar Usuario" else "Guardar Usuario")
                }
            }
        }
    }
}

fun sendUserForm(
    formViewModel: FormViewModel,
    usuarioViewModel: UsuarioViewModel,
    initialData: Usuario? = null,
    isEditMode: Boolean,
    selectedRol: String,
    onSubmitSuccess: () -> Unit
) {
    val requiredFields = mutableListOf("nombre", "telefono", "fechaNacimiento", "correo", "dui")

    if (selectedRol == "RESIDENTE") {
        requiredFields += "casa"
    }

    var optionalFields = emptyList<String>()

    if (isEditMode) {
        optionalFields = listOf("password")
    } else {
        requiredFields += "password"
    }

    formViewModel.markTriedToSubmit()
    val isValid = formViewModel.validate(fields = requiredFields + optionalFields, optionalFields = optionalFields)

    if (isValid) {
        val usuario = Usuario(
            fotoPerfil = formViewModel.imageFields["fotoPerfil"],
            dui = formViewModel.formattedTextFields["dui"]?.text ?: return,
            nombre = formViewModel.textFields["nombre"]?.text ?: return,
            telefono = formViewModel.formattedTextFields["telefono"]?.text ?: return,
            fechaNacimiento = formViewModel.dateFields["fechaNacimiento"] ?: return,
            email = formViewModel.textFields["correo"]?.text ?: return,
            casa = if (selectedRol == "RESIDENTE") {
                formViewModel.textFields["casa"]?.text ?: return
            }else null,
            password = formViewModel.textFields["password"]?.text
                ?.takeIf { it.isNotBlank() }
                ?: initialData?.password
                ?: return,
            rol = selectedRol
        )

        //val usuarioDB = usuario.toUsuarioDB()

        if (isEditMode) {
            usuarioViewModel.editUsuario(usuario)
            //usuarioViewModel.editUsuario(usuario)
        } else {
            usuarioViewModel.addUsuario(usuario)
        }

        formViewModel.clearAllFields()
        onSubmitSuccess()
    }
}
