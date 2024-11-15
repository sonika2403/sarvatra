package com.android.sarvatra.utils

import android.content.Context
import android.content.SharedPreferences
import com.android.sarvatra.database.user.User

class PreferencesHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)

    // Function to set the login status
    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    // Function to check the login status
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // Function to save user data to preferences
    fun saveUserToPreferences(user: User?) {
        prefs.edit().apply {
            putString(KEY_USERNAME, user?.username)
            putString(KEY_NAME, user?.name)
            putString(KEY_PASSWORD, user?.password)
            putString(KEY_PHOTO_URI, user?.photoUri)
            putString(KEY_EMAIL, user?.email)
            putString(KEY_LOGIN_TIME, user?.loginTime)
            putString(KEY_GENDER, user?.gender)
            putString(KEY_MOBILE, user?.mobile)
            putString(KEY_DATE_OF_BIRTH, user?.dateOfBirth)
            putString(KEY_ROLE, user?.role)
            putString(KEY_TIER, user?.tier)
            apply()
        }
    }

    // Function to get user data from preferences
    fun getUserFromPreferences(): User? {
        val username = prefs.getString(KEY_USERNAME, null)
        val name = prefs.getString(KEY_NAME, null)
        val password = prefs.getString(KEY_PASSWORD, null)
        val photoUri = prefs.getString(KEY_PHOTO_URI, null)
        val email = prefs.getString(KEY_EMAIL, null)
        val loginTime = prefs.getString(KEY_LOGIN_TIME, null)
        val gender = prefs.getString(KEY_GENDER, null)
        val mobile = prefs.getString(KEY_MOBILE, null)
        val dateOfBirth = prefs.getString(KEY_DATE_OF_BIRTH, null)
        val role = prefs.getString(KEY_ROLE, null)
        val tier = prefs.getString(KEY_TIER, null)

        return if (username != null && name != null) {
            // Create and return a User object from the data in preferences
            User(
                username = username,
                name = name,
                password = password.toString(),
                photoUri = photoUri,
                email = email.toString(),
                loginTime = loginTime.toString(),
                gender = gender.toString(),
                mobile = mobile.toString(),
                dateOfBirth = dateOfBirth.toString(),
                role = role.toString(),
                tier = tier.toString()
            )
        } else {
            null
        }
    }


    fun clearUserData() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val USER_PREFS = "user_prefs"
        private const val KEY_IS_LOGGED_IN = "IS_LOGGED_IN"
        private const val KEY_USERNAME = "USERNAME"
        private const val KEY_NAME = "NAME"
        private const val KEY_PASSWORD = "PASSWORD"
        private const val KEY_PHOTO_URI = "PHOTO_URI"
        private const val KEY_EMAIL = "EMAIL"
        private const val KEY_LOGIN_TIME = "LOGIN_TIME"
        private const val KEY_GENDER = "GENDER"
        private const val KEY_MOBILE = "MOBILE"
        private const val KEY_DATE_OF_BIRTH = "DATE_OF_BIRTH"
        private const val KEY_ROLE = "ROLE"
        private const val KEY_TIER = "TIER"
    }
}
