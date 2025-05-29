
package com.vcgame.app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vcgame.app.ui.theme.AppTheme

@Composable
fun ProfileScreen(onGoToSettings: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is your Profile Page.")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onGoToSettings) {
            Text("Go to Settings from Profile")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen(onGoToSettings = {})
    }
}