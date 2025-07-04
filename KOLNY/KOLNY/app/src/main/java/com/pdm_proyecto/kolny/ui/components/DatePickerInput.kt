package com.pdm_proyecto.kolny.ui.components

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.pdm_proyecto.kolny.viewmodels.FormViewModel
import java.util.*

@Composable
fun DatePickerInput(
    fieldKey: String,
    label: String,
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    onImeAction: (() -> Unit)? = null,
    imeAction: ImeAction = ImeAction.Default,
    viewModel: FormViewModel,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val error = viewModel.errors[fieldKey]
    val displayedText = viewModel.getFormattedDate(fieldKey)
    val wasFocused = remember { mutableStateOf(false) }
    val shouldOpenDialog = remember { mutableStateOf(false) }

    //para que funcione al hacerle click
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect {
            if (it is PressInteraction.Release) {
                shouldOpenDialog.value = true
            }
        }
    }

    //para que funcione con el imeAction (cuando le hacen focus)
    LaunchedEffect(wasFocused.value) {
        if (wasFocused.value) {
            shouldOpenDialog.value = true
        }
    }

    //ya muestra el datePicker
    LaunchedEffect(shouldOpenDialog.value) {
        if (shouldOpenDialog.value) {
            shouldOpenDialog.value = false

            val calendar = Calendar.getInstance().apply {
                viewModel.dateFields[fieldKey]?.let { time = it }
            }

            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    val newDate = calendar.time
                    Log.d("DATE_PICKER", "Fecha seleccionada: $newDate")
                    viewModel.onDateChange(fieldKey, newDate)

                    when (imeAction) {
                        ImeAction.Next -> onImeAction?.invoke() ?: focusManager.moveFocus(FocusDirection.Down)
                        ImeAction.Done -> onImeAction?.invoke() ?: focusManager.clearFocus()
                        else -> onImeAction?.invoke()
                    }

                    wasFocused.value = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = displayedText,
            onValueChange = {},
            placeholder = { Text(placeHolder) },
            readOnly = true,
            isError = error != null,
            interactionSource = interactionSource,
            modifier = modifier.onFocusChanged { focusState ->
                if (focusState.isFocused && !wasFocused.value) {
                    wasFocused.value = true
                }
            }
        )
        if (error != null) {
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }
    }
}
