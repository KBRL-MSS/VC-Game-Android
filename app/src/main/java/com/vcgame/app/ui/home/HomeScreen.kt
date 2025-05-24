package com.vcgame.app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vcgame.app.ui.theme.AppTheme

/**
 * Composable function for the Home Screen.
 *
 * @param username The username of the logged-in user to display a personalized welcome message.
 */
@Composable
fun HomeScreen(username: String) {
    // Column to arrange elements vertically in the center of the screen
    Column(
        modifier = Modifier.fillMaxSize(), // Fill the entire available space
        verticalArrangement = Arrangement.Center, // Vertically center the content
        horizontalAlignment = Alignment.CenterHorizontally // Horizontally center the content
    ) {
        // Display a welcome message
        Text(text = "Welcome, $username!")
    }
}

/**
 * Preview function for the HomeScreen Composable.
 * This allows you to see how the HomeScreen looks in Android Studio's preview pane.
 */
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme { // Apply your app's theme for the preview
        HomeScreen(username = "Guest") // Provide a sample username for the preview
    }
}
