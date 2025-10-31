package com.example.jetpackcomposesample.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposesample.data.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailBottomSheet(
    user: User,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "User Details",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            DetailRow(label = "Name", value = user.name)
            DetailRow(label = "Username", value = user.username)
            DetailRow(label = "Email", value = user.email)
            DetailRow(label = "User ID", value = user.id.toString())

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Close")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}