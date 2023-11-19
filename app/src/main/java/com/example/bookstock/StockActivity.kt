package com.hivebraintech.bookstock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstock.Products
import com.example.bookstock.ProductsAdapter
import com.google.firebase.firestore.FirebaseFirestore

class StockActivity : AppCompatActivity() {

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

        // Crear instancia del adaptador de productos
        adapter = ProductsAdapter()

        // Obtener la referencia al RecyclerView en el diseño
        recyclerView = findViewById(R.id.rvwDataStock)

        // Establecer el adaptador creado para el RecyclerView
        recyclerView.adapter = adapter

        // Establecer un administrador de diseño lineal para organizar los elementos en una lista
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Obtener elementos
        getCollection()
    }

    private fun getCollection() {
        collection.get()
            .addOnSuccessListener { querySnapshot ->
                // Crear una lista mutable para almacenar objetos de tipo Products
                val listProducts = mutableListOf<Products>()

                // Iterar a través de los documentos en el resultado de la consulta
                for (document in querySnapshot) {
                    // Obtener valores específicos del documento de Firestore
                    val id = document.id
                    val author = document.getString("author")
                    val price = document.getDouble("price")
                    val title = document.getString("title")
                    val url = document.getString("url")
                    val year = document.getLong("year")?.toInt()

                    // Verificar que ninguno de los valores obtenidos del documento sea nulo
                    if (
                        id != null &&
                        author != null &&
                        price != null &&
                        title != null &&
                        url != null &&
                        year != null
                    ) {
                        // Crear un objeto Products con los valores obtenidos
                        val products = Products(id, author, price, title, url, year)

                        // Agregar objeto a la lista
                        listProducts.add(products)

                        Log.d(
                            "StockActivity",
                            "ID: $id, Author: $author, Price: $price, Title: $title, URL: $url, Year: $year"
                        )
                    }
                }
                adapter.setData(listProducts)
            }
            .addOnFailureListener { e ->
                Log.e("StockActivity", "No se pudo obtener datos. Error: $e")
            }
    }
}