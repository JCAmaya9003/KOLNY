package com.pdm_proyecto.kolny.ui.components

import android.Manifest
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.pdm_proyecto.kolny.viewmodels.FormViewModel

//cuándo quieran usar un icono, como para noticias, solo pongan showPreview en false
//y cambien el defaultContent por una imagen que represente el boton de agregar imagen
//si mandan esto en el parametro, yo digo que puede servir
/*
defaultContent = {
              Icon(Icons.Default.Add, contentDescription = "Agregar imagen", modifier = Modifier.size(40.dp))
}
*/
//y ahí editenlo, depende para que lo vayan a hacerlo
@Composable
fun ImagePickerInput(
    fieldKey: String,
    viewModel: FormViewModel,
    modifier: Modifier = Modifier,
    defaultContent: @Composable () -> Unit,
    imageSize: Dp = 100.dp,
    shape: Shape = MaterialTheme.shapes.medium,
    showPreview: Boolean = true
) {
    val context = LocalContext.current
    val imageUri: Uri? = viewModel.imageFields[fieldKey]?.let { Uri.parse(it) }

    val pickLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewModel.onImageSelected(fieldKey, uri)
    }

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    val permLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            pickLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } else {
            Toast.makeText(context, "Permiso denegado para acceder a imágenes", Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = modifier) {
        Surface(
            modifier = Modifier
                .size(imageSize)
                .clickable { permLauncher.launch(permission) },
            shape = shape,
            tonalElevation = 1.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (showPreview && imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    defaultContent()
                }
            }
        }
    }
}

