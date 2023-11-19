package com.hivebraintech.bookstock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    // Variable de autenticación
    private lateinit var firebaseAuth: FirebaseAuth

    // Variable de interfaz
    private lateinit var authStateListener: AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Formulario Login
        val edtEmail: EditText = findViewById(R.id.edtEmailLogin)
        val edtPassword: EditText = findViewById(R.id.edtPasswordLogin)

        // Botón Ingresar
        val btnIngresar: Button = findViewById(R.id.btnIngresarLogin)

        // TextView Crear Cuenta
        val txvCrearCuenta: TextView = findViewById(R.id.txvCrearCuentaLogin)

        // Inicialización de la variable de autenticación
        firebaseAuth = Firebase.auth

        // Listener Ingresar
        btnIngresar.setOnClickListener {
            // Obtener el texto de las entradas
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            // Validaciones
            if (email.isEmpty()) {
                Log.d("SignInActivity", "Campo <email> en blanco.")

                Toast.makeText(
                    baseContext,
                    "Por favor, ingresa tu correo electrónico.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password.isEmpty()) {
                Log.d("SignInActivity", "Campo <password> en blanco.")

                Toast.makeText(
                    baseContext,
                    "Por favor, ingresa tu contraseña.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                singIn(email, password)
            }
        }

        // Listener Crear Cuenta
        txvCrearCuenta.setOnClickListener() {
            Log.d("SignInActivity", "Inicio de SignUpActivity.")

            // Ir a SignUpActivity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun singIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                // Verificación de credenciales
                if (task.isSuccessful) {
                    // Usuario actual
                    val user = firebaseAuth.currentUser

                    Log.d("SignInActivity", "Inicio de sesión exitoso. UID: ${user?.uid}.")

                    Toast.makeText(
                        baseContext,
                        "¡Bienvenido!",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Ir a HomeActivity
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.e("SignInActivity", "Error al iniciar sesión: ${task.exception?.message}.")

                    Toast.makeText(
                        baseContext,
                        "Acceso incorrecto: verifica los datos ingresados.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}