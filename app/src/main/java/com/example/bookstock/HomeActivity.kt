package com.example.bookstock

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //Variables de elementos de Home
        val txvUsuario: TextView = findViewById(R.id.txvUsuarioHome)
        val edtEmail: TextView = findViewById(R.id.txvEmailHome)
        val edtTitulo: EditText = findViewById(R.id.edtTituloHome)
        val edtAutor: EditText = findViewById(R.id.edtAutorHome)
        val edtAnio: EditText = findViewById(R.id.edtAnioHome)
        val edtPrecio: EditText = findViewById(R.id.edtPrecioHome)
        //TextView para vista de Libro Agregado
        val  txvTituloAgregado:TextView = findViewById(R.id.txvTituloAgregadoHome)
        val  txvAutorAgregado:TextView = findViewById(R.id.txvAutorAgregadoHome)
        val  txvAnioAgregado:TextView = findViewById(R.id.txvAnioAgregadoHome)
        val  txvPrecioAgregado:TextView = findViewById(R.id.txvPrecioAgregadoHome)
        //Botones
        val btnAgregar: Button = findViewById(R.id.btnAgregarHome)
        val btnConsultar: Button = findViewById(R.id.btnConsultarHome)

        //Listener para botón Agregar
        btnAgregar.setOnClickListener {
            val tituloAgregado = edtTitulo.text.toString()
            val autorAgregado = edtAutor.text.toString()
            val anioAgregado = edtAnio.text.toString()
            val precioAgregado = edtPrecio.text.toString()
            val tituloAgregadoFormatted = "Título: $tituloAgregado"
            val autorAgregadoFormatted = "Autor: $autorAgregado"
            val anioAgregadoFormatted = "Año: $anioAgregado"
            val precioAgregadoFormatted = "Precio: $precioAgregado"

            txvTituloAgregado.text = tituloAgregadoFormatted
            txvAutorAgregado.text = autorAgregadoFormatted
            txvAnioAgregado.text = anioAgregadoFormatted
            txvPrecioAgregado.text = precioAgregadoFormatted
            Toast.makeText(this, "LIBRO AGREGADO", Toast.LENGTH_SHORT).show()
        }

        //Listener para botón Consultar
        btnConsultar.setOnClickListener {
            //Cambia a StockActivity
            val intent = Intent(this, StockActivity::class.java)
            startActivity(intent)
        }

    }
}