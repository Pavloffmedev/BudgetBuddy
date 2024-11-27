package com.pavloffmedev.budgetbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pavloffmedev.budgetbuddy.R
import com.pavloffmedev.budgetbuddy.databinding.FragmentHomeBinding
import com.pavloffmedev.budgetbuddy.viewmodelfactories.MainActivityModelFactory
import com.pavloffmedev.budgetbuddy.viewmodels.MainActivityModel

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
}