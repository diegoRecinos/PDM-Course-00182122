package com.pdm0126.ex_rankeuca.screens.resultscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdm0126.ex_rankeuca.RankeUcaApplication
import com.pdm0126.ex_rankeuca.data.repository.optionrepository.OptionRepository
import com.pdm0126.ex_rankeuca.screens.home.HomeScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResultScreenViewModel(
    private val repository: OptionRepository
) : ViewModel() {

    private val _internalState = MutableStateFlow(HomeScreenUIState())

    val uiState: StateFlow<HomeScreenUIState> = combine(
        repository.getOptions(),
        _internalState
    ) { options, internal ->
        internal.copy(
            options = options.sortedByDescending { it.votes },
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeScreenUIState(isLoading = true)
    )

    fun fetchOptions() {
    }

    fun resetAllVotes() {
        viewModelScope.launch {
            _internalState.update { it.copy(isLoading = true) }
            try {
                repository.resetVotes()
                _internalState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _internalState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as RankeUcaApplication
                ResultScreenViewModel(app.appProvider.provideOptionRepository())
            }
        }
    }
}
