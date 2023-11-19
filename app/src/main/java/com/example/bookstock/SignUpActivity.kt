package com.hivebraintech.bookstock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    // Variable de autenticación
    private lateinit var firebaseAuth: FirebaseAuth

    // Instancia de Firebase Firestore para interactuar con la base de datos en la nube
    private val db = FirebaseFirestore.getInstance()

    // Referencia a la colección "users" en la base de datos Firestore
    private val collection = db.collection("users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Formulario SignUp
        val edtNombre: TextView = findViewById(R.id.edtNombreSignUp)
        val edtApellido: TextView = findViewById(R.id.edtApellidoSignUp)
        val edtEmail: TextView = findViewById(R.id.edtEmailSignUp)
        val edtPassword: TextView = findViewById(R.id.edtPasswordSignUp)
        val edtConfirmPassword: TextView = findViewById(R.id.edtConfirmarPasSignUp)
        val btnCrearCuenta: TextView = findViewById(R.id.btnCrearCuentaSignUp)

        // Inicialización de la variable de autenticación
        firebaseAuth = Firebase.auth

        btnCrearCuenta.setOnClickListener {
            // Obtener el texto de las entradas
            val firstName = edtNombre.text.toString()
            val surname = edtApellido.text.toString()
            val email = edtEmail.text.toString()
            val newPassword = edtPassword.text.toString()
            val verifyPassword = edtConfirmPassword.text.toString()

            // Validaciones
            if (firstName.isEmpty()) {
                Log.d("SignUp", "Campo <nombre> en blanco.")

                Toast.makeText(
                    baseContext,
                    "Por favor, ingresa tu(s) nombre(s).",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (surname.isEmpty()) {
                Log.d("SignUp", "Campo <apellido> en blanco.")

                Toast.makeText(
                    baseContext,
                    "Por favor, ingresa tu apellido(s).",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (email.isEmpty()) {
                Log.d("SignUp", "Campo <email> en blanco.")

                Toast.makeText(
                    baseContext,
                    "Por favor, ingresa tu correo.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (newPassword.isEmpty()) {
                Log.d("SignUp", "Campo <password> en blanco.")

                Toast.makeText(
                    baseContext,
                    "Por favor, ingresa tu contraseña.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (verifyPassword.isEmpty()) {
                Log.d("SignUp", "Campo <confirmPassword> en blanco.")

                Toast.makeText(
                    baseContext,
                    "Por favor, ingresa tu contraseña nuevamente.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (newPassword.equals(verifyPassword)) {
                createAccount(email, newPassword, firstName, surname)
            } else {
                Log.d("SignUp", "Las contraseñas no coinciden.")

                Toast.makeText(
                    baseContext,
                    "Por favor, verifica que las contraseñas coincidan.",
                    Toast.LENGTH_SHORT
                ).show()

                // Focus al campo <password> para reescribirlo
                edtPassword.text = ""
                edtConfirmPassword.text = ""
                edtPassword.requestFocus()
            }
        }
    }

    private fun createAccount(email: String, password: String, firstName: String, surname: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                // Verificación de credenciales
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser

                    Log.d("SignUp", "Creación de nuevo usuario con exito. UID: ${user?.uid}.")

                    if (user != null) {
                        val uid = user.uid
                        saveUserData(uid, firstName, surname, email)

                        // Ir a MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.d("SignUp", "No se pudo obtener el UID del usuario.")
                    }
                } else {
                    Log.e("SignUp", "Error al crear el nuevo usuario: ${task.exception?.message}.")

                    Toast.makeText(
                        baseContext,
                        "No se pudo crear la cuenta.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun saveUserData(uid: String, firstName: String, surname: String, email: String) {
        // Mapa Hash para los datos
        val user = hashMapOf(
            "uid" to uid,
            "firstName" to firstName,
            "surname" to surname,
            "email" to email
        )

        db.collection("users")
            // Agregar datos a la colección
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("SignUp", "Datos de usuario asociados correctamente al ID: ${documentReference.id}")

                Toast.makeText(
                    baseContext,
                    "Se creó la cuenta correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { e ->
                Log.e("SignUp", "Error al guardar los datos del usuario, Error: $e")
            }
    }
}