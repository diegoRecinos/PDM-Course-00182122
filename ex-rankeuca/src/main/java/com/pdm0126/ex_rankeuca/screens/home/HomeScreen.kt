package com.pdm0126.ex_rankeuca.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                text = "Opciones para votar",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )

            if (uiState.isLoading && uiState.options.isEmpty()) {
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Text(text = "Error: ${uiState.error}", modifier = Modifier.padding(16.dp))
            } else {
                PullToRefreshBox(
                    isRefreshing = uiState.isLoading,
                    onRefresh = { viewModel.fetchOptions() }
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        items(uiState.options) { option ->
                            OptionItem(
                                option = option,
                                isSelected = uiState.selectedOptionId == option.id,
                                isLoading = uiState.isVoting && uiState.selectedOptionId == option.id,
                                enabled = !uiState.hasVoted && !uiState.isVoting,
                                onVote = { viewModel.vote(option.id) }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
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
                }
            )
        }
    }
}
