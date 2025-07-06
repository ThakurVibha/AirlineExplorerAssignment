package com.example.technicalassignment.technicalassignment.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.technicalassignment.technicalassignment.domain.model.Airline
import com.example.technicalassignment.technicalassignment.presentation.viewmodel.AirlineViewModel


/**
 * Displays a list of airlines with loading, error, and offline handling states.
 *
 * Shows:
 * - Alert dialog if offline
 * - Airline cards in a colorful list on success
 * - Error message on failure
 *
 * @param viewModel ViewModel providing airline data and connectivity status
 * @param onItemClick Callback triggered when a user selects an airline
 */

@Composable
fun AirlineListScreen(
    viewModel: AirlineViewModel,
    onItemClick: (Airline) -> Unit
) {
    val state by viewModel.airlines.collectAsState()
    val isOffline by viewModel.isOffline.collectAsState()

    // 👇 Dialog visibility controlled locally
    var showDialog by remember { mutableStateOf(true) }

    LaunchedEffect(isOffline) {
        if (isOffline) showDialog = true
    }


    val cardColors = listOf(
        Color(0xFFFFCDD2),
        Color(0xFFBBDEFB),
        Color(0xFFFFF9C4),
        Color(0xFFC8E6C9),
        Color(0xFFD1C4E9)
    )

    // Show No Internet Dialog
    if (isOffline && showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK", color = Color.Black)
                }
            },
            title = {
                Text("No Internet", color = Color.Black, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "You're currently offline. Please check your internet connection.",
                    color = Color.DarkGray
                )
            },
            containerColor = Color.White
        )
    }

    when (state) {
        is AirlineViewModel.UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        is AirlineViewModel.UiState.Success -> {
            val airlines = (state as AirlineViewModel.UiState.Success).data

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                itemsIndexed(airlines) { index, airline ->
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = cardColors[index % cardColors.size]
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onItemClick(airline) }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(airline.logoUrl),
                                contentDescription = airline.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = airline.name,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Country: ${airline.country}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.DarkGray
                                )
                            }

                            Text(
                                text = "${airline.fleetSize} ✈️",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Color.Black,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
        }

        is AirlineViewModel.UiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${(state as AirlineViewModel.UiState.Error).message}")
            }
        }
        else -> {}
    }
}
