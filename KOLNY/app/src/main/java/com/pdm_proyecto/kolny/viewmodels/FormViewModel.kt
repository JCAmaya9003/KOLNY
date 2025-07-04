package com.pdm_proyecto.kolny.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.pdm_proyecto.kolny.utils.formatDate
import com.pdm_proyecto.kolny.utils.formatWithHyphen
import java.util.Date
import android.net.Uri

//si hay problemas más tarde con corrutinas hay que adaptar esto a StateFlow
class FormViewModel : ViewModel() {

    //cambiando de String a textFieldValue para luego mejorar la UX
    private val _textFields = mutableStateMapOf<String, TextFieldValue>()
    val textFields: Map<String, TextFieldValue> get() = _textFields

    private val _formattedTextFields = mutableStateMapOf<String, TextFieldValue>()
    val formattedTextFields: Map<String, TextFieldValue> get() = _formattedTextFields

    private val _dateFields = mutableStateMapOf<String, Date>()
    val dateFields: Map<String, Date> get() = _dateFields

    //SOLO PARA TRABAJAR LOCAL
    private val _imageFields = mutableStateMapOf<String, String?>()
    val imageFields: Map<String, String?> get() = _imageFields
    //las imagenes son actualmente un String que contiene el URI local
    //cuando se conecte a backend, cambiar esta parte para:
    //subir la imagen a la BD y obtener el URL de la imagen en lugar del URI local

    private val _errors = mutableStateMapOf<String, String?>()
    val errors: Map<String, String?> get() = _errors

    private var hasTriedToSubmit = false

    //cuando toque editar vamos a usar esto, para setear los valores de los inputs
    fun setInitialTextValue(key: String, value: TextFieldValue) {
        _textFields[key] = value
    }

    fun setInitialFormattedValue(key: String, value: TextFieldValue) {
        _formattedTextFields[key] = value
    }

    fun setInitialDateValue(key: String, date: Date) {
        _dateFields[key] = date
    }

    fun setInitialImageUri(key: String, uri: String?) {
        _imageFields[key] = uri
    }

    fun clearAllFields() {
        _textFields.clear()
        _formattedTextFields.clear()
        _dateFields.clear()
        _imageFields.clear()
        _errors.clear()
    }

    fun onTextChange(key: String, value: TextFieldValue) {
        _textFields[key] = value
        if (shouldValidateRealtime()) {
            validateSingleField(key, value.text)
        }
    }

    fun onFormattedTextChange(key: String, value: TextFieldValue) {
        when {
            key.contains("telefono") -> _formattedTextFields[key] = formatWithHyphen(value, 8, 5)
            key.contains("dui") -> _formattedTextFields[key] = formatWithHyphen(value, 9, 9)
            key.contains("placa") -> _formattedTextFields[key] = value.copy(text = value.text.uppercase())
            else -> _formattedTextFields[key] = value
        }
        if (shouldValidateRealtime()) {
            validateSingleField(key, _formattedTextFields[key]?.text.orEmpty())
        }
    }


    fun onDateChange(key: String, date: Date) {
        _dateFields[key] = date
        if (shouldValidateRealtime()) {
            validateSingleDateField(key, date)
        }
    }

    fun onImageSelected(key: String, uri: Uri?) {
        _imageFields[key] = uri?.toString()
    }

    fun getFormattedDate(key: String): String {
        return _dateFields[key]?.let { formatDate(it) } ?: ""
    }

    fun validate(
        fields: List<String>,
        optionalFields: List<String> = emptyList()
    ): Boolean {
        Log.d("VALIDATE", "entro aca")
        _errors.clear()

        for (key in fields) {
            val fieldValue = _textFields[key]
            val value = fieldValue?.text.orEmpty()
            val isOptional = key in optionalFields
            validateSingleField(key, value, isOptional)
        }

        for ((key, field) in _formattedTextFields) {
            val value = field.text
            val isOptional = key in optionalFields
            validateSingleField(key, value, isOptional)
        }

        for ((key, date) in _dateFields) {
            val isOptional = key in optionalFields
            validateSingleDateField(key, date, isOptional)
        }

        _errors.forEach { (key, error) ->
            if (error != null) Log.d("VALIDATE", "$key - $error")
        }

        return _errors.none { it.value != null }
    }


    private fun validateSingleField(key: String, value: String, isOptional: Boolean = false) {

        _errors[key] = when {
            value.isBlank() -> if (isOptional) null else "Este campo es obligatorio"

            key.contains("correo") && !Patterns.EMAIL_ADDRESS.matcher(value).matches() -> {
                "Correo inválido"
            }

            key.contains("password") -> when {
                value.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
                value.any { it.isWhitespace() } -> "La contraseña no puede contener espacios"
                else -> null
            }

            key.contains("telefono") && !value.matches(Regex("""\d{4}-\d{4}""")) -> {
                "Teléfono inválido"
            }

            key.contains("dui") && !value.matches(Regex("""\d{8}-\d""")) -> {
                "DUI inválido"
            }

            else -> null
        }
    }

    private fun validateSingleDateField(key: String, date: Date?, isOptional: Boolean = false) {
        if (date == null) {
            _errors[key] = if (isOptional) null else "Este campo es obligatorio"
            return
        }

        _errors[key] = when {
            key.contains("fechaNacimiento") && date.after(Date()) -> {
                "Fecha de nacimiento inválida"
            }
            key.contains("fechaEvento") && date.before(Date()) -> {
                "Fecha de evento inválida"
            }
            else -> null
        }
    }

    fun markTriedToSubmit() {
        hasTriedToSubmit = true
    }

    fun shouldValidateRealtime(): Boolean = hasTriedToSubmit
}
