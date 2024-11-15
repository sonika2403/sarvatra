package com.android.sarvatra.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.sarvatra.database.AppDatabase
import com.android.sarvatra.databinding.ActivityHomeBinding
import com.android.sarvatra.utils.PreferencesHelper
import com.android.sarvatra.utils.isNightTime
import kotlinx.coroutines.launch
import com.android.sarvatra.R

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var backPressedOnce = false
    private lateinit var database: AppDatabase
    private val backPressHandler = Handler(Looper.getMainLooper())

    private lateinit var prefsHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(applicationContext)
        prefsHelper = PreferencesHelper(this)

        loadUserData()
        setupUI()
        setupCardListeners()
    }

    private fun loadUserData() {
        lifecycleScope.launch {
            try {
                val user = database.userDao().getFirstUser()
                prefsHelper.saveUserToPreferences(user)
            } catch (e: Exception) {
                showToast("Failed to load user data")
                e.printStackTrace()
            }
        }
    }

    private fun setupUI() {
        val user = prefsHelper.getUserFromPreferences()
        if (isNightTime()) {
            binding.root.setBackgroundResource(R.drawable.night_background)
            binding.greetingText.setText(getString(R.string.good_evening, user?.name))
            binding.greetingText.setTextColor(Color.parseColor("#D1E7FF"))
            binding.sunAnimation.visibility = View.INVISIBLE
            binding.nightAnimation.visibility = View.VISIBLE
        } else {
            binding.greetingText.setText(getString(R.string.good_day, user?.name))
            binding.sunAnimation.visibility = View.VISIBLE
            binding.nightAnimation.visibility = View.GONE
        }
    }

    private fun setupCardListeners() {
        binding.meditationCard.setOnClickListener {
            showToast(getString(R.string.meditation))
            val intent = Intent(this, MeditationActivity::class.java)
            startActivity(intent)
        }

        binding.journalCard.setOnClickListener {
            showToast(getString(R.string.journal))
            val intent = Intent(this, JournalActivity::class.java)
            startActivity(intent)
        }

        binding.todoCard.setOnClickListener {
            showToast(getString(R.string.todo_list))
            val intent = Intent(this, TodoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@HomeActivity, message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (backPressedOnce) {
            finishAffinity()
        } else {
            backPressedOnce = true
            showToast(getString(R.string.press_back_again_to_exit))
            backPressHandler.postDelayed({ backPressedOnce = false }, 3000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressHandler.removeCallbacksAndMessages(null)
    }
}
