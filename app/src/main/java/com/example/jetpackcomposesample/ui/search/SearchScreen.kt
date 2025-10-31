package com.example.jetpackcomposesample.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposesample.data.model.Post
import com.example.jetpackcomposesample.ui.search.components.PostDetailDialog
import com.example.jetpackcomposesample.ui.search.components.PostItem

// Search Screen with Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel()
) {
    val postsState by viewModel.postsState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var selectedPost by remember { mutableStateOf<Post?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search Posts") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                placeholder = { Text("Search posts...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true
            )

            when (val state = postsState) {
                is PostsState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is PostsState.Success -> {
                    val filteredPosts = viewModel.getFilteredPosts(state.posts, searchQuery)

                    if (filteredPosts.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No posts found")
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(count = filteredPosts.size) { index ->
                                PostItem(
                                    post = filteredPosts[index],
                                    onClick = { selectedPost = filteredPosts[index] }
                                )
                            }
                        }
                    }
                }

                is PostsState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = state.message,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.loadPosts() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }

        // Post Detail Dialog
        selectedPost?.let { post ->
            PostDetailDialog(
                post = post,
                onDismiss = { selectedPost = null }
            )
        }
    }
}