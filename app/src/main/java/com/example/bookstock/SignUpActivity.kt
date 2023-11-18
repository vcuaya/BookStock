package com.example.bookstock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //Variables de elementos de Signup
        val edtNombre:EditText = findViewById(R.id.edtNombreSignUp)
        val edtApellido:EditText = findViewById(R.id.edtApellidoSignUp)
        val edtEmail: EditText = findViewById(R.id.edtEmailSignUp)
        val edtPassword: EditText = findViewById(R.id.edtPasswordSignUp)
        val edtConfirmPassword: EditText = findViewById(R.id.edtConfirmarPasSignUp)
        val btnCrearCuenta: Button = findViewById(R.id.btnCrearCuentaSignUp)

        btnCrearCuenta.setOnClickListener {
            //Cambia a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}