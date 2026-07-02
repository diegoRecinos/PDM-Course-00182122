package com.pdm0126.ex_rankeuca.screens.questions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.ex_rankeuca.data.model.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsScreen(
    onQuestionClick: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: QuestionsViewModel = viewModel(factory = QuestionsViewModel.Companion.Factory)
) {
    val questions by viewModel.questions.collectAsStateWithLifecycle()
    var showSheet by rememberSaveable { mutableStateOf(false) }
    var selectedQuestion by rememberSaveable { mutableStateOf<Question?>(null) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        topBar = {
            TopAppBar(
                title = { Text("Preguntas") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        selectedQuestion = null
                        showSheet = true
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Nueva pregunta")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Nuevo")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            if (questions.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.HelpOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.height(36.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Todavía no hay preguntas",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Toca Nuevo para crear la primera.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items = questions, key = { it.id }) { question ->
                        ElevatedCard(
                            modifier = Modifier.clickable { onQuestionClick(question.id) }
                        ) {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        text = question.title,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                },
                                supportingContent = {
                                    Text(
                                        text = "${question.optionCount} opciones",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                trailingContent = {
                                    Row {
                                        IconButton(onClick = {
                                            selectedQuestion = question
                                            showSheet = true
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Editar ${question.title}",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        IconButton(onClick = { viewModel.deleteQuestion(question) }) {
                                            Icon(
                                                imageVector = Icons.Default.DeleteOutline,
                                                contentDescription = "Borrar ${question.title}",
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showSheet) {
        QuestionBottomSheet(
            question = selectedQuestion,
            onSave = { title ->
                if (selectedQuestion == null) {
                    viewModel.addQuestion(title)
                } else {
                    viewModel.updateQuestion(selectedQuestion!!.copy(title = title))
                }
            },
            onDismiss = {
                showSheet = false
                selectedQuestion = null
            }
        )
    }
}
