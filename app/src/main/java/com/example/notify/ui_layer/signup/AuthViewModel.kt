package com.example.notify.ui_layer.signup

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notify.models.user.UserRequest
import com.example.notify.models.user.UserResponse
import com.example.notify.repo.UserRepository
import com.example.notify.utils.NetworkResult
import com.example.notify.utils.NotesResult
import com.example.notify.utils.NotifyPreferencesDataStore
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
        MutableStateFlow<NetworkResult<Response<UserResponse>>>(NetworkResult.Loading)
    val newUser: StateFlow<NetworkResult<Response<UserResponse>>> = _newUser.asStateFlow()
    private val tokenManager = NotifyPreferencesDataStore(context)

    fun userAuth(userRequest: UserRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.registerAuth(userRequest).collect { state ->
                when (state) {

                    is NetworkResult.Error -> {
                        _newUser.update {
                            NetworkResult.Error(message = state.message)
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
                            NetworkResult.Success(data = state.data)
                        }
                    }

                    NotesResult.Loading -> {
                        _newUser.update {
                            NetworkResult.Loading
                        }
                    }
                }

            }
        }
    }
}