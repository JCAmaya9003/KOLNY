package com.pdm_proyecto.kolny.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle

@Composable
fun CardInfo(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(4.dp)
    ) {
        Text(
            text = label,
            fontStyle = FontStyle.Italic,
            color = Color.Gray,
        )
        Text( text = value )
    }
    Spacer(modifier = Modifier.height(4.dp))
}