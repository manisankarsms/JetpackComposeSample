package com.example.jetpackcomposesample.ui.home

import com.example.jetpackcomposesample.data.model.User

sealed class UsersState {
    object Loading : UsersState()
    data class Success(val users: List<User>) : UsersState()
    data class Error(val message: String) : UsersState()
}