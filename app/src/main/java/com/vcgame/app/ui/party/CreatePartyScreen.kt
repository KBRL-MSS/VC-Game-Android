package com.vcgame.app.ui.party

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vcgame.app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePartyScreen(
    onBack: () -> Unit,
    onCreatePartyClicked: () -> Unit // Callback to navigate to PartyDetailsScreen
) {
    var partyName by remember { mutableStateOf("") }
    var maxPlayers by remember { mutableStateOf("4") } // Default

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create New Party") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            Text("Set up your new party")
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = partyName,
                onValueChange = { partyName = it },
                label = { Text("Party Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = maxPlayers,
                onValueChange = { newValue ->
                    // Allow only digits
                    maxPlayers = newValue.filter { it.isDigit() }
                },
                label = { Text("Max Players") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onCreatePartyClicked,
                enabled = partyName.isNotBlank() && maxPlayers.toIntOrNull() in 2..8, // Basic validation
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Party")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePartyScreenPreview() {
    AppTheme {
        CreatePartyScreen(onBack = {}, onCreatePartyClicked = {})
    }
}