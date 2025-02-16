package com.example.notify.ui_layer.viewmodel

import android.util.Patterns
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.notify.models.user.UserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


data class InputUiState(
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val confirmedPassword: String = "",
    val errorMessage: String? = null
)

class FormViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InputUiState())
    val uiState = _uiState.asStateFlow()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmedPassword by mutableStateOf("")
        private set

    var userName by mutableStateOf("")
        private set


    val emailHasErrors by derivedStateOf {
        if (email.isNotEmpty()) {
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }

    val passwordHasError by derivedStateOf {
        if (password.isNotEmpty()) {
            password != confirmedPassword
        } else {
            false
        }
    }

    fun updateUsername(input: String) {
        _uiState.value = _uiState.value.copy(email = input)
    }

    fun updateEmail(input: String) {
        _uiState.value = _uiState.value.copy(username = input)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun updateConfirmPassword(confirmedPassword: String) {
        _uiState.value = _uiState.value.copy(confirmedPassword = confirmedPassword)
    }


    fun validator(
        email: String = _uiState.value.email,
        password: String = _uiState.value.password,
        confirmedPassword: String? = _uiState.value.confirmedPassword
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (email.isEmpty() || password.isEmpty()) {

            result = Pair(false, "All fields are required")

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            result = Pair(false, "Please provide valid email")

        } else if (password.length <= 5) {

            result = Pair(false, "Password length should be greater than 5")

        } else if ( password != confirmedPassword) {
            result = Pair(false, "Confirmed password should be match")
        }
        return result
    }

    fun getUserRequest(): UserRequest {
        return UserRequest(
            email = _uiState.value.email,
            password = _uiState.value.password,
            username = _uiState.value.username
        )
    }
}