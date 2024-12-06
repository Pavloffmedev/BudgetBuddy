package com.pavloffmedev.budgetbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavloffmedev.budgetbuddy.R
import com.pavloffmedev.budgetbuddy.adapters.WasteAdapter
import com.pavloffmedev.budgetbuddy.databinding.FragmentStatsBinding
import com.pavloffmedev.budgetbuddy.getCheckedIndex
import com.pavloffmedev.budgetbuddy.viewmodelfactories.MainActivityModelFactory
import com.pavloffmedev.budgetbuddy.viewmodels.MainActivityModel

class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    private val vm : MainActivityModel by activityViewModels { MainActivityModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        vm.onCreateStatsFragment()
        views()
        subscribe()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun views() {
        binding.wasteList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.filterGroup.setOnCheckedStateChangeListener { _, _ ->
            vm.setDaysFilter(binding.filterGroup.getCheckedIndex())
        }
    }

    private fun subscribe() {
        vm.wasteList.observe(this.viewLifecycleOwner) {
            binding.wasteList.adapter = WasteAdapter(
                objects = it,
                currency = "₽",
                todayText = getString(R.string.budget_buddy_22),
                yesterdayTex = getString(R.string.budget_buddy_23)
            )
        }
    }
}