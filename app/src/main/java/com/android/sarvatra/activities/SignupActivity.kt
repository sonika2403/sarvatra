package com.android.sarvatra.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.sarvatra.database.AppDatabase
import com.android.sarvatra.database.user.User
import com.android.sarvatra.databinding.ActivitySignupBinding
import com.android.sarvatra.utils.isValidEmail
import com.android.sarvatra.utils.isValidUsername
import com.android.sarvatra.utils.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var database: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(applicationContext)

        binding.btnLogin.setOnClickListener {
            handleSignup()
        }
    }

    private fun handleSignup() {
        val email = binding.email.text.toString().trim()
        val username = binding.username.text.toString().trim()
        val password = binding.password.text.toString()
        val rePassword = binding.repass.text.toString()

        when {
            email.isEmpty() -> showToast("Email cannot be empty", this@SignupActivity)
            !isValidEmail(email) -> showToast(
                "Please enter a valid email address",
                this@SignupActivity
            )

            username.isEmpty() -> showToast("Username cannot be empty", this@SignupActivity)
            !isValidUsername(username) -> showToast(
                "Username can only contain alphanumeric characters",
                this@SignupActivity
            )

            password.isEmpty() -> showToast("Password cannot be empty", this@SignupActivity)
            rePassword.isEmpty() -> showToast("Please re-enter your password", this@SignupActivity)
            password != rePassword -> showToast("Passwords do not match", this@SignupActivity)
            else -> {
                showToast("Sign-up successful!", this@SignupActivity)
                CoroutineScope(Dispatchers.IO).launch() {
                    database.userDao().deleteAllUsers()
                }

                val intent = Intent(this, ProfileActivity::class.java).apply {
                    putExtra("EMAIL", email)
                    putExtra("USERNAME", username)
                    putExtra("PASSWORD", password)
                }
                startActivity(intent)
            }
        }
    }
}