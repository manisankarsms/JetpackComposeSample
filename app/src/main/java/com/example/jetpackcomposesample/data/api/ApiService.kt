package com.example.jetpackcomposesample.data.api

import com.example.jetpackcomposesample.data.model.LoginRequest
import com.example.jetpackcomposesample.data.model.Post
import com.example.jetpackcomposesample.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): User

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("posts")
    suspend fun getPosts(): List<Post>

}