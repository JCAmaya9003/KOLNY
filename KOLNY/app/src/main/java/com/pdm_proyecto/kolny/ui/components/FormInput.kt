package com.pdm_proyecto.kolny.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.pdm_proyecto.kolny.viewmodels.FormViewModel

@Composable
fun FormInput(
    fieldKey: String,
    label: String,
    placeHolder: String = "",
    viewModel: FormViewModel,
    inputType: KeyboardType = KeyboardType.Text,
    isFormatted: Boolean = false,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val error = viewModel.errors[fieldKey]

    // Estado para mostrar/ocultar contraseña
    var passwordVisible by remember { mutableStateOf(false) }

    val visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label)
        Spacer(Modifier.height(8.dp))
        if (isFormatted) {
            val value = viewModel.formattedTextFields[fieldKey] ?: TextFieldValue()
            OutlinedTextField(
                value = value,
                onValueChange = { viewModel.onFormattedTextChange(fieldKey, it) },
                placeholder = { Text(placeHolder) },
                isError = error != null,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = inputType),
                visualTransformation = visualTransformation,
                trailingIcon = {
                    if (isPassword) {
                        val image = if (passwordVisible) Icons.Filled.CheckCircle else Icons.Filled.Lock
                        val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    }
                },
                modifier = modifier
            )
        } else {
            val value = viewModel.textFields[fieldKey] ?: ""
            OutlinedTextField(
                value = value,
                onValueChange = { viewModel.onTextChange(fieldKey, it) },
                placeholder = { Text(placeHolder) },
                isError = error != null,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = inputType),
                visualTransformation = visualTransformation,
                trailingIcon = {
                    if (isPassword) {
                        val image = if (passwordVisible) Icons.Filled.CheckCircle else Icons.Filled.Lock
                        val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    }
                },
                modifier = modifier
            )
        }
        if (error != null) {
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }
    }
}

