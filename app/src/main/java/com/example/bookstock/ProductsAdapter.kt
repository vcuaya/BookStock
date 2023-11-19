package com.example.bookstock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hivebraintech.bookstock.R

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private var data: List<Products> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvwId: TextView = itemView.findViewById(R.id.txvIdStock)
        val tvwtitle: TextView = itemView.findViewById(R.id.txvTitleStock)
        val tvwAuthor: TextView = itemView.findViewById(R.id.txvAuthorStock)
        val tvwYear: TextView = itemView.findViewById(R.id.txvYearStock)
        val tvwPrice: TextView = itemView.findViewById(R.id.txvPriceStock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.lista_item_libro, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.tvwId.text = item.id.toString()
        holder.tvwtitle.text = item.title
        holder.tvwAuthor.text = item.author
        holder.tvwYear.text = item.year.toString()
        holder.tvwPrice.text = item.price.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<Products>) {
        this.data = data
        notifyDataSetChanged()
    }
}