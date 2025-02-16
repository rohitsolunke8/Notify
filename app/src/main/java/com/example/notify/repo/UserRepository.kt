package com.example.notify.repo

import com.example.notify.api.UserApi
import com.example.notify.models.user.UserRequest
import com.example.notify.models.user.UserResponse
import com.example.notify.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {


    fun registerAuth(userRequest: UserRequest): Flow<NetworkResult<Response<UserResponse>>> =
        flow {

            emit(NetworkResult.Loading(loading = true))
            try {
                val response = userApi.signup(userRequest)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(data = response, loading = false))
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    emit(NetworkResult.Error(errorObj.getString("message"), loading = false))
                } else {
                    emit(NetworkResult.Error(response.errorBody().toString(), loading = false))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message, loading = false))
            }
        }

    fun loginAuth(userRequest: UserRequest): Flow<NetworkResult<Response<UserResponse>>> =
        flow {
            emit(NetworkResult.Loading(loading = true))
            try {
                val response = userApi.signin(userRequest)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(data = response, loading = false))
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    emit(NetworkResult.Error(errorObj.getString("message"), loading = false))
                } else {
                    emit(NetworkResult.Error(response.errorBody().toString(), loading = false))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message, loading = false))
            }
        }
}