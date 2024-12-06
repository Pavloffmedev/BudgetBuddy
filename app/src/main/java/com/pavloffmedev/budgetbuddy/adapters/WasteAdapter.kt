package com.pavloffmedev.budgetbuddy.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pavloffmedev.budgetbuddy.databinding.ItemWasteBinding
import com.pavloffmedev.budgetbuddy.formatTime
import com.pavloffmedev.budgetbuddy.objects.Waste

class WasteAdapter(
    private val objects: ArrayList<Waste>,
    private val currency: String,
    private val todayText : String,
    private val yesterdayTex: String
) : RecyclerView.Adapter<WasteAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemWasteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) = with(binding) {
            val data = objects[position]

            wasteSumText.text = "- ${data.sum} $currency"
            wasteTimeText.text = data.time.formatTime(todayText, yesterdayTex)
            wasteCategoryText.text = data.categoryName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WasteAdapter.ViewHolder =
        ViewHolder(ItemWasteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: WasteAdapter.ViewHolder, position: Int) =
        holder.bind(position)

    override fun getItemCount(): Int = objects.count()
}