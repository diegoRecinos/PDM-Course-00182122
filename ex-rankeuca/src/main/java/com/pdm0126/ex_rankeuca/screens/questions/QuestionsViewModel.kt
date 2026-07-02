package com.pdm0126.ex_rankeuca_room.screens.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdm0126.ex_rankeuca.RankeUcaApplication
import com.pdm0126.ex_rankeuca.data.model.Question
import com.pdm0126.ex_rankeuca.data.repository.QuestionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuestionsViewModel(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    val questions: StateFlow<List<Question>> =
        questionRepository.getQuestions()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun addQuestion(title: String) {
        viewModelScope.launch {
            questionRepository.addQuestion(title)
        }
    }

    fun deleteQuestion(question: Question) {
        viewModelScope.launch {
            questionRepository.deleteQuestion(question)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as RankeUcaApplication
                QuestionsViewModel(app.appProvider.provideQuestionRepository())
            }
        }
    }
}