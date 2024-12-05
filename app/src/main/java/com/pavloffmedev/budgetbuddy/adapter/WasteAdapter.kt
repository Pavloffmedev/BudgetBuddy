package com.pavloffmedev.budgetbuddy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pavloffmedev.budgetbuddy.databinding.ItemWasteBinding
import com.pavloffmedev.budgetbuddy.`object`.Waste

class WasteAdapter(private val objects: ArrayList<Waste>) : RecyclerView.Adapter<WasteAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : ItemWasteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) = with(binding) {
            val data = objects[position]


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WasteAdapter.ViewHolder =
        ViewHolder(ItemWasteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: WasteAdapter.ViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount(): Int = objects.count()
}