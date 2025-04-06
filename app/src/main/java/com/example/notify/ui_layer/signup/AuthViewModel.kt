package com.example.notify.ui_layer.signup

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notify.models.user.UserRequest
import com.example.notify.models.user.UserResponse
import com.example.notify.repo.UserRepository
import com.example.notify.ui_layer.navigation.NewNote
import com.example.notify.utils.NetworkResult
import com.example.notify.utils.NotifyPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    @field:SuppressLint("StaticFieldLeak") @ApplicationContext val context: Context,
    private val userRepository: UserRepository,
) : ViewModel() {

    private var _newUser =
        MutableStateFlow<NetworkResult<Response<UserResponse>>>(NetworkResult.Ideal)
    val newUser: StateFlow<NetworkResult<Response<UserResponse>>> = _newUser.asStateFlow()
    private val tokenManager = NotifyPreferencesDataStore(context)

    fun userAuth(userRequest: UserRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.registerAuth(userRequest).collect { state ->
                when (state) {

                    is NetworkResult.Error -> {
                        _newUser.update {
                            NetworkResult.Error(state.message)
                        }

                    }

                    is NetworkResult.Loading -> {
                        _newUser.update {
                            NetworkResult.Loading
                        }
                    }

                    is NetworkResult.Success -> {
                        tokenManager.saveToken(state.data.body()!!.token)
                        _newUser.update {
                            NetworkResult.Success(state.data)
                        }
                    }

                    else -> {
                        _newUser.update {
                            NetworkResult.Ideal
                        }
                    }
                }

            }
        }
    }

    fun userLogin(userRequest: UserRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.loginAuth(userRequest).collectLatest { state ->
                when(state) {
                    is NetworkResult.Error -> {
                        _newUser.update {
                            NetworkResult.Error(state.message)
                        }
                    }

                    NetworkResult.Loading -> {
                        _newUser.update {
                            NetworkResult.Loading
                        }
                    }
                    is NetworkResult.Success -> {
                        tokenManager.saveToken(state.data.body()!!.token)
                        _newUser.update {
                            NetworkResult.Success(state.data)
                        }
                    }

                    NetworkResult.Ideal -> {
                        _newUser.update {
                            NetworkResult.Ideal
                        }
                    }
                }
            }
        }
    }


    fun resetState() {
        _newUser.update {
            NetworkResult.Ideal
        }
    }
}
