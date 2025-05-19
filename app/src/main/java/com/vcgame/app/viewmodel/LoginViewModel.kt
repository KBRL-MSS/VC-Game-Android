package com.vcgame.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _loginState = MutableLiveData<String>()
    val loginState: LiveData<String> = _loginState

    fun login(username: String, password: String) {
        if (username == "user" && password == "pass") {
            _loginState.value = "Success"
        } else {
            _loginState.value = "Failure"
        }
    }
}
