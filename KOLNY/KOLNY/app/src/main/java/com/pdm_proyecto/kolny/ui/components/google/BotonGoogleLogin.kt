package com.pdm_proyecto.kolny.ui.components.google

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.pdm_proyecto.kolny.R

@Composable
fun BotonGoogleLogin(
    onCredentialReady: (com.google.firebase.auth.AuthCredential) -> Unit
) {
    val context = LocalContext.current

    /* Configuración de GoogleSignIn */
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // utiliza el client-id web que viene en google-services.json
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    /* ActivityResultLauncher */
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { res ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(res.data)
        try {
            val account = task.result          // puede lanzar excepción
            Log.d("LOGIN", "EMAIL SELECTED = ${account.email}")
            val credential = GoogleAuthProvider
                .getCredential(account.idToken, null)
            onCredentialReady(credential)      // callback al VM
        } catch (e: Exception) {
            Log.e("LOGIN", "Google sign-in failed", e)
        }
    }

    /* UI */
    Button(
        onClick = { launcher.launch(googleSignInClient.signInIntent) },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp)
    ){
        Icon(
            painter = painterResource(id = R.drawable.google_logo),
            contentDescription = "Google logo",
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text("Iniciar sesión con Google", color = Color.Black)
    }
}