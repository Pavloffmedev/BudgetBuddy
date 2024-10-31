package com.pavloffmedev.budgetbuddy

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pavloffmedev.budgetbuddy.databinding.ActivityMainBinding
import com.pavloffmedev.budgetbuddy.viewmodelfactories.MainActivityModelFactory
import com.pavloffmedev.budgetbuddy.viewmodels.MainActivityModel

class MainActivity : AppCompatActivity() {

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
                    binding.startSettingsLay.startAnimationOpenLayout()
                }

                else -> {}
            }
        }
    }


    private fun views() {
        val navController = findNavController(R.id.nav_host_fragment_activity_general)
        binding.navView.setupWithNavController(navController)
        binding.startSettingsLay.visible(false)
    }
}