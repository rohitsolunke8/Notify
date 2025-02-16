package com.example.notify.ui_layer.signup

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notify.models.user.UserRequest
import com.example.notify.models.user.UserResponse
import com.example.notify.repo.UserRepository
import com.example.notify.utils.Constants.TOKEN
import com.example.notify.utils.NetworkResult
import com.example.notify.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        MutableStateFlow<NetworkResult<Response<UserResponse>>>(NetworkResult.Idel())
    val newUser: StateFlow<NetworkResult<Response<UserResponse>>> = _newUser.asStateFlow()

    fun userAuth(userRequest: UserRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.registerAuth(userRequest).collect { state ->
                when (state) {

                    is NetworkResult.Error -> {
                        _newUser.update {
                            NetworkResult.Error(message = state.message, loading = false)
                        }
                    }

                    is NetworkResult.Loading -> {
                        _newUser.update {
                            NetworkResult.Loading(loading = true)
                        }
                    }

                    is NetworkResult.Success -> {
                        TokenManager.saveToken(
                            state.data?.body()!!.token,
                            key = stringPreferencesKey(TOKEN),
                            context = context
                        )

                        _newUser.update {
                            NetworkResult.Success(data = state.data, loading = false)
                        }
                    }

                    is NetworkResult.Idel -> {}
                }

            }
        }
    }

    fun resetState() {
        _newUser.update {
            NetworkResult.Idel()
        }
    }
}