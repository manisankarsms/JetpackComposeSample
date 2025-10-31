package com.example.jetpackcomposesample.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposesample.data.model.User
import com.example.jetpackcomposesample.ui.home.components.UserDetailBottomSheet
import com.example.jetpackcomposesample.ui.home.components.UserItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val usersState by viewModel.usersState.collectAsState()
    var selectedUser by remember { mutableStateOf<User?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Users List") }
            )
        }
    ) { padding ->
        when (val state = usersState) {
            is UsersState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UsersState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = state.users.size
                    ) { index ->
                        UserItem(
                            user = state.users[index],
                            onClick = { selectedUser = state.users[index] }
                        )
                    }
                }
            }

            is UsersState.Error -> {
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
                        Button(onClick = { viewModel.loadUsers() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }

        // Show bottom sheet when user is selected
        selectedUser?.let { user ->
            UserDetailBottomSheet(
                user = user,
                onDismiss = { selectedUser = null }
            )
        }
    }
}