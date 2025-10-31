package com.example.jetpackcomposesample.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposesample.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repo = UserRepository()

    private val _usersState = MutableStateFlow<UsersState>(UsersState.Loading)
    val usersState: StateFlow<UsersState> = _usersState

    init {
        loadUsers()
    }

    fun loadUsers() {
        _usersState.value = UsersState.Loading

        viewModelScope.launch {
            val result = repo.fetchUsers()
            _usersState.value = if (result.isSuccess) {
                UsersState.Success(result.getOrNull() ?: emptyList())
            } else {
                UsersState.Error(result.exceptionOrNull()?.message ?: "Failed to load users")
            }
        }
    }
}