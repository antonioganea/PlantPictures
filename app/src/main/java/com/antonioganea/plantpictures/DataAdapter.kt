package com.antonioganea.plantpictures


import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View

internal class DataAdapter(
    private var data: List<String>,
    private var images: HashMap<String, Int>
) :
    RecyclerView.Adapter<DataAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemTextView: TextView = view.findViewById(R.id.itemTextView)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.catalog_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        holder.itemTextView.text = item
        holder.itemTextView.setCompoundDrawablesWithIntrinsicBounds(images.get(item) ?: 0, 0, 0, 0);
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun filterList(filterllist: ArrayList<String>) {
        data = filterllist
        notifyDataSetChanged()
    }
}