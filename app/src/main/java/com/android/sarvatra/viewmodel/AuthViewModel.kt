package com.android.sarvatra.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>().apply {
        value = repository.isLoggedIn()
    }
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    fun login() {
        repository.setLoggedIn(true)
        _isLoggedIn.value = true
    }

    fun logout() {
        repository.setLoggedIn(false)
        _isLoggedIn.value = false
    }
}
