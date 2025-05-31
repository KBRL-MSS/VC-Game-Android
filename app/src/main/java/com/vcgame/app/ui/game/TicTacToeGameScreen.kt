// app/src/main/java/com/vcgame/app/ui/game/TicTacToeGameScreen.kt
package com.vcgame.app.ui.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vcgame.app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeGameScreen(
    onBack: () -> Unit,
    partyId: String? = null // Optional: to show which party the game belongs to
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tic-Tac-Toe Game ${partyId?.let { "(Party ID: $it)" } ?: ""}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Party Details")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to Tic-Tac-Toe!",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Game in progress...",
                style = MaterialTheme.typography.bodyLarge
            )
            // This is where you would integrate your actual Tic-Tac-Toe game UI
            // For now, it's just a placeholder.
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onBack) {
                Text("End Game (Go Back)")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicTacToeGameScreenPreview() {
    AppTheme {
        TicTacToeGameScreen(onBack = {}, partyId = "ABC123XYZ")
    }
}