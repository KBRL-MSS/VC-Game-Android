package com.vcgame.app.ui.home

import androidx.compose.foundation.background // Added import
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vcgame.app.ui.theme.AppTheme
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.layout.Box
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme // Added import for MaterialTheme.colorScheme.surfaceVariant
import androidx.compose.ui.composed // Added for composed modifier usage
import androidx.compose.ui.draw.shadow // Added for shadow
import androidx.compose.ui.unit.Dp // Added for Dp type
import androidx.compose.ui.draw.clip // Added for clip
import androidx.compose.foundation.shape.RoundedCornerShape // Added for RoundedCornerShape
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    username: String,
    onGoToProfile: () -> Unit,
    onGoToSettings: () -> Unit,
    onLogout: () -> Unit,
    onGoToDashboard: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val menuItems = listOf(
        DrawerMenuItem("Dashboard", Icons.Default.Home) {
            scope.launch { drawerState.close() }
            onGoToDashboard()
        },
        DrawerMenuItem("Profile", Icons.Default.Person) {
            scope.launch { drawerState.close() }
            onGoToProfile()
        },
        DrawerMenuItem("Settings", Icons.Default.Settings) {
            scope.launch { drawerState.close() }
            onGoToSettings()
        }
    )

    // Define the height of your fixed bottom bar
    val bottomBarHeight = 60.dp
    val ticTacToeImageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/Tic_tac_toe.svg/800px-Tic_tac_toe.svg.png"


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp))
                Spacer(Modifier.height(16.dp))

                menuItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.title) },
                        selected = false,
                        onClick = item.onClick,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp), // Add some bottom padding
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Close, contentDescription = null) }, // Added Logout icon
                        label = { Text("Logout") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            onLogout()
                        },
                        modifier = Modifier.fillMaxWidth(0.65f) // Make button take 80% width, for instance
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Welcome $username") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            // Use a Box to layer the main content, and the fixed bottom bar
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Apply padding from Scaffold for TopBar
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    AsyncImage(
                        model = ticTacToeImageUrl,
                        contentDescription = "Tic Tac Toe Board",
                        modifier = Modifier
                            .size(300.dp) // Adjust size as needed
                            .clip(RoundedCornerShape(16.dp)), // Optional: rounded corners for the image
                            //.shadow(8.dp, RoundedCornerShape(16.dp)), // Optional: shadow
                        contentScale = ContentScale.Fit // Fit the image within the bounds
                    )
                    Spacer(Modifier.height(50.dp))
                    FloatingActionButton(
                        onClick = { /* Action for FAB */ println("FAB Clicked") },
                        shape = CircleShape,
                        modifier = Modifier
                            .width(200.dp)
                    ) {
                        Text(
                            text = "Play",
                            fontSize = 20.sp
                        )
                    }

                }
                Column (
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(bottomBarHeight) // Set the fixed height for the entire bottom bar
                            //.align(Alignment.BottomCenter) // Align this Box to the very bottom center of its parent Box
                            .background(MaterialTheme.colorScheme.surface) // Optional: Add a background color for the bar
                        //.shadow(elevation = 8.dp) // Optional: Add a shadow for separation
                    ) {
                        // Row for left and right buttons
                        Row(
                            modifier = Modifier
                                .fillMaxSize(), // Fill the height of this bottom Box
                            //.padding(horizontal = 16.dp), // Padding inside the bottom bar for the buttons
                            horizontalArrangement = Arrangement.SpaceAround, // Distribute space
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Button(
                                onClick = { /* Action for Left Button */ println("Join Party Clicked") },
                                shape = RectangleShape,
                                modifier = Modifier
                                    .weight(1f) // Takes available space
                                    .height(56.dp) // Standard button height
                            ) {
                                Text("Join Party")
                            }

                            Spacer(modifier = Modifier.width(1.dp))

                            Button(
                                onClick = { /* Action for Right Button */ println("Create Party Clicked") },
                                shape = RectangleShape,
                                modifier = Modifier
                                    .weight(1f) // Takes available space
                                    .height(56.dp)
                            ) {
                                Text("Create Party")
                            }
                        }


                    }
                }


            }
        }
    }
}

data class DrawerMenuItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    AppTheme {
        DashboardScreen(
            username = "Gamer!",
            onGoToProfile = {},
            onGoToSettings = {},
            onLogout = {},
            onGoToDashboard = {}
        )
    }
}