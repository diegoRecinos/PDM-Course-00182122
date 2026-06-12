package com.pdm0126.ex_rankeuca_room.screens.home

import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.ex_rankeuca_room.data.api.KtorClient
import com.pdm0126.ex_rankeuca_room.data.model.Option
import com.pdm0126.ex_rankeuca_room.data.repository.ApiRepository
import com.pdm0126.ex_rankeuca_room.data.repository.OptionRepository
import com.pdm0126.ex_rankeuca_room.data.repository.RepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
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
): ViewModel() {

    //old private val repository: RepositoryInterface = ApiRepository(KtorClient.client)

    //se queda abierto escuchando a Room se actualiza cada vez que room cambie
    val uiState: StateFlow<HomeScreenUIState> = repository.getOptions()
        .map { options ->
            HomeScreenUIState(options = options.sortedByDescending { it.votes })
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeScreenUIState(isLoading = true)
        )
    private val _uiState = MutableStateFlow(HomeScreenUIState())
    // old uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()

    init {
        fetchOptions()
        //viewModelScope.launch {repository.refreshOptions()}
    }

    fun fetchOptions() {
        viewModelScope.launch {
            repository.refreshOptions()
        }
    }

    fun vote(optionId: Int) {
        viewModelScope.launch {
            repository.voteOption(optionId)
        }
    }

//old
//    fun fetchOptions() {
//        viewModelScope.launch {
//            try {
//                _uiState.update { it.copy(isLoading = true, error = null) }
//                repository.getOptions()
//                    .onSuccess { options ->
//                        _uiState.update {
//                            it.copy(
//                                options = options.sortedByDescending { it.votes },
//                                isLoading = false
//                            )
//                        }
//                    }
//                    .onFailure { error ->
//                        _uiState.update { it.copy(error = error.message, isLoading = false) }
//                    }
//            } catch (e: Exception) {
//                e("HomeScreenViewModel", "Error fetching options: ${e.message}", e)
//                _uiState.update { it.copy(error = e.message, isLoading = false) }
//            }
//        }
//    }
//
//    fun vote(optionId: Int) {
//        if (!_uiState.value.isVoting && !_uiState.value.hasVoted) {
//            viewModelScope.launch {
//                _uiState.update { it.copy(isVoting = true, selectedOptionId = optionId, error = null) }
//                repository.voteOption(optionId)
//                    .onSuccess {
//                        _uiState.update {
//                            it.copy(
//                                selectedOptionId = optionId,
//                                hasVoted = true,
//                                isVoting = false
//                            )
//                        }
//                        fetchOptions()
//                    }
//                    .onFailure { error ->
//                        _uiState.update {
//                            it.copy(
//                                isVoting = false,
//                                selectedOptionId = null,
//                                error = error.message
//                            )
//                        }
//                    }
//            }
//        }
//    }

    fun resetLocalVote() {
        _uiState.update {
            it.copy(
                hasVoted = false,
                selectedOptionId = null,
                error = null
            )

        }
        fetchOptions()
    }


//    fun resetAllVotesAdmin() {
//        viewModelScope.launch {
//            try {
//                _uiState.update { it.copy(isLoading = true, error = null) }
//                repository.resetVotes()
//                    .onSuccess {
//                        resetLocalVote()
//                        fetchOptions()
//                    }
//                    .onFailure { error ->
//                        _uiState.update { it.copy(error = error.message, isLoading = false) }
//                    }
//            } catch (e: Exception) {
//                e("HomeScreenViewModel", "Error resetting votes: ${e.message}", e)
//                _uiState.update { it.copy(error = e.message, isLoading = false) }
//            }
//        }
//    }
}
