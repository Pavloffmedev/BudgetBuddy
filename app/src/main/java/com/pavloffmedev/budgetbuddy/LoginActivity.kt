package com.pavloffmedev.budgetbuddy

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.pavloffmedev.budgetbuddy.databinding.ActivityLoginBinding
import com.pavloffmedev.budgetbuddy.viewmodelfactories.LoginActivityModelFactory
import com.pavloffmedev.budgetbuddy.viewmodels.LoginActivityModel

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding : ActivityLoginBinding
    private val vm : LoginActivityModel by viewModels { LoginActivityModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribe()
        views()
        properties()
    }


    private fun subscribe() {
        vm.activityFlags.observe(this) {

        }
    }


    private fun properties() {
        onBackPressedDispatcher.addCallback(this) {
            Snackbar.make(binding.root, R.string.budget_buddy_5, Snackbar.LENGTH_SHORT).show()
        }
    }


    private fun views() {
        binding.sendCode.isEnabled = false
        binding.confirmCode.isEnabled = false

        binding.emailInputText.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (vm.isEmailLoading.value == false) binding.sendCode.isEnabled = isEmailValid(p0?.toString()?: "")
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        listOf(binding.sendCode, binding.confirmCode).forEach { clickableView ->
            clickableView.setOnClickListener(this@LoginActivity)
        }
    }

    override fun onClick(view: View) {
        view.startAnimationClick()

        when(R.id.sendCode) {
            R.id.sendCode -> {
                binding.sendCode.isEnabled = false
                vm.sendCode(binding.emailInputText.editText?.text.toString())
            }
        }
    }
}