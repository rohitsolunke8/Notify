package com.example.notify.api

import com.example.notify.models.user.UserRequest
import com.example.notify.models.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("api/users/signup")
    suspend fun signup(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST("/api/users/signin")
    suspend fun signin(@Body userRequest: UserRequest) : Response<UserResponse>

}