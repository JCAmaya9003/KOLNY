package com.pdm_proyecto.kolny.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
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
    inputCapitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    maxLength: Int? = null,
    isFormatted: Boolean = false,
    isPassword: Boolean = false,
    imeAction: ImeAction = ImeAction.Default,
    onImeAction: (() -> Unit)? = null,
    minLines: Int = 1,
    maxLines: Int = 1,
    modifier: Modifier = Modifier,
) {
    val error = viewModel.errors[fieldKey]
    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val visualTransformation =
        if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None

    val keyboardOptions = KeyboardOptions(
        keyboardType = inputType,
        capitalization = inputCapitalization,
        imeAction = imeAction
    )

    val keyboardActions = KeyboardActions(
        onNext = { onImeAction?.invoke() ?: focusManager.moveFocus(FocusDirection.Down) },
        onDone = { onImeAction?.invoke() ?: focusManager.clearFocus() }
    )

    val value = if (isFormatted) {
        viewModel.formattedTextFields[fieldKey] ?: TextFieldValue()
    } else {
        viewModel.textFields[fieldKey] ?: TextFieldValue()
    }.let {
        if (it.selection.collapsed && it.selection.start == 0)
            it.copy(selection = TextRange(it.text.length))
        else it
    }

    val onValueChange: (TextFieldValue) -> Unit = {
        val newText = if (maxLength != null) it.text.take(maxLength) else it.text
        val updatedValue = TextFieldValue(text = newText, selection = TextRange(newText.length))

        if (isFormatted) {
            viewModel.onFormattedTextChange(fieldKey, updatedValue)
        } else {
            viewModel.onTextChange(fieldKey, updatedValue)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label)
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeHolder) },
            isError = error != null,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            trailingIcon = {
                if (isPassword) {
                    val icon = if (passwordVisible) Icons.Filled.CheckCircle else Icons.Filled.Lock
                    val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = description)
                    }
                }
            },
            modifier = modifier,
            minLines = minLines,
            maxLines = maxLines,
            singleLine = maxLines == 1
        )

        if (error != null) {
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }
    }
}




