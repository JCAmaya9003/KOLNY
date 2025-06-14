package com.pdm_proyecto.kolny.ui.components

import androidx.compose.ui.platform.LocalContext
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pdm_proyecto.kolny.utils.formatDate
import com.pdm_proyecto.kolny.viewmodels.FormViewModel
import java.util.*

@Composable
fun DatePickerInput(
    fieldKey: String,
    label: String,
    placeHolder: String = "",
    viewModel: FormViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val error = viewModel.errors[fieldKey]
    val displayedText = viewModel.getFormattedDate(fieldKey)

    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect {
            if (it is PressInteraction.Release) {
                val calendar = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        calendar.set(year, month, dayOfMonth)
                        val newDate = calendar.time
                        Log.d("DATE_PICKER", "Fecha seleccionada: $newDate")
                        viewModel.onDateChange(fieldKey, newDate)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = displayedText,
            onValueChange = {},
            placeholder = { Text(placeHolder) },
            readOnly = true,
            isError = error != null,
            modifier = modifier,
            interactionSource = interactionSource
        )
        if (error != null) {
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }
    }
}


