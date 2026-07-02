package com.pdm0126.ex_rankeuca.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdm0126.ex_rankeuca.RankeUcaApplication
import com.pdm0126.ex_rankeuca.data.model.Option
import com.pdm0126.ex_rankeuca_room.data.repository.OptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeScreenUIState(
    val isLoading: Boolean = false,
    val options: List<Option> = emptyList(),
    val error: String? = null,
    val hasVoted: Boolean = false,
    val isVoting: Boolean = false,
    val selectedOptionId: Int? = null
)

class HomeScreenViewModel(
    private val repository: OptionRepository
) : ViewModel() {

    // Estado interno para flags loading, voting, errores
    private val _internalState = MutableStateFlow(HomeScreenUIState())

    // Combinamos el Flow vivo de Room con nuestro estado interno de la UI
    val uiState: StateFlow<HomeScreenUIState> = combine(
        repository.getOptions(),
        _internalState
    ) { options, internal ->
        internal.copy(
            options = options.sortedByDescending { it.votes },
            isLoading = if (options.isNotEmpty()) false else internal.isLoading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeScreenUIState(isLoading = true)
    )

    init {
        fetchOptions()
    }

    fun fetchOptions() {
        viewModelScope.launch {
            _internalState.update { it.copy(isLoading = true, error = null) }
            try {
                repository.refreshOptions()
                _internalState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _internalState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun vote(optionId: Int) {
        if (!_internalState.value.isVoting && !_internalState.value.hasVoted) {
            viewModelScope.launch {
                _internalState.update { it.copy(isVoting = true, selectedOptionId = optionId, error = null) }
                try {
                    repository.voteOption(optionId)
                    _internalState.update { it.copy(isVoting = false, hasVoted = true) }
                } catch (e: Exception) {
                    _internalState.update { it.copy(isVoting = false, error = e.message, selectedOptionId = null) }
                }
            }
        }
    }

    fun resetLocalVote() {
        _internalState.update {
            it.copy(
                hasVoted = false,
                selectedOptionId = null,
                error = null
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as RankeUcaApplication
                HomeScreenViewModel(app.appProvider.provideOptionRepository())
            }
        }
    }
}