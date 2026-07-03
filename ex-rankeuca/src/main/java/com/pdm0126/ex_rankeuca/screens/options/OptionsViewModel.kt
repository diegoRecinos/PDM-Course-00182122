package com.pdm0126.ex_rankeuca.screens.options

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdm0126.ex_rankeuca.RankeUcaApplication
import com.pdm0126.ex_rankeuca.data.model.Option
import com.pdm0126.ex_rankeuca.data.repository.optionrepository.OptionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class OptionsViewModel(
    private val questionId: Int,
    private val optionRepository: OptionRepository
) : ViewModel() {

    var isRefreshing by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    val options: StateFlow<List<Option>> =
        optionRepository.getOptions(questionId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
    init {
        refreshOptions()
    }

    fun refreshOptions() {
        viewModelScope.launch {
            errorMessage = null
            isRefreshing = true
            try {
                optionRepository.refreshOptions()
            } catch (e: Exception) {
                if (options.value.isEmpty()) {
                    errorMessage = "Error al cargar opciones"
                }
            } finally {
                isRefreshing = false
            }
        }
    }
    fun addOption(name: String, imageUrl: String) {
        viewModelScope.launch {
            optionRepository.createOption(name, imageUrl, questionId)
        }
    }

    fun updateOption(option: Option) {
        viewModelScope.launch {
            optionRepository.updateOption(option)
        }
    }

    fun deleteOption(option: Option) {
        viewModelScope.launch {
            optionRepository.deleteOption(option)
        }
    }

    companion object {
        fun provideFactory(questionId: Int) = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as RankeUcaApplication
                OptionsViewModel(
                    questionId = questionId,
                    optionRepository = app.appProvider.provideOptionRepository()
                )
            }
        }
    }
}
