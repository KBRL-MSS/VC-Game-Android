package com.vcgame.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vcgame.app.ui.login.LoginScreen
//import com.vcgame.app.ui.login.LoginScreen
import com.vcgame.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Column {
                    Greeting(
                        name = "Gamers",
                        modifier = Modifier.padding(top = 30.dp)
                    )
                    AppLogin()
                }
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Gamers",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                    AppNavigation()
//                }
            }
        }
    }
}

@Composable
fun AppLogin() {
    Text(
        text = "App Login Screen"
    )
    LoginScreen()
//    val navController = rememberNavController()
//    NavHost(navController, startDestination = "login") {
//        //composable("login") { LoginScreen() }
//        composable("home") { HomeScreen() }
//    }
}

@Composable
fun HomeScreen() {
    Text(
        text = "Home Screen"
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Gamers")
    }
}