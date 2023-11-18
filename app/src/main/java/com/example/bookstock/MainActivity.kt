package com.example.bookstock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Variables de elementos de Login
        val edtEmail:EditText = findViewById(R.id.edtEmailLogin)
        val edtPassword:EditText = findViewById(R.id.edtPasswordLogin)
        val txvCrearCuenta:TextView = findViewById(R.id.txvCrearCuentaLogin)

        //Botón Ingresar
        val btnIngresar:Button = findViewById(R.id.btnIngresarLogin)
        //Listener para botón Ingresar
        btnIngresar.setOnClickListener {
            //Cambia a HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        //Listener para Crear Cuenta
        txvCrearCuenta.setOnClickListener(){
            //Cambia a SignUp Activity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}