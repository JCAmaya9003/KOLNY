package com.pdm_proyecto.kolny.screens.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.pdm_proyecto.kolny.R
import com.pdm_proyecto.kolny.data.model.ResultadoAcceso
import com.pdm_proyecto.kolny.viewmodel.loginVM.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseUser




@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val activity = context as Activity
    val auth = FirebaseAuth.getInstance()

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener {
                if (it.isSuccessful) {
                    onLoginSuccess()
                }
            }
        } catch (_: Exception) {}
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
                value = "",
                onValueChange = {},
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
                value = "",
                onValueChange = {},
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

            Button(
                onClick = {},
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
                Text(
                    text = "Iniciar sesión con Google",
                    color = Color.Black
                )
            }

            Text(
                text = "¿No tienes cuenta? Contáctanos al +503 1234–5678",
                fontSize = 12.sp,
                color = Color(0xFF2371A7)
            )
        }
    }
    val viewModel: LoginViewModel = viewModel()

    val firebaseUser = FirebaseAuth.getInstance().currentUser

    val correo = firebaseUser?.email ?: return

    LaunchedEffect(correo) {
        viewModel.verificarUsuarioPorCorreo(correo)
    }

    val estadoAcceso by viewModel.estadoAcceso.collectAsState()

    //dara error hasta que se haga la navegacion
    when (estadoAcceso) {
        is ResultadoAcceso.AdminDesarrollador -> navController.navigate("admin_panel")
        is ResultadoAcceso.AdminDirectiva -> navController.navigate("admin_panel")
        is ResultadoAcceso.Residente -> navController.navigate("residente_panel")
        is ResultadoAcceso.Vigilante -> navController.navigate("vigilante_panel")
        ResultadoAcceso.NoRegistrado -> mostrarToast("Correo no registrado")
        ResultadoAcceso.Inactivo -> mostrarToast("Cuenta desactivada")
        ResultadoAcceso.Desconocido -> mostrarToast("Acceso no válido")
        else -> {}
    }




    /*LoginViewModel.verificarUsuarioPorCorreo(firebaseUser) { resultado ->
        when (resultado) {
            is ResultadoAcceso.AdminDesarrollador -> navController.navigate("admin_panel")
            is ResultadoAcceso.AdminDirectiva -> navController.navigate("admin_panel")
            is ResultadoAcceso.Residente -> navController.navigate("residente_panel")
            is ResultadoAcceso.Vigilante -> navController.navigate("vigilante_panel")
            ResultadoAcceso.NoRegistrado -> mostrarToast("Correo no registrado en la comunidad.")
            ResultadoAcceso.Inactivo -> mostrarToast("Tu cuenta está desactivada.")
            ResultadoAcceso.Desconocido -> mostrarToast("Error al verificar tu rol.")
        }
    }*/




}
