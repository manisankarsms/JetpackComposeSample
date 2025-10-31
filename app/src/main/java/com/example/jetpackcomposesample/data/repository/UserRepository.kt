package com.example.jetpackcomposesample.data.repository

import com.example.jetpackcomposesample.data.api.RetrofitClient
import com.example.jetpackcomposesample.data.model.LoginRequest
import com.example.jetpackcomposesample.data.model.Post
import com.example.jetpackcomposesample.data.model.User

class UserRepository {
    private val api = RetrofitClient.instance

    suspend fun performLogin(username: String, password: String): Result<User> {
        return try {
//            val response = api.login(LoginRequest(username, password))
            val response = User(123, "Mani", "mani@gmail.com", "manisankarsms")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchUsers(): Result<List<User>> {
        return try {
            val users = api.getUsers()
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchPosts(): Result<List<Post>> {
        return try {
            val posts = api.getPosts()
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}