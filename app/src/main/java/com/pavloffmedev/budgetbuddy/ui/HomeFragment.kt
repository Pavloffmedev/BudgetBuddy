package com.pavloffmedev.budgetbuddy.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.color.MaterialColors
import com.pavloffmedev.budgetbuddy.R
import com.pavloffmedev.budgetbuddy.databinding.FragmentHomeBinding
import com.pavloffmedev.budgetbuddy.viewmodelfactories.MainActivityModelFactory
import com.pavloffmedev.budgetbuddy.viewmodels.MainActivityModel
import com.pavloffmedev.budgetbuddy.visible
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val vm: MainActivityModel by activityViewModels { MainActivityModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        views()
        subscribe()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.addWastesButton -> {
                vm.changeAddWastesLayVisibility(true)
            }
        }
    }

    private fun views() {
        listOf(binding.addWastesButton).forEach { view ->
            view.setOnClickListener(this)
        }
    }

    private fun subscribe() {
        vm.userLive.observe(this.viewLifecycleOwner) { user ->
            binding.monthWastesText.text = vm.getCurrencyCount(user.monthWastes)
            binding.monthMoneyRemaining.text = vm.getCurrencyCount(user.monthMoneyRemaining)

            binding.pieChart.visible(false)
            user.monthStats?.let { monthStats ->
                this@HomeFragment.viewLifecycleOwner.lifecycleScope.launch {
                    binding.pieChart.visible(true)

                    val entries = ArrayList<PieEntry>()
                    monthStats.forEach { categoryData ->
                        entries.add(PieEntry(categoryData.index, categoryData.name))
                    }


                    val dataSet = PieDataSet(entries, "")
                    dataSet.colors = ColorTemplate.MATERIAL_COLORS.asList()
                    dataSet.sliceSpace = 3f
                    dataSet.valueTextColor = MaterialColors.getColor(requireContext(), com.google.android.material.R.attr.colorOnSurface, "")
                    dataSet.valueTextSize = 12f
                    dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                    dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                    dataSet.valueLinePart1Length = 0.5f
                    dataSet.valueLinePart2Length = 0.4f
                    dataSet.valueLineColor = Color.TRANSPARENT

                    val pieData = PieData(dataSet)
                    binding.pieChart.data = pieData

                    binding.pieChart.description.isEnabled = false
                    binding.pieChart.isDrawHoleEnabled = false
                    binding.pieChart.setEntryLabelTextSize(16f)
                    binding.pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD)
                    binding.pieChart.setEntryLabelColor(MaterialColors.getColor(requireContext(), com.google.android.material.R.attr.colorOnSurface, ""))
                    binding.pieChart.legend.isEnabled = false
                    binding.pieChart.animateY(500)
                }
            }
        }
    }
}