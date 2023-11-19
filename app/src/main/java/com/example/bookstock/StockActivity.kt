package com.hivebraintech.bookstock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstock.Products
import com.example.bookstock.ProductsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StockActivity : AppCompatActivity() {
    // Variable de autenticación de Firebase
    // private lateinit var firebaseAuth: FirebaseAuth

    // Adaptador para la lista de productos
    private lateinit var adapter: ProductsAdapter

    // Vista de reciclaje para mostrar la lista de productos
    private lateinit var recyclerView: RecyclerView

    // Instancia de Firebase Firestore para acceder a la base de datos
    private val db = FirebaseFirestore.getInstance()

    // Colección específica en la base de datos para almacenar productos
    private val collection = db.collection("products")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        adapter = ProductsAdapter()
        recyclerView = findViewById(R.id.rvwDataStock)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        getCollection()
    }

    private fun getCollection() {
        collection.get()
            .addOnSuccessListener { querySnapshot ->
                val listProducts = mutableListOf<Products>()
                for (document in querySnapshot) {
                    val id = document.id
                    val author = document.getString("author")
                    val price = document.getDouble("price")
                    val title = document.getString("title")
                    val year = document.getLong("year")?.toInt()

                    if (
                        id != null &&
                        author != null &&
                        price != null &&
                        title != null &&
                        year != null
                    ) {
                        val products = Products(id, author, price, title, year)
                        listProducts.add((products))

                        // To Debug
                        Log.d(
                            "StockActivity",
                            "ID: $id, Author: $author, Price: $price, Title: $title, Year: $year"
                        )
                    }
                }
                adapter.setData(listProducts)
            }
            .addOnFailureListener { e ->
                // To Debug
                Log.e("StockActivity", "No se pudo obtener los datos de Firebase. Error: $e")
            }
    }
}