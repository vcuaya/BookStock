package com.example.bookstock

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hivebraintech.bookstock.R
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.regex.Pattern

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    // Lista de productos
    private var data: List<Products> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Elementos del RecyclerView
        val tvwId: TextView = itemView.findViewById(R.id.txvIdStock)
        val tvwtitle: TextView = itemView.findViewById(R.id.txvTitleStock)
        val tvwAuthor: TextView = itemView.findViewById(R.id.txvAuthorStock)
        val tvwYear: TextView = itemView.findViewById(R.id.txvYearStock)
        val tvwPrice: TextView = itemView.findViewById(R.id.txvPriceStock)
        val image: ImageView = itemView.findViewById(R.id.ivBookImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.lista_item_libro, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Imagen por defecto
        val defaultImageResource = R.drawable.default_book_image

        // Variable para formato de moneda
        val format: NumberFormat = NumberFormat.getCurrencyInstance()

        // Obtener el elemento en la posición dada
        val item = data[position]

        // Mostrar elementos en el Item correspondiente
        holder.tvwId.text = "ID: " + item.id.toString()
        holder.tvwtitle.text = item.title
        holder.tvwAuthor.text = "Autor: " + item.author
        holder.tvwYear.text = "Año de publicación: " + item.year.toString()
        holder.tvwPrice.text = (format.format(item.price)).toString()

        // Verificar si la URL no es nula ni está vacía
        if (item.url != null && item.url.isNotEmpty() && !isURL(item.url)) {
            // Cargar la imagen usando Picasso
            Picasso.get().load(item.url).into(holder.image)

            Log.d("ProductsAdapter", "URL válida: ${item.url}.")
        } else {
            // Si la URL es nula o vacía, cargar una imagen predeterminada desde recursos
            holder.image.setImageResource(defaultImageResource)

            Log.d("ProductsAdapter", "URL inválida: ${item.url}.")
        }

        Log.d(
            "ProductsAdapter",
            "" +
                    "ID: ${holder.tvwId.text.toString()}, " +
                    "Title: ${holder.tvwtitle.text.toString()}, " +
                    "Author: ${holder.tvwAuthor.text.toString()}, " +
                    "Year: ${holder.tvwYear.text.toString()}, " +
                    "Price: ${holder.tvwPrice.text.toString()}, " +
                    "Url: ${holder.image}."
        )
    }

    override fun getItemCount(): Int {
        // Devolver el tamaño actual de la lista de datos
        return data.size
    }

    fun setData(data: List<Products>) {
        // Actualizar la lista de datos con la nueva lista
        this.data = data

        // Notificar al adaptador que los datos han cambiado, lo que actualizará la interfaz de usuario
        notifyDataSetChanged()
    }

    fun isURL(url: String): Boolean {
        val urlPattern = Pattern.compile(
            "^((http|https|ftp)://)?([a-zA-Z0-9-]+\\.)?[a-zA-Z]{2,}(:[0-9]+)?(/.*)?\$"
        )

        val matcher = urlPattern.matcher(url)

        return matcher.matches()
    }
}