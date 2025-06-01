package com.example.rapikids.ui

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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
import androidx.compose.foundation.text.KeyboardOptions

data class User(val name: String, val email: String)

@Composable
fun RegisterScreen(navController: NavHostController, onRegistrationSuccess: (String) -> Unit) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance().getReference("users")

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var termsChecked by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("Registrarse") }
    var errorMessage by remember { mutableStateOf("") }
    var isRegisteringWithGoogle by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }

    fun saveUserToDatabase(userId: String?, name: String, email: String, onComplete: () -> Unit) {
        val user = User(name, email)
        userId?.let {
            database.child(it).setValue(user)
                .addOnSuccessListener {
                    Log.d("RegisterScreen", "User data saved to Realtime Database")
                    onComplete()
                    onRegistrationSuccess(name)
                    navController.navigate(Screen.Home.route)
                }
                .addOnFailureListener { e ->
                    Log.e("RegisterScreen", "Error saving user data to Realtime Database", e)
                    Toast.makeText(context, "Error al guardar la información del usuario", Toast.LENGTH_SHORT).show()
                    auth.currentUser?.delete()
                }
        }
    }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("662901884059-t6md0s4gn12h8mslese5uf86rsdcun0r.apps.googleusercontent.com")
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

            isRegisteringWithGoogle = true
            auth.signInWithCredential(credential)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        account.email?.let { googleEmail ->
                            database.orderByChild("email").equalTo(googleEmail)
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        isRegisteringWithGoogle = false
                                        if (snapshot.exists()) {
                                            errorMessage = "Esta cuenta de Google ya está registrada. Por favor, inicia sesión."
                                        } else {
                                            val newUser = authResult.result?.user
                                            val googleName = account.displayName ?: ""
                                            saveUserToDatabase(newUser?.uid, googleName, googleEmail) {
                                                onRegistrationSuccess(googleName)
                                            }
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        isRegisteringWithGoogle = false
                                        errorMessage = "Error al verificar el registro: ${error.message}"
                                        Log.e("RegisterScreen", "Error al leer de la base de datos", error.toException())
                                    }
                                })
                        } ?: run {
                            isRegisteringWithGoogle = false
                            errorMessage = "No se pudo obtener el correo electrónico de la cuenta de Google."
                        }
                    } else {
                        isRegisteringWithGoogle = false
                        errorMessage = "Error de registro con Google: ${authResult.exception?.message}"
                    }
                }
        } catch (e: Exception) {
            isRegisteringWithGoogle = false
            errorMessage = "Error de autenticación con Google: ${e.localizedMessage}"
        }
    }

    fun registerWithEmailPassword() {
        if (termsChecked) {
            if (email.isNotBlank() && password.isNotBlank()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            saveUserToDatabase(user?.uid, name, email) {
                                onRegistrationSuccess(name)
                            }
                        } else {
                            val error = task.exception?.message ?: "Error desconocido"
                            if (error.contains("The email address is already in use")) {
                                Toast.makeText(context, "El correo electrónico ya está registrado", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
                                Log.e("RegisterScreen", "Firebase Auth Error: $error")
                            }
                        }
                    }
            } else {
                Toast.makeText(context, "Por favor, introduce correo y contraseña", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "User Icon", modifier = Modifier.size(56.dp))
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "Bienvenid@", style = MaterialTheme.typography.headlineSmall)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TextButton(
                onClick = {
                    selectedTab = "Ingresar"
                    navController.navigate(Screen.Login.route)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Ingresar", color = if (selectedTab == "Ingresar") Color(0xFFD81B60) else Color.Gray, fontSize = 18.sp)
            }
            TextButton(
                onClick = {
                    selectedTab = "Registrarse"
                    navController.navigate(Screen.Register.route)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Registrarse", color = if (selectedTab == "Registrarse") Color(0xFFD81B60) else Color.Gray, fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = termsChecked, onCheckedChange = { termsChecked = it })
            Text("Términos y condiciones")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red, style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = { registerWithEmailPassword() },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD81B60)),
            shape = RoundedCornerShape(12.dp),
            enabled = !isRegisteringWithGoogle
        ) {
            Text("Registrarse", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("O regístrate con")
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                googleSignInClient.signOut().addOnCompleteListener {
                    val signInIntent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            elevation = ButtonDefaults.buttonElevation(4.dp),
            enabled = !isRegisteringWithGoogle
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Icon(painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Registrarse con Google",
                    modifier = Modifier.size(240.dp), tint = Color.Unspecified)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        if (isRegisteringWithGoogle) {
            Spacer(modifier = Modifier.height(8.dp))
            CircularProgressIndicator()
        }
    }
}