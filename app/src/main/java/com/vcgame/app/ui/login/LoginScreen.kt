package com.vcgame.app.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.vcgame.app.ui.theme.AppTheme
import com.vcgame.app.utils.ValidationUtils.validatePassword
import com.vcgame.app.utils.ValidationUtils.validateUsername

@Composable
fun LoginScreen(
    onLoginClicked: (String, String) -> Unit,
    onSignUpClicked: () -> Unit
) {
    // State variables to hold the username and password entered by the user
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) } // State for password error message
    var usernameError by remember { mutableStateOf<String?>(null) } // State for username error message

    // Column to arrange elements vertically in the center of the screen
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire available space
            .background(Color.Cyan)
            .padding(16.dp), // Add padding around the column
        verticalArrangement = Arrangement.Center, // Vertically center the content
        horizontalAlignment = Alignment.CenterHorizontally  // Horizontally center the content
    ) {
        Text(
            text = "Welcome to KBRL Games!",
            fontSize = 20.sp,
            modifier = Modifier
                .blur(0.3.dp, BlurredEdgeTreatment.Rectangle)
        )

        Spacer(modifier = Modifier.height(16.dp)) // Add vertical space

        // Text field for username input
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                // CLEAR ERROR ON CHANGE
                usernameError = null
            },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            // ADD THESE PARAMETERS FOR ERROR INDICATION
            isError = usernameError != null,
            supportingText = {
                usernameError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp)) // Add vertical space

        // Text field for password input
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                // CLEAR ERROR ON CHANGE
                passwordError = null
            },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            // ADD THESE PARAMETERS FOR ERROR INDICATION
            isError = passwordError != null,
            supportingText = {
                passwordError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp)) // Add vertical space

        // Login button
        Button(
            onClick = {
                var isValid = true

                // VALIDATE USERNAME
                val userValidationResult = validateUsername(username)
                if (userValidationResult != null) {
                    usernameError = userValidationResult
                    isValid = false
                }

                // VALIDATE PASSWORD
                val passValidationResult = validatePassword(password)
                if (passValidationResult != null) {
                    passwordError = passValidationResult
                    isValid = false
                }

                if (isValid) {
                    onLoginClicked(username, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .blur(0.5.dp, BlurredEdgeTreatment.Rectangle)// Make button fill width
        ) {
            Text("Login") // Button text
        }

        Spacer(modifier = Modifier.height(16.dp))

        // New button/text for navigating to Sign Up
        TextButton(onClick = onSignUpClicked) { // Call the new lambda
            Text("Don't have an account? Sign Up")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(
            onLoginClicked = { _, _ -> /* Do nothing for preview */ },
            onSignUpClicked = { /* Do nothing for preview */ }
        )
    }
}