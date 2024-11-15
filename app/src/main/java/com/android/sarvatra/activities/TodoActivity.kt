package com.android.sarvatra.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.sarvatra.R
import com.android.sarvatra.databinding.ActivityTodoBinding
import com.android.sarvatra.utils.PreferencesHelper
import com.android.sarvatra.utils.isNightTime

class TodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoBinding
    private lateinit var prefsHelper: PreferencesHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefsHelper = PreferencesHelper(this)
        setupUI()
    }

    private fun setupUI() {
        val user = prefsHelper.getUserFromPreferences()
        if (isNightTime()) {
            binding.root.setBackgroundResource(R.drawable.night_background)
        } else {
            binding.root.setBackgroundResource(R.drawable.app_background)
        }
    }
}