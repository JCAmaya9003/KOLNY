package com.pdm_proyecto.kolny.ui.components.visit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm_proyecto.kolny.data.models.Visita
import com.pdm_proyecto.kolny.ui.components.FormInput
import com.pdm_proyecto.kolny.viewmodels.FormViewModel
import com.pdm_proyecto.kolny.viewmodels.VisitaViewModel
import java.util.Date

@Composable
fun VisitForm(
    visitaViewModel: VisitaViewModel,
    initialData: Visita? = null,
    onSubmitSuccess: () -> Unit = {}
){
    val isEditMode = initialData != null
    val formViewModel: FormViewModel = viewModel(
        key = if (isEditMode) "screen_edit_visita" else "screen_add_visita"
    )

    LaunchedEffect(initialData) {
        initialData?.let { visita ->
            formViewModel.setInitialTextValue("nombre", TextFieldValue(visita.nombreVisita))
            formViewModel.setInitialTextValue("motivo", TextFieldValue(visita.motivo))
            formViewModel.setInitialFormattedValue("dui", TextFieldValue(visita.dui))
            formViewModel.setInitialFormattedValue("placa", TextFieldValue(visita.placa ?: ""))
        }
    }

    Column(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 32.dp
            )
            .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column {
            FormInput(
                fieldKey = "nombre",
                label = "Nombre del visitante:",
                placeHolder = "ej. Jacobo Perez",
                viewModel = formViewModel,
                inputCapitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next,

            )
            FormInput(
                fieldKey = "motivo",
                label = "Motivo de visita:",
                placeHolder = "ej. Jugar fulbo",
                viewModel = formViewModel,
                imeAction = ImeAction.None,
                minLines = 3,
                maxLines = Int.MAX_VALUE
            )
            FormInput(
                fieldKey = "dui",
                label = "DUI:",
                placeHolder = "ej. 12345678-9",
                viewModel = formViewModel,
                inputType = KeyboardType.Number,
                isFormatted = true,
                imeAction = ImeAction.Next
            )
            FormInput(
                fieldKey = "placa",
                label = "Placa (Opcional):",
                placeHolder = "ej. 1A34B6",
                viewModel = formViewModel,
                inputCapitalization = KeyboardCapitalization.Characters,
                isFormatted = true,
                maxLength = 8,
                imeAction = ImeAction.Done,
                onImeAction = {
                    sendVisitForm(
                        formViewModel = formViewModel,
                        visitaViewModel = visitaViewModel,
                        initialData = initialData,
                        isEditMode = isEditMode,
                        onSubmitSuccess = onSubmitSuccess
                    )
                }
            )
        }
        Button(onClick = {
            sendVisitForm(
                formViewModel = formViewModel,
                visitaViewModel = visitaViewModel,
                initialData = initialData,
                isEditMode = isEditMode,
                onSubmitSuccess = onSubmitSuccess
            )
        }) {
            Text(if (isEditMode) "Actualizar Visita" else "Guardar Visita")
        }
    }
}

fun sendVisitForm(
    formViewModel: FormViewModel,
    visitaViewModel: VisitaViewModel,
    initialData: Visita? = null,
    isEditMode: Boolean,
    onSubmitSuccess: () -> Unit
) {
    val requiredFields = mutableListOf("nombre", "motivo", "dui")
    val optionalFields = listOf("placa")

    val isValid = formViewModel.validate(
        fields = requiredFields + optionalFields,
        optionalFields = optionalFields
    )

    if (isValid) {
        val visita = Visita(
            nombreVisita = formViewModel.textFields["nombre"]?.text ?: return,
            dui = formViewModel.formattedTextFields["dui"]?.text ?: return,
            placa = formViewModel.formattedTextFields["placa"]?.text,
            motivo = formViewModel.textFields["motivo"]?.text ?: return,
            fechaVisita = initialData?.fechaVisita ?: Date()
        )

        if (isEditMode) {
            visitaViewModel.editVisita(visita)
        } else {
            visitaViewModel.addVisita(visita)
        }

        formViewModel.clearAllFields()
        onSubmitSuccess()
    }
}