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
        Log.d("VALIDATE", "entro aquí")

        _errors.clear()

        //para inputs normales
        for (key in fields) {
            val value = _textFields[key]
            when {
                key in optionalFields && value.isNullOrBlank() -> {
                    _errors[key] = null
                }

                value.isNullOrBlank() -> {
                    _errors[key] = "Este campo es obligatorio"
                }

                key.contains("correo") && !Patterns.EMAIL_ADDRESS.matcher(value).matches() -> {
                    _errors[key] = "Correo inválido"
                }

                key.contains("password") -> {
                    when {
                        value.length < 8 -> {
                            _errors[key] = "La contraseña debe tener al menos 8 caracteres"
                        }

                        value.contains(" ") -> {
                            _errors[key] = "La contraseña no puede contener espacios"
                        }

                        else -> _errors[key] = null
                    }
                }

                else -> {
                    _errors[key] = null
                }
            }
        }

        //para inputs con formato
        for ((key, field) in _formattedTextFields) {
            val value = field.text
            when {
                key in optionalFields && value.isBlank() -> {
                    _errors[key] = null
                }

                value.isBlank() -> {
                    _errors[key] = "Este campo formateado es obligatorio"
                }

                key.contains("telefono") && !value.matches(Regex("""\d{4}-\d{4}""")) -> {
                    _errors[key] = "Teléfono inválido"
                }

                //aca falta la validacion de ver si hay otros duis iguales
                key.contains("dui") && !value.matches(Regex("""\d{8}-\d""")) -> {
                    _errors[key] = "DUI inválido"
                }

                else -> {
                    _errors[key] = null
                }
            }
        }

        //para fechas
        for (key in fields.filter { it.contains("fecha", ignoreCase = true) }) {
            val date = _dateFields[key]

            when {
                key in optionalFields && date == null -> {
                    _errors[key] = null
                }

                date == null -> {
                    _errors[key] = "Este campo es obligatorio"
                }

                key.contains("fechaNacimiento") && date > Date() -> {
                    _errors[key] = "Fecha de nacimiento inválida"
                }

                key.contains("fechaEvento") && date <= Date() -> {
                    _errors[key] = "Fecha de evento inválida"
                }

                else -> {
                    _errors[key] = null
                }
            }
        }

        //aca es para logs por si alguna validación falla en algún punto
        Log.d("VALIDATE", "errores:")
        _errors.forEach { (key, error) ->
            if (error != null) Log.d("VALIDATE", "$key - $error")
        }

        val isValid = _errors.none { it.value != null }

        return isValid
    }

}