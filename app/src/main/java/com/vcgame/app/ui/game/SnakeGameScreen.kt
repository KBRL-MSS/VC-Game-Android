package com.vcgame.app.ui.game

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.random.Random

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

enum class Direction { UP, DOWN, LEFT, RIGHT }

data class SnakeState(
    val snake: List<Pair<Int, Int>> = listOf(Pair(10, 10), Pair(10, 11), Pair(10, 12)),
    val food: Pair<Int, Int> = Pair(5, 5),
    val direction: Direction = Direction.UP,
    val isGameOver: Boolean = false,
    val score: Int = 0
)

class SnakeViewModel(private val context: Context) : ViewModel() {
    private val HIGH_SCORE_KEY = intPreferencesKey("snake_high_score")

    private val _state = MutableStateFlow(SnakeState())
    val state: StateFlow<SnakeState> = _state.asStateFlow()

    val highScore: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[HIGH_SCORE_KEY] ?: 0
        }

    init {
        startGame()
    }

    fun startGame() {
        _state.value = SnakeState(
            food = generateFood(listOf(Pair(10, 10), Pair(10, 11), Pair(10, 12)))
        )
        viewModelScope.launch {
            while (!_state.value.isGameOver) {
                delay(150)
                moveSnake()
            }
        }
    }

    fun onDirectionChange(newDirection: Direction) {
        val currentDirection = _state.value.direction
        if (newDirection == Direction.UP && currentDirection != Direction.DOWN ||
            newDirection == Direction.DOWN && currentDirection != Direction.UP ||
            newDirection == Direction.LEFT && currentDirection != Direction.RIGHT ||
            newDirection == Direction.RIGHT && currentDirection != Direction.LEFT
        ) {
            _state.value = _state.value.copy(direction = newDirection)
        }
    }

    private fun moveSnake() {
        val currentState = _state.value
        val head = currentState.snake.first()
        val newHead = when (currentState.direction) {
            Direction.UP -> Pair(head.first, head.second - 1)
            Direction.DOWN -> Pair(head.first, head.second + 1)
            Direction.LEFT -> Pair(head.first - 1, head.second)
            Direction.RIGHT -> Pair(head.first + 1, head.second)
        }

        if (newHead.first < 0 || newHead.first >= 20 || newHead.second < 0 || newHead.second >= 20 || currentState.snake.contains(newHead)) {
            _state.value = currentState.copy(isGameOver = true)
            saveHighScore(currentState.score)
            return
        }

        val newSnake = mutableListOf(newHead) + currentState.snake
        if (newHead == currentState.food) {
            val newScore = currentState.score + 1
            _state.value = currentState.copy(
                snake = newSnake,
                food = generateFood(newSnake),
                score = newScore
            )
        } else {
            _state.value = currentState.copy(snake = newSnake.dropLast(1))
        }
    }

    private fun generateFood(snake: List<Pair<Int, Int>>): Pair<Int, Int> {
        var food: Pair<Int, Int>
        do {
            food = Pair(Random.nextInt(20), Random.nextInt(20))
        } while (snake.contains(food))
        return food
    }

    private fun saveHighScore(score: Int) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                val currentHighScore = preferences[HIGH_SCORE_KEY] ?: 0
                if (score > currentHighScore) {
                    preferences[HIGH_SCORE_KEY] = score
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnakeGameScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val viewModel: SnakeViewModel = viewModel { SnakeViewModel(context) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val highScore by viewModel.highScore.collectAsStateWithLifecycle(initialValue = 0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Snake Game") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (state.isGameOver) {
                        IconButton(onClick = { viewModel.startGame() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Restart")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Score: ${state.score}", style = MaterialTheme.typography.titleLarge)
                Text(text = "High Score: $highScore", style = MaterialTheme.typography.titleLarge)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            val (x, y) = dragAmount
                            if (kotlin.math.abs(x) > kotlin.math.abs(y)) {
                                if (x > 0) viewModel.onDirectionChange(Direction.RIGHT)
                                else viewModel.onDirectionChange(Direction.LEFT)
                            } else {
                                if (y > 0) viewModel.onDirectionChange(Direction.DOWN)
                                else viewModel.onDirectionChange(Direction.UP)
                            }
                        }
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val tileSize = size.width / 20

                    // Draw Food
                    drawRect(
                        color = Color.Red,
                        topLeft = Offset(state.food.first * tileSize, state.food.second * tileSize),
                        size = Size(tileSize, tileSize)
                    )

                    // Draw Snake
                    state.snake.forEachIndexed { index, part ->
                        val color = if (index == 0) Color.Green else Color.DarkGray
                        drawRect(
                            color = color,
                            topLeft = Offset(part.first * tileSize, part.second * tileSize),
                            size = Size(tileSize, tileSize)
                        )
                        // Add eyes to the head
                        if (index == 0) {
                            val eyeSize = tileSize / 5
                            drawCircle(
                                color = Color.White,
                                radius = eyeSize,
                                center = Offset(part.first * tileSize + tileSize / 4, part.second * tileSize + tileSize / 4)
                            )
                            drawCircle(
                                color = Color.White,
                                radius = eyeSize,
                                center = Offset(part.first * tileSize + 3 * tileSize / 4, part.second * tileSize + tileSize / 4)
                            )
                        }
                    }
                }

                if (state.isGameOver) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "GAME OVER",
                                color = Color.White,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.startGame() }) {
                                Text("Try Again")
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Swipe to Control", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
