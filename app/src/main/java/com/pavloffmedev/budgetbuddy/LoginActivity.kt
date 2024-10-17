package com.pavloffmedev.budgetbuddy

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.pavloffmedev.budgetbuddy.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribe()
        views()
    }


    private fun subscribe() {

    }


    private fun views() {
        binding.sendCode.isEnabled = false
        binding.emailInputText.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.sendCode.isEnabled = isEmailValid(p0?.toString()?: "")
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun onClick(view: View) {
        view.startAnimationClick()

        when(R.id.sendCode) {
            R.id.sendCode -> {
                binding.sendCode.isEnabled = false
            }
        }
    }
}