package com.android.sarvatra.utils

import android.content.Context
import android.widget.Toast
import com.android.sarvatra.activities.SignupActivity

fun isValidEmail(email: String): Boolean {
    // Define the regex pattern for a valid email
    val emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"

    // Match the email with the pattern
    return email.matches(emailPattern.toRegex())
}

fun isValidUsername(username: String): Boolean {
    // Check if the username matches the regex for alphanumeric characters only
    return username.matches("^[a-zA-Z0-9]+$".toRegex())
}

 fun showToast(message: String, context:Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}