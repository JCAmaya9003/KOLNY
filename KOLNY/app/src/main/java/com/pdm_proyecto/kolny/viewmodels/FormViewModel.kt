package com.pdm_proyecto.kolny.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.pdm_proyecto.kolny.utils.formatDate
import com.pdm_proyecto.kolny.utils.formatWithHyphen
import java.util.Date

//si hay problemas más tarde con corrutinas hay que adaptar esto a StateFlow
class FormViewModel : ViewModel() {

    private val _textFields = mutableStateMapOf<String, String>()
    val textFields: Map<String, String> get() = _textFields

    private val _formattedTextFields = mutableStateMapOf<String, TextFieldValue>()
    val formattedTextFields: Map<String, TextFieldValue> get() = _formattedTextFields

    private val _dateFields = mutableStateMapOf<String, Date>()
    val dateFields: Map<String, Date> get() = _dateFields

    private val _errors = mutableStateMapOf<String, String?>()
    val errors: Map<String, String?> get() = _errors

    //cuando toque editar vamos a usar esto, para setear los valores de los inputs
    fun setInitialTextValue(key: String, value: String) {
        _textFields[key] = value
    }

    fun setInitialFormattedValue(key: String, value: TextFieldValue) {
        _formattedTextFields[key] = value
    }

    fun setInitialDateValue(key: String, date: Date) {
        _dateFields[key] = date
    }

    fun clearAllFields() {
        _textFields.clear()
        _formattedTextFields.clear()
        _dateFields.clear()
        _errors.clear()
    }

    fun onTextChange(key: String, value: String) {
        _textFields[key] = value
    }

    fun onFormattedTextChange(key: String, value: TextFieldValue) {
        when {
            key.contains("telefono") -> _formattedTextFields[key] = formatWithHyphen(value, 8, 5)
            key.contains("dui") -> _formattedTextFields[key] = formatWithHyphen(value, 9, 9)
        }
    }

    fun onDateChange(key: String, date: Date) {
        _dateFields[key] = date
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
            val value = _textFields[key]
            val isOptional = key in optionalFields

            if (value.isNullOrBlank()) {
                _errors[key] = if (isOptional) null else "Este campo es obligatorio"
                continue
            }

            when {
                key.contains("correo") && !Patterns.EMAIL_ADDRESS.matcher(value).matches() -> {
                    _errors[key] = "Correo inválido"
                }

                key.contains("password") -> {
                    _errors[key] = when {
                        value.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
                        value.contains(" ") -> "La contraseña no puede contener espacios"
                        else -> null
                    }
                }

                else -> _errors[key] = null
            }
        }

        for ((key, field) in _formattedTextFields) {
            val value = field.text
            val isOptional = key in optionalFields

            if (value.isBlank()) {
                _errors[key] = if (isOptional) null else "Este campo es obligatorio"
                continue
            }

            _errors[key] = when {
                key.contains("telefono") && !value.matches(Regex("""\d{4}-\d{4}""")) -> "Teléfono inválido"
                key.contains("dui") && !value.matches(Regex("""\d{8}-\d""")) -> "DUI inválido"
                else -> null
            }
        }

        for ((key, date) in _dateFields) {
            val isOptional = key in optionalFields

            if (date == null && !isOptional) {
                _errors[key] = "Este campo es obligatorio"
                continue
            }

            when {
                key.contains("fechaNacimiento") && date.after(Date()) ->
                    _errors[key] = "Fecha de nacimiento inválida"
                key.contains("fechaEvento") && date.before(Date()) ->
                    _errors[key] = "Fecha de evento inválida"
                else -> _errors[key] = null
            }
        }

        _errors.forEach { (key, error) ->
            if (error != null) Log.d("VALIDATE", "$key - $error")
        }

        return _errors.none { it.value != null }
    }

}