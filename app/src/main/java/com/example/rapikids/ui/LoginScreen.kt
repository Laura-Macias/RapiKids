package com.example.rapikids.ui

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rapikids.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun LoginScreen(navController: NavController, onLoginSuccess: (String) -> Unit) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance().getReference("users")

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf("Ingresar") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoggingInWithGoogle by remember { mutableStateOf(false) }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("106742159318252340163")
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

            isLoggingInWithGoogle = true
            auth.signInWithCredential(credential)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        account.email?.let { googleEmail ->
                            database.orderByChild("email").equalTo(googleEmail)
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        isLoggingInWithGoogle = false
                                        if (snapshot.exists()) {
                                            val userSnapshot = snapshot.children.first()
                                            val userName = userSnapshot.child("name").getValue(String::class.java) ?: ""
                                            onLoginSuccess(userName)
                                            navController.navigate(Screen.Home.route)
                                        } else {
                                            errorMessage = "Esta cuenta de Google no está registrada. Por favor regístrate primero."
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        isLoggingInWithGoogle = false
                                        errorMessage = "Error al verificar el registro: ${error.message}"
                                        Log.e("LoginScreen", "Error al leer de la base de datos", error.toException())
                                    }
                                })
                        } ?: run {
                            isLoggingInWithGoogle = false
                            errorMessage = "No se pudo obtener el correo electrónico de la cuenta de Google."
                        }
                    } else {
                        isLoggingInWithGoogle = false
                        errorMessage = "Error de inicio con Google: ${authResult.exception?.message}"
                    }
                }
        } catch (e: Exception) {
            isLoggingInWithGoogle = false
            errorMessage = "Error de autenticación con Google: ${e.localizedMessage}"
        }
    }

    fun loginUser() {
        if (email.isEmpty() || password.isEmpty()) {
            errorMessage = "Por favor ingresa todos los campos."
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    database.child(auth.currentUser?.uid ?: "").child("name")
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val userName = snapshot.getValue(String::class.java) ?: ""
                                onLoginSuccess(userName)
                                navController.navigate(Screen.Home.route)
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.e("LoginScreen", "Error al leer el nombre del usuario", error.toException())
                                navController.navigate(Screen.Home.route)
                            }
                        })
                } else {
                    errorMessage = "Error de inicio de sesión: ${task.exception?.message}"
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_hand_wave),
                contentDescription = "Hand Wave Icon",
                modifier = Modifier.size(52.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "¡Qué bueno verte de nuevo!",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = {
                    selectedTab = "Ingresar"
                    navController.navigate(Screen.Login.route)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Ingresar",
                    color = if (selectedTab == "Ingresar") Color(0xFFD81B60) else Color.Gray,
                    fontSize = 18.sp
                )
            }
            TextButton(
                onClick = {
                    selectedTab = "Registrarse"
                    navController.navigate(Screen.Register.route)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Registrarse",
                    color = if (selectedTab == "Registrarse") Color(0xFFD81B60) else Color.Gray,
                    fontSize = 18.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { loginUser() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD81B60)),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isLoggingInWithGoogle
                ) {
                    Text("Ingresar", color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = {
                        navController.navigate(Screen.RecuperarContrasena.route)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("¿Olvidaste tu contraseña?", color = Color(0xFFD81B60))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        googleSignInClient.signOut().addOnCompleteListener {
                            val signInIntent = googleSignInClient.signInIntent
                            launcher.launch(signInIntent)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.LightGray),
                    elevation = ButtonDefaults.buttonElevation(4.dp),
                    enabled = !isLoggingInWithGoogle
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_google),
                            contentDescription = "Login con Google",
                            modifier = Modifier.size(240.dp),
                            tint = Color.Unspecified
                        )
                    }
                }

                if (isLoggingInWithGoogle) {
                    Spacer(modifier = Modifier.height(8.dp))
                    CircularProgressIndicator()
                }
            }
        }
    }
}