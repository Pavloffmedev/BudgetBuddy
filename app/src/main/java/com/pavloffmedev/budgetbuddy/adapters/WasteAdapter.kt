package com.pavloffmedev.budgetbuddy.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.pavloffmedev.budgetbuddy.R
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
            wastePic.setImageResource(getCategoryPic(data.categoryName))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WasteAdapter.ViewHolder =
        ViewHolder(ItemWasteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: WasteAdapter.ViewHolder, position: Int) =
        holder.bind(position)

    override fun getItemCount(): Int = objects.count()

    @DrawableRes
    private fun getCategoryPic(categoryName : String): Int {
        return when(categoryName) {
            "Еда и напитки" -> R.drawable.ic_eats
            "Транспорт" -> R.drawable.ic_transport
            "Коммунальные услуги" -> R.drawable.ic_comunal
            "Развлечения и досуг" -> R.drawable.ic_dosug
            "Здоровье" -> R.drawable.ic_health
            "Одежда" -> R.drawable.ic_styler
            "Образование" -> R.drawable.ic_school
            else -> R.drawable.settings
        }
    }
}