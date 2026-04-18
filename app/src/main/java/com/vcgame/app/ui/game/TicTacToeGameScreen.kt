package com.vcgame.app.ui.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import com.vcgame.app.R
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import com.vcgame.app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeGameScreen(
    onBack: () -> Unit,
    partyId: String? = null
) {
    var board by remember { mutableStateOf(List(9) { "" }) }
    var isXTurn by remember { mutableStateOf(true) }
    var winner by remember { mutableStateOf<String?>(null) }
    var isDraw by remember { mutableStateOf(false) }

    fun checkWinner(currentBoard: List<String>): String? {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Rows
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Cols
            listOf(0, 4, 8), listOf(2, 4, 6)             // Diagonals
        )
        for (pattern in winPatterns) {
            if (currentBoard[pattern[0]].isNotEmpty() &&
                currentBoard[pattern[0]] == currentBoard[pattern[1]] &&
                currentBoard[pattern[0]] == currentBoard[pattern[2]]
            ) {
                return currentBoard[pattern[0]]
            }
        }
        return null
    }

    fun onCellClick(index: Int) {
        if (board[index].isEmpty() && winner == null) {
            val newBoard = board.toMutableList()
            newBoard[index] = if (isXTurn) "X" else "O"
            board = newBoard
            isXTurn = !isXTurn
            
            val gameWinner = checkWinner(newBoard)
            if (gameWinner != null) {
                winner = gameWinner
            } else if (newBoard.none { it.isEmpty() }) {
                isDraw = true
            }
        }
    }

    fun resetGame() {
        board = List(9) { "" }
        isXTurn = true
        winner = null
        isDraw = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tic-Tac-Toe") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    val gameStarted = board.any { it.isNotEmpty() }
                    if (gameStarted || winner != null || isDraw) {
                        IconButton(onClick = { resetGame() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset")
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Status Section
            val statusText = when {
                winner != null -> "Winner: $winner! 🎉"
                isDraw -> "It's a Draw! 🤝"
                else -> "Player ${if (isXTurn) "X" else "O"}'s Turn"
            }
            
            Text(
                text = statusText,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = if (winner != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Game Board
            Box(
                modifier = Modifier
                    .size(320.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
            ) {
                val lineColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                
                // Draw the # grid lines
                Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    val width = size.width
                    val height = size.height
                    val strokeWidth = 6.dp.toPx()

                    // Vertical lines
                    drawLine(
                        color = lineColor,
                        start = Offset(width / 3, 0f),
                        end = Offset(width / 3, height),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = lineColor,
                        start = Offset(2 * width / 3, 0f),
                        end = Offset(2 * width / 3, height),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )

                    // Horizontal lines
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, height / 3),
                        end = Offset(width, height / 3),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, 2 * height / 3),
                        end = Offset(width, 2 * height / 3),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )
                }

                // Interactive cells
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    for (row in 0..2) {
                        Row(modifier = Modifier.weight(1f)) {
                            for (col in 0..2) {
                                val index = row * 3 + col
                                TicTacToeCell(
                                    value = board[index],
                                    onClick = { onCellClick(index) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Reset Button
            val gameStartedBottom = board.any { it.isNotEmpty() }
            if (winner != null || isDraw) {
                Button(
                    onClick = { resetGame() },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(56.dp).fillMaxWidth(0.6f)
                ) {
                    Text("Play Again", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            } else if (gameStartedBottom) {
                Button(
                    onClick = { resetGame() },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(56.dp).fillMaxWidth(0.6f)
                ) {
                    Text("Reset", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun TicTacToeCell(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(enabled = value.isEmpty(), onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = value.isNotEmpty(),
            enter = fadeIn() + scaleIn()
        ) {
            Text(
                text = value,
                fontSize = 48.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (value == "X") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicTacToeGameScreenPreview() {
    AppTheme {
        TicTacToeGameScreen(onBack = {})
    }
}
