package com.pavloffmedev.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pavloffmedev.budgetbuddy.databinding.ActivityMainBinding
import com.pavloffmedev.budgetbuddy.viewmodelfactories.MainActivityModelFactory
import com.pavloffmedev.budgetbuddy.viewmodels.MainActivityModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnClickListener {

    private val vm: MainActivityModel by viewModels { MainActivityModelFactory(this) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribe()
        views()
    }


    private fun subscribe() {
        vm.activityFlagLive.observe(this) {
            when (it) {
                MainActivityFlag.NEED_TO_LOGIN -> {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                }

                MainActivityFlag.ADVERTISING -> {}

                MainActivityFlag.NEED_START_SETTINGS -> {
                    binding.loadingLay.visible(false)
                    binding.startSettingsLay.startAnimationOpenLayout()
                }

                else -> {}
            }
        }

        vm.addWastesVisibilityLive.observe(this) {
            if (it) {
                binding.addWastesLay.startAnimationOpenLayout()
            }
            else {
                binding.addWastesLay.startAnimationCloseLayout()
            }
        }

        vm.loadingVisibilityLive.observe(this) {
            if (it) {
                binding.loadingLay.startAnimationOpenLayout()
            }
            else {
                binding.loadingLay.startAnimationCloseLayout()
            }
        }

        vm.needStartSettingsVisibilityLive.observe(this) {
            if (it) {
                binding.startSettingsLay.startAnimationOpenLayout()
            }
            else {
                binding.startSettingsLay.startAnimationCloseLayout()
            }
        }
    }


    private fun views() {
        val navController = findNavController(R.id.nav_host_fragment_activity_general)
        binding.navView.setupWithNavController(navController)
        binding.startSettingsLay.visible(false)
        binding.loadingLay.visible(false)

        lifecycleScope.launch {
            listOf(binding.addAddWastesButton, binding.cancelAddWastesButton, binding.confirmStart).forEach { view ->
                view.setOnClickListener(this@MainActivity)
            }
        }

    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.cancelAddWastesButton -> {
                vm.changeAddWastesLayVisibility(false)
            }

            R.id.addAddWastesButton -> {
                val wastesSum = binding.addWastesField.editText?.text.toString().toIntOrNull()

                if (wastesSum == null) {
                    binding.addWastesField.error = getString(R.string.budget_buddy_17)
                    return
                }
                else {
                    binding.addWastesField.error = null
                }

                vm.postWasteData(wastesSum, binding.addWastesCategoriesChipGroup.getCheckedIndex())
                binding.addWastesField.editText?.text = null
                binding.addWastesCategoriesChipGroup.check(R.id.categoryChip1)
            }

            R.id.confirmStart -> {
                val nameInputted = binding.nameField.editText?.text.toString()
                val monthLimitInputted = binding.wastesField.editText?.text.toString().toIntOrNull()

                if (nameInputted.isEmpty()) {
                    binding.nameField.error = getString(R.string.budget_buddy_17)
                    return
                }
                else {
                    binding.nameField.error = null
                }

                if (monthLimitInputted == null) {
                    binding.wastesField.error = getString(R.string.budget_buddy_17)
                    return
                }
                else {
                    binding.wastesField.error = null
                }

                vm.postStartSettings(nameInputted, monthLimitInputted)
            }
        }
    }
}