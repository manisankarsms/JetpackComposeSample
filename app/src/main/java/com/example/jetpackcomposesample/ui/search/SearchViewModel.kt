package com.example.jetpackcomposesample.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposesample.data.model.Post
import com.example.jetpackcomposesample.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repo = UserRepository()

    private val _postsState = MutableStateFlow<PostsState>(PostsState.Loading)
    val postsState: StateFlow<PostsState> = _postsState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        loadPosts()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun loadPosts() {
        _postsState.value = PostsState.Loading

        viewModelScope.launch {
            val result = repo.fetchPosts()
            _postsState.value = if (result.isSuccess) {
                PostsState.Success(result.getOrNull() ?: emptyList())
            } else {
                PostsState.Error(result.exceptionOrNull()?.message ?: "Failed to load posts")
            }
        }
    }

    fun getFilteredPosts(posts: List<Post>, query: String): List<Post> {
        if (query.isEmpty()) return posts
        return posts.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.body.contains(query, ignoreCase = true)
        }
    }
}