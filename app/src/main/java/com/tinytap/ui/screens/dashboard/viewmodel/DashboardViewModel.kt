package com.tinytap.ui.screens.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.tinytap.data.repository.RedditRepository
import com.tinytap.model.network_models.RedditResponseModel
import com.tinytap.model.ui_models.DashboardCardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.tinytap.ui.screens.dashboard.viewmodel.DashboardViewModel.UiEvent.UserSwipedList.SwipeDirection

@HiltViewModel
class DashboardViewModel @Inject constructor(
    val redditRepository: RedditRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiAction = MutableSharedFlow<UiAction>()
    val uiAction = _uiAction.asSharedFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    private val uiEvent = _uiEvent.asSharedFlow()


    init {
        getDashboardModels()
        observeUiEvents()
    }

    private fun getDashboardModels() = viewModelScope.launch {
        when (val response = redditRepository.getRedditModel()) {
            is NetworkResponse.Success -> {
                extractDashboardModels(response)
            }
            is NetworkResponse.Error -> {
                updateUiState(
                    UiState(
                        errorMessage = response.error.localizedMessage ?: "Server error",
                        state = UiState.State.Error
                    )
                )
            }
            else -> Unit
        }
    }

    private suspend fun extractDashboardModels(response: NetworkResponse.Success<RedditResponseModel>) {
        val dashboardCards = mutableListOf<DashboardCardModel>()
        response.body.data.children.forEach { children ->
            val data = children.data
            dashboardCards.add(
                DashboardCardModel(data.id, data.thumbnail, data.title, data.author)
            )
        }
        updateUiState(
            UiState(
                models = dashboardCards,
                state = UiState.State.Data
            )
        )
    }

    private suspend fun updateUiState(uiState: UiState) {
        _uiState.emit(uiState)
    }

    private fun observeUiEvents() = viewModelScope.launch {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.UserSwipedList -> {
                    if (event.direction == SwipeDirection.DOWN_TO_UP) {
                        deleteCard(event.modelId)
                    }
                }
            }
        }
    }

    private fun deleteCard(modelId: String) = viewModelScope.launch {
        val listWithoutDeletedCard = _uiState.value.models.toMutableList().filterNot { it.id == modelId }
        updateUiState(UiState(models = listWithoutDeletedCard, state = UiState.State.Data))
    }


    private fun submitAction(uiAction: UiAction) = viewModelScope.launch {
        _uiAction.emit(uiAction)
    }

    fun submitEvent(uiEvent: UiEvent) = viewModelScope.launch {
        _uiEvent.emit(uiEvent)
    }

    sealed interface UiEvent {
        data class UserSwipedList(val direction: SwipeDirection, val modelId: String) : UiEvent {
            enum class SwipeDirection {
                DOWN_TO_UP,
                UP_TO_DOWN
            }
        }
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