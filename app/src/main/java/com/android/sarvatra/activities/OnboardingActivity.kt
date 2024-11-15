package com.android.sarvatra.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.sarvatra.database.AppDatabase
import com.android.sarvatra.databinding.ActivityOnboardingBinding
import com.android.sarvatra.utils.PreferencesHelper
import com.android.sarvatra.viewmodel.AuthRepository
import com.android.sarvatra.viewmodel.AuthViewModel
import com.android.sarvatra.viewmodel.AuthViewModelFactory

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var database: AppDatabase
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(applicationContext)

        val prefsHelper = PreferencesHelper(this)
        val repository = AuthRepository(prefsHelper)
        val factory = AuthViewModelFactory(repository)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        authViewModel.isLoggedIn.observe(this) { loggedIn ->
            if (loggedIn) {
                val intent = Intent(this@OnboardingActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this@OnboardingActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        authViewModel.isLoggedIn.observe(this) { loggedIn ->
            if (loggedIn) {
                val intent = Intent(this@OnboardingActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        authViewModel.isLoggedIn.observe(this) { loggedIn ->
            if (loggedIn) {
                val intent = Intent(this@OnboardingActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}