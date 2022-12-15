package com.tinytap.ui.screens.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinytap.data.repository.RedditRepository
import com.tinytap.model.ui_models.DashboardCardModel
import com.tinytap.ui.screens.dashboard.viewmodel.DashboardViewModel.UiEvent.UserSwipedCard.SwipeDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val redditRepository: RedditRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    val newUiState : StateFlow<UiState> = redditRepository.getDashboardModels().map { models ->
        if (models.isEmpty()) {
            UiState(state = UiState.State.Initial)
        } else {
            UiState(models = models, state = UiState.State.Data)
        }
    }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UiState()
        )

    private val _uiAction = MutableSharedFlow<UiAction>()
    val uiAction = _uiAction.asSharedFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    private val uiEvent = _uiEvent.asSharedFlow()


    init {
        getDashboardModels()
        observeUiEvents()
    }

    private fun getDashboardModels() = viewModelScope.launch(Dispatchers.IO) {
        redditRepository.getNewRedditModels()
    }


    private fun observeUiEvents() = viewModelScope.launch {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.UserSwipedCard -> {
                    when (event.direction) {
                        SwipeDirection.DOWN_TO_UP -> {
                            deleteModel(event.cardModel.id)
                        }
                        SwipeDirection.UP_TO_DOWN -> {
                            updateUiState(_uiState.value.copy(selectedCardModel = event.cardModel))
                            submitAction(UiAction.ShowExtraInformationDialog)
                        }
                    }
                }
                UiEvent.ExtraInformationDialogConfirmButtonClicked -> {
                    submitAction(UiAction.NoAction)
                }
                is UiEvent.UserClickedOnCard -> {
                    updateCurrentPostOfInterest(event)
                }
            }
        }
    }

    private suspend fun updateCurrentPostOfInterest(event: UiEvent.UserClickedOnCard) {
        val newList = _uiState.value.models.toMutableList().apply {
            val item = find { it.id == event.cardModel.id } ?: return
            item.isCurrentPostOfInterest = item.isCurrentPostOfInterest.not()
        }
        updateUiState(UiState(models = newList, state = UiState.State.Data))
    }

    private fun deleteModel(modelId: String) = viewModelScope.launch(Dispatchers.IO) {
        redditRepository.deleteEntity(modelId)
    }

    private suspend fun updateUiState(uiState: UiState) {
        _uiState.emit(uiState)
    }

    private fun submitAction(uiAction: UiAction) = viewModelScope.launch {
        _uiAction.emit(uiAction)
    }

    fun submitEvent(uiEvent: UiEvent) = viewModelScope.launch {
        _uiEvent.emit(uiEvent)
    }

    sealed interface UiEvent {
        object ExtraInformationDialogConfirmButtonClicked : UiEvent
        data class UserClickedOnCard(val cardModel: DashboardCardModel) : UiEvent
        data class UserSwipedCard(val direction: SwipeDirection, val cardModel: DashboardCardModel) : UiEvent {
            enum class SwipeDirection {
                DOWN_TO_UP,
                UP_TO_DOWN
            }
        }
    }

    data class UiState(
        val models: List<DashboardCardModel> = mutableListOf(),
        val selectedCardModel: DashboardCardModel? = null,
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
        object NoAction : UiAction
        object ShowExtraInformationDialog : UiAction
    }
}