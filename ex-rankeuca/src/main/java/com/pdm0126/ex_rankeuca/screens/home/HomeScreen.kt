package com.pdm0126.ex_rankeuca.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.pdm0126.ex_rankeuca.data.model.Option

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToResultScreen: () -> Unit,
    onNavigateToOptions: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.Companion.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("RankeUca - Vota") },
                actions = {
                    IconButton(onClick = onNavigateToOptions) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Administrar opciones"
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (uiState.hasVoted) {
                Button(
                    onClick = onNavigateToResultScreen,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text("Ver resultados")
                }
            }
        }
    ) { innerPadding ->
        val options = uiState.options
        val isRefreshing = uiState.isLoading
        val error = uiState.error

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                // 1. Cargando: Room vacío y todavía esperando a la API
                options.isEmpty() && isRefreshing -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                // 2. Error sin cache: la API falló y no hay nada guardado
                options.isEmpty() && error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error al cargar los datos: $error",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.fetchOptions() }) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Reintentar")
                        }
                    }
                }

                // 3. Datos: hay cache (con o sin internet)
                else -> {
                    PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = { viewModel.fetchOptions() },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                Text(
                                    text = "Opciones para votar",
                                    modifier = Modifier.padding(16.dp),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                            items(options) { option ->
                                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                    OptionItem(
                                        option = option,
                                        isSelected = uiState.selectedOptionId == option.id,
                                        isLoading = uiState.isVoting && uiState.selectedOptionId == option.id,
                                        enabled = !uiState.hasVoted && !uiState.isVoting,
                                        onVote = { viewModel.vote(option.id) }
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OptionItem(
    option: Option,
    isSelected: Boolean,
    isLoading: Boolean,
    enabled: Boolean,
    onVote: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onVote() },
        elevation = CardDefaults.cardElevation(4.dp),
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
    ) {
        Column {
            AsyncImage(
                model = option.imageUrl,
                contentDescription = option.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            ListItem(
                headlineContent = {
                    Text(option.value, style = MaterialTheme.typography.titleLarge)
                },
                supportingContent = {
                    if (isSelected) {
                        Text(text = "tu voto")
                    } else if (enabled) {
                        Text(text = "toca para votar")
                    }
                },
                trailingContent = {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.height(24.dp))
                    }
                }
            )
        }
    }
}