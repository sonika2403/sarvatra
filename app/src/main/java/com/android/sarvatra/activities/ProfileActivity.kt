package com.android.sarvatra.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.sarvatra.R
import com.android.sarvatra.database.AppDatabase
import com.android.sarvatra.database.user.User
import com.android.sarvatra.databinding.ActivityProfileBinding
import com.android.sarvatra.utils.PreferencesHelper
import com.android.sarvatra.viewmodel.AuthRepository
import com.android.sarvatra.viewmodel.AuthViewModel
import com.android.sarvatra.viewmodel.AuthViewModelFactory
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var database: AppDatabase
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository(PreferencesHelper(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(applicationContext)

        val email = intent.getStringExtra("EMAIL") ?: ""
        val username = intent.getStringExtra("USERNAME") ?: ""
        val password = intent.getStringExtra("PASSWORD") ?: ""

        Log.d("ProfileActivity", "Email: $email")
        Log.d("ProfileActivity", "Username: $username")
        Log.d("ProfileActivity", "Password: $password")

        authViewModel.isLoggedIn.observe(this) { loggedIn ->
            if (loggedIn) {
                navigateToHome()
            }
        }

        binding.btnSave.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val dob = binding.dob.text.toString().trim()
            val phone = binding.phone.text.toString().trim()
            val gender = getSelectedGender()

            if (name.isNotEmpty() && dob.isNotEmpty() && phone.isNotEmpty()) {
                val user = User(
                    username = username.trim(),
                    name = name,
                    password = password,
                    email = email.trim(),
                    gender = gender,
                    mobile = phone,
                    dateOfBirth = dob
                )

                lifecycleScope.launch {
                    try {
                        val existingUser = database.userDao().getUserByUsername(username)
                        if (existingUser == null) {
                            database.userDao().insertUser(user)
                            runOnUiThread {
                                Toast.makeText(this@ProfileActivity, "Profile saved", Toast.LENGTH_SHORT).show()
                            }
                            authViewModel.login()
                        } else {
                            Toast.makeText(this@ProfileActivity, "User already exists", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e("ProfileActivity", "Error saving user: ${e.message}")
                        runOnUiThread {
                            Toast.makeText(this@ProfileActivity, "Failed to save profile", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this@ProfileActivity, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun getSelectedGender(): String {
        return when (binding.genderRadioGroup.checkedRadioButtonId) {
            R.id.radioMale -> "Male"
            R.id.radioFemale -> "Female"
            R.id.radioOther -> "Other"
            else -> "Other"
        }
    }
}
