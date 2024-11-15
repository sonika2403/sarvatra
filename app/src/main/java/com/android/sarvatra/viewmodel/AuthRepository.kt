package com.android.sarvatra.viewmodel

import com.android.sarvatra.utils.PreferencesHelper

class AuthRepository(private val prefsHelper: PreferencesHelper) {
    fun setLoggedIn(isLoggedIn: Boolean) {
        prefsHelper.setLoggedIn(isLoggedIn)
    }

    fun isLoggedIn(): Boolean {
        return prefsHelper.isLoggedIn()
    }
}
