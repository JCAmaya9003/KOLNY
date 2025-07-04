package com.pdm_proyecto.kolny.utils

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

fun formatWithHyphen(input: TextFieldValue, length: Int, dashAt: Int): TextFieldValue {
    val digits = input.text.filter { it.isDigit() }.take(length)

    val formatted = if (digits.length > (dashAt - 1)) {
        digits.substring(0, (dashAt - 1)) + "-" + digits.substring((dashAt - 1))
    } else {
        digits
    }

    var cursorPos = input.selection.end

    if (digits.length == dashAt && !input.text.contains("-")) {
        cursorPos++
    }

    cursorPos = cursorPos.coerceAtMost(formatted.length)

    return TextFieldValue(
        text = formatted,
        selection = TextRange(cursorPos)
    )
}