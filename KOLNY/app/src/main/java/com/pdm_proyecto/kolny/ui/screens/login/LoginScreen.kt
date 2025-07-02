package com.pdm_proyecto.kolny.ui.screens.login

import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.pdm_proyecto.kolny.R
import androidx.navigation.NavController
import com.google.firebase.auth.GoogleAuthProvider
import com.pdm_proyecto.kolny.data.models.ResultadoAcceso
import com.pdm_proyecto.kolny.viewmodels.LoginViewModel
import kotlin.jvm.java
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    //navController: NavController,
    onLoginSuccess: (ResultadoAcceso) -> Unit

) {

    val context = LocalContext.current
    val loginViewModel: LoginViewModel = hiltViewModel()
    var emailField     by remember { mutableStateOf("") }
    var passwordField  by remember { mutableStateOf("") }

    /* ---------- Google Sign-In launcher ---------- */
    val googleSignInClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )
    }

    //var correoAutenticado by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential =
                    GoogleAuthProvider.getCredential(account.idToken /* idToken */, null)
                loginViewModel.loginConCredencialGoogle(credential)
            } catch (_: Exception) { /* puedes mostrar un snackbar */ }
        }
    }

    val estadoAcceso by loginViewModel.estadoAcceso.collectAsState()

    /*LaunchedEffect(estadoAcceso) {
        when (estadoAcceso) {
            is ResultadoAcceso.AdminDesarrollador,
            is ResultadoAcceso.AdminDirectiva,
            is ResultadoAcceso.Residente,
            is ResultadoAcceso.Vigilante -> onLoginSuccess(estadoAcceso)
            ResultadoAcceso.NoRegistrado -> { /* toast… */ }
            ResultadoAcceso.Inactivo    -> { /* toast… */ }
            else -> Unit
        }
    }*/

    /* ---------- error local ---------- */
    var loginIntentado by remember { mutableStateOf(false) }

    var errorMsg by remember { mutableStateOf<String?>(null) }

    /* Reacciona al cambio de estadoAcceso */
    LaunchedEffect(estadoAcceso) {
        if (!loginIntentado) {
            return@LaunchedEffect
        }
        when (estadoAcceso) {
            is ResultadoAcceso.AdminDesarrollador,
            is ResultadoAcceso.AdminDirectiva,
            is ResultadoAcceso.Vigilante,
            is ResultadoAcceso.Residente -> {
                errorMsg = null
                onLoginSuccess(estadoAcceso)
            }
            ResultadoAcceso.NoRegistrado -> {
                errorMsg = "Correo electrónico o contraseña incorrectos"
            }
            ResultadoAcceso.Inactivo -> {
                errorMsg = "Cuenta inactiva"
            }
            ResultadoAcceso.Desconocido -> {
                errorMsg = "Error desconocido"
            }

            is ResultadoAcceso.ErrorBd -> errorMsg = (estadoAcceso as ResultadoAcceso.ErrorBd).mensaje
            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD7ECFF)) // Azul más claro como en Figma
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "KOLNY",
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )

            OutlinedTextField(
                value = emailField,
                onValueChange = {emailField = it },
                label = { Text("CORREO ELECTRONICO") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                singleLine = true,
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2371A7),
                    unfocusedBorderColor = Color(0xFF2371A7),
                )
            )

            OutlinedTextField(
                value = passwordField,
                onValueChange = {passwordField = it},
                label = { Text("CONTRASEÑA") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                singleLine = true,
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2371A7),
                    unfocusedBorderColor = Color(0xFF2371A7),
                )
            )

            Text(
                text = "¿Olvidaste tu contraseña?",
                fontSize = 12.sp,
                color = Color(0xFF2371A7),
                modifier = Modifier.align(Alignment.End)
            )
            errorMsg?.let { msg ->
                Spacer(Modifier.height(4.dp))
                Text(
                    text = msg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(4.dp))
            }

            Button( //boton ingresar
                onClick = {
                    loginIntentado = true
                    /*loginViewModel.email    = emailField.trim()
                    loginViewModel.password = passwordField*/
                    loginViewModel.loginConEmail(emailField.trim(), passwordField)},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2371A7)
                )
            ) {
                Text("INGRESAR", color = Color.White)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(Modifier.weight(1f), color = Color.Black)
                Text("  O inicia sesión con  ", color = Color.Black, fontSize = 12.sp)
                Divider(Modifier.weight(1f), color = Color.Black)
            }
            Button(
                onClick = {
                    val intent = googleSignInClient.signInIntent
                    launcher.launch(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = "Google logo",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Iniciar sesión con Google", color = Color.Black)
            }

            Text(
                text = "¿No tienes cuenta? Contáctanos al +503 1234–5678",
                fontSize = 12.sp,
                color = Color(0xFF2371A7)
            )
        }
    }
}