package com.hivebraintech.bookstock

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstock.ProductsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class HomeActivity : AppCompatActivity() {
    // Variable de autenticación de Firebase
    private lateinit var firebaseAuth: FirebaseAuth

    // Instancia de Firebase Firestore para acceder a la base de datos
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inicialización de la variable de autenticación
        firebaseAuth = Firebase.auth

        // Usuario actual
        val currentUser = firebaseAuth.currentUser
        val uid = currentUser?.uid

        // Elementos de Home
        val tvwUserName: TextView = findViewById(R.id.txvUsuarioHome)
        val tvwEmail: TextView = findViewById(R.id.txvEmailHome)

        // Mostrar datos del usuario actual
        if (uid != null) {
            gedUserByUid(uid) { userName, email ->
                tvwUserName.text = userName
                tvwEmail.text = email
            }
        }

        // Botones
        val btnAgregar: Button = findViewById(R.id.btnAgregarHome)
        val btnConsultar: Button = findViewById(R.id.btnConsultarHome)

        // Listener Agregar
        btnAgregar.setOnClickListener {
            setCollection()
        }

        //Listener Consultar
        btnConsultar.setOnClickListener {
            // Ir a StockActivity
            val intent = Intent(this, StockActivity::class.java)
            startActivity(intent)
        }

    }

    private fun gedUserByUid(uid: String, callback: (String, String) -> Unit) {
        Log.d("Home", "El UID es: $uid")
        val usersCollection = FirebaseFirestore.getInstance().collection("users")

        usersCollection.whereEqualTo("uid", uid).get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents[0]
                val firstName = document.getString("firstName") ?: ""
                val surname = document.getString("surname") ?: ""
                val email = document.getString("email") ?: ""
                val fullName = "$firstName $surname".trim()

                Log.d(
                    "HomeActivity",
                    "El ID: ${document.id}, firstName: ${firstName}, surname: ${surname}, email: ${email}"
                )

                callback(fullName, email)
            } else {
                callback("Usuario Desconocido", "Correo Desconocido")
            }
        }.addOnFailureListener { e ->
            Log.e("HomeActivity", "Error al obtener los datos de usuario en Firestore: $e")
            callback("Usuario Desconocido", "Correo Desconocido")
        }
    }

    private fun setCollection() {
        // Formulario Agregar Producto
        val edtTitle: EditText = findViewById(R.id.edtTituloHome)
        val edtAuthor: EditText = findViewById(R.id.edtAutorHome)
        val edtYear: EditText = findViewById(R.id.edtAnioHome)
        val edtPrice: EditText = findViewById(R.id.edtPrecioHome)

        // Obtener el texto de las entradas
        var title: String = edtTitle.text.toString()
        var author: String = edtAuthor.text.toString()
        var yearString: String = edtYear.text.toString()
        var priceString: String = edtPrice.text.toString()

        var year: Int = 0
        var price: Double = 0.0

        // Validaciones
        if (title.isEmpty()) {
            Log.d("HomeActivity", "Campo <title> en blanco.")

            Toast.makeText(
                baseContext, "Por favor, ingresa el título del libro.", Toast.LENGTH_SHORT
            ).show()
        } else if (author.isEmpty()) {
            Log.d("HomeActivity", "Campo <author> en blanco.")

            Toast.makeText(
                baseContext, "Por favor, ingresa el autor del libro.", Toast.LENGTH_SHORT
            ).show()
        } else if (yearString.isEmpty()) {
            Log.d("HomeActivity", "Campo <year> en blanco.")

            Toast.makeText(
                baseContext,
                "Por favor, ingresa el año de publicación del libro.",
                Toast.LENGTH_SHORT
            ).show()
        } else if (priceString.isEmpty()) {
            Log.d("HomeActivity", "Campo <price> en blanco.")

            Toast.makeText(
                baseContext, "Por favor, ingresa el precio del libro.", Toast.LENGTH_SHORT
            ).show()
        } else {
            year = yearString.toInt()
            price = priceString.toDouble()

            var data = hashMapOf(
                "title" to title, "author" to author, "year" to year, "price" to price
            )
            db.collection("products").add(data).addOnSuccessListener { documentReference ->
                Log.d(
                    "HomeActivity",
                    "Registro agregado correctamente con ID: ${documentReference.id}."
                )

                // Obtener TextViews por actualizar
                val id: TextView = findViewById(R.id.txvIdHome)
                val title: TextView = findViewById(R.id.txvTituloAgregadoHome)
                val author: TextView = findViewById(R.id.txvAutorAgregadoHome)
                val year: TextView = findViewById(R.id.txvAnioAgregadoHome)
                val price: TextView = findViewById(R.id.txvPrecioAgregadoHome)

                // Actualizar datos del libro recién agregado
                id.text = "Producto agregado con ID: ${documentReference.id}"
                title.text = data["title"].toString()
                author.text = data["author"].toString()
                year.text = data["year"].toString()
                price.text = data["price"].toString()
            }.addOnFailureListener { e ->
                Log.e("HomeActivity", "No se pudo agregar el registro. Error: $e")

                Toast.makeText(
                    baseContext, "No se pudo agregar el registro. Error: $e", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}