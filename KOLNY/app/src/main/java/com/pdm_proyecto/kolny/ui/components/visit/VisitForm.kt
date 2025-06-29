package com.pdm_proyecto.kolny.ui.components.visit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
            formViewModel.setInitialTextValue("nombre", visita.nombreVisita)
            formViewModel.setInitialTextValue("motivo", visita.motivo)
            formViewModel.setInitialFormattedValue("dui", TextFieldValue(visita.dui))
            formViewModel.setInitialTextValue("placa", visita.placa ?: "")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column {
            FormInput(
                fieldKey = "nombre",
                label = "Nombre del visitante:",
                placeHolder = "ej. Jacobo Perez",
                viewModel = formViewModel
            )
            FormInput(
                fieldKey = "motivo",
                label = "Motivo de visita:",
                placeHolder = "ej. Jugar fulbo",
                viewModel = formViewModel
            )
            FormInput(
                fieldKey = "dui",
                label = "DUI:",
                placeHolder = "ej. 12345678-9",
                viewModel = formViewModel,
                inputType = KeyboardType.Number,
                isFormatted = true
            )
            FormInput(
                fieldKey = "placa",
                label = "Placa (Opcional):",
                placeHolder = "ej. 1A34B6",
                viewModel = formViewModel
            )
            Button(onClick = {
                val requiredFields = mutableListOf("nombre", "motivo", "dui")
                val optionalFields = listOf("placa")

                val isValid = formViewModel.validate(
                    fields = requiredFields + optionalFields,
                    optionalFields = optionalFields
                )

                if (isValid) {
                    val visita = Visita(
                        nombreVisita = formViewModel.textFields["nombre"] ?: return@Button,
                        dui = formViewModel.formattedTextFields["dui"]?.text ?: return@Button,
                        placa = formViewModel.textFields["placa"],
                        motivo = formViewModel.textFields["motivo"] ?: return@Button,
                        fechaVisita = Date()
                    )

                    if (isEditMode) {
                        visitaViewModel.editVisita(visita)
                    } else {
                        visitaViewModel.addVisita(visita)
                    }

                    formViewModel.clearAllFields()
                    onSubmitSuccess()
                }
            }) {
                Text(if (isEditMode) "Actualizar Visita" else "Guardar Visita")
            }
        }
    }
}