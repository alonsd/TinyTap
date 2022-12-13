package com.tinytap.ui.screens.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.tinytap.data.repository.RedditRepository
import com.tinytap.model.ui_models.DashboardCardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    redditRepository: RedditRepository
) : ViewModel() {

    val uiState : StateFlow<UiState> = redditRepository.dashboardModels.map { response ->
            if (response is NetworkResponse.Error) {
                UiState(errorMessage = response.error.localizedMessage ?: "Server error", state = UiState.State.Error)
            }
            val data = response as NetworkResponse.Success
            UiState(models = data.body, state = UiState.State.Data)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            UiState()
        )

    private val _uiAction = MutableSharedFlow<UiAction>()
    val uiAction = _uiAction.asSharedFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    private val uiEvent = _uiEvent.asSharedFlow()


    init {

    }


    private fun submitAction(uiAction: UiAction) = viewModelScope.launch {
        _uiAction.emit(uiAction)
    }

    fun submitEvent(uiEvent: UiEvent) = viewModelScope.launch {
        _uiEvent.emit(uiEvent)
    }

    sealed interface UiEvent {
    }

    data class UiState(
        val models: List<DashboardCardModel> = emptyList(),
        val errorMessage: String = "",
        val state: State = State.Initial
    ) {
        enum class State {
            Data,
            Error,
            Initial
        }
    }

    sealed interface UiAction {

    }
}