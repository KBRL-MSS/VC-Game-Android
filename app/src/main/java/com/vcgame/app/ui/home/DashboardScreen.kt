package com.vcgame.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.vcgame.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    username: String,
    onGoToProfile: () -> Unit,
    onGoToSettings: () -> Unit,
    onLogout: () -> Unit,
    onPlayTicTacToe: () -> Unit,
    onPlaySnake: () -> Unit,
    onGoToDashboard: () -> Unit,
    onGoToJoinParty: () -> Unit,
    onGoToCreateParty: () -> Unit
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

    val bottomBarHeight = 60.dp
    val scrollState = rememberScrollState()

    val games = listOf(
        GameItem(
            name = "Snake",
            imageRes = R.drawable.ic_snake,
            onPlay = onPlaySnake
        ),
        GameItem(
            name = "Tic Tac Toe",
            imageRes = R.drawable.ic_tictactoe,
            onPlay = onPlayTicTacToe
        ),
        GameItem(
            name = "More Games Soon",
            imageRes = R.drawable.ic_more_games,
            onPlay = {}
        )
    )

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
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.align(
                            Alignment.CenterHorizontally)) },
                        label = { Text(text = "Logout", modifier = Modifier.align(Alignment.CenterHorizontally)) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            onLogout()
                        },
                        modifier = Modifier
                            .width(150.dp)
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Featured Games",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp, start = 24.dp).align(Alignment.Start)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(scrollState)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        games.forEach { game ->
                            GameCard(game = game)
                        }
                    }
                    
                    Spacer(Modifier.height(100.dp))
                }

                // Bottom Buttons Container
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(bottomBarHeight)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Button(
                                onClick = onGoToJoinParty,
                                shape = RectangleShape,
                                modifier = Modifier.weight(1f).height(56.dp)
                            ) {
                                Text("Join Party")
                            }

                            Spacer(modifier = Modifier.width(1.dp))

                            Button(
                                onClick = onGoToCreateParty,
                                shape = RectangleShape,
                                modifier = Modifier.weight(1f).height(56.dp)
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

@Composable
fun GameCard(game: GameItem) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (game.imageRes != null) {
                Icon(
                    painter = painterResource(id = game.imageRes),
                    contentDescription = game.name,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    tint = Color.Unspecified
                )
            } else {
                AsyncImage(
                    model = game.imageUrl,
                    contentDescription = game.name,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Fit,
                    onLoading = { println("Coil: Loading ${game.imageUrl}") },
                    onSuccess = { println("Coil: Success ${game.imageUrl}") },
                    onError = { error ->
                        println("Coil: Error ${game.imageUrl} - ${error.result.throwable.message}")
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = game.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = game.onPlay,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = game.name == "Tic Tac Toe" || game.name == "Snake"
            ) {
                Text("Play")
            }
        }
    }
}

data class GameItem(
    val name: String,
    val imageUrl: String? = null,
    val imageRes: Int? = null,
    val onPlay: () -> Unit
)

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
            onGoToDashboard = {},
            onGoToJoinParty = {},
            onGoToCreateParty = {},
            onPlayTicTacToe = {},
            onPlaySnake = {}
        )
    }
}
