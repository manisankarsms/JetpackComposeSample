package com.example.jetpackcomposesample.ui.search

import com.example.jetpackcomposesample.data.model.Post

sealed class PostsState {
    object Loading : PostsState()
    data class Success(val posts: List<Post>) : PostsState()
    data class Error(val message: String) : PostsState()
}