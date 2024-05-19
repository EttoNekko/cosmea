package com.example.messages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.Background
import com.example.designsystem.icon.Icons
import com.example.designsystem.theme.CosmeaTheme

@Composable
internal fun AddFriendRoute(
    onBackPressed: () -> Unit
) {
    AddFriendScreen(
        onBackPressed = onBackPressed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFriendScreen(
    onBackPressed: () -> Unit
) {
    var username by remember { mutableStateOf("") }

    Background {
        LazyColumn {
            // Create a server
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopAppBar(
                        title = {
                            Text(text = "Add Friends", style = MaterialTheme.typography.titleLarge)
                        },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(
                                    imageVector = Icons.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Add by Username",
                        style = MaterialTheme.typography.titleLarge
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Enter a username") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Add a Button for sending the friend request
                    Button(
                        onClick = { /* Handle send friend request */ },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Send Friend Request")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AddFriendScreenPreview() {
    CosmeaTheme() {
        AddFriendScreen(
            onBackPressed = {}
        )
    }
}

@Preview
@Composable
fun AddFriendScreenDarkPreview() {
    CosmeaTheme(darkTheme = true) {
        AddFriendScreen(
            onBackPressed = {}
        )
    }
}