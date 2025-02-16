package com.example.notify.ui_layer.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notify.models.user.UserRequest
import com.example.notify.models.user.UserResponse
import com.example.notify.repo.UserRepository
import com.example.notify.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _existingUser =
        MutableStateFlow<NetworkResult<Response<UserResponse>>>(NetworkResult.Idel())
    val existingUserViewModel = _existingUser.asStateFlow()


    fun loginAuth(userRequest: UserRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.loginAuth(userRequest).collectLatest { login ->
                when (login) {

                    is NetworkResult.Error -> {
                        _existingUser.update {
                            NetworkResult.Error(login.message, loading = login.loading)
                        }
                    }

                    is NetworkResult.Idel -> {
                        _existingUser.update {
                            NetworkResult.Idel()
                        }
                    }

                    is NetworkResult.Loading -> {
                        _existingUser.update {
                            NetworkResult.Loading(loading = login.loading)
                        }
                    }

                    is NetworkResult.Success -> {
                        _existingUser.update {
                            NetworkResult.Success(data = login.data!!, loading = login.loading)
                        }
                    }

                }
            }
        }
    }

    fun resetState() {
        _existingUser.update {
            NetworkResult.Idel()
        }
    }
}