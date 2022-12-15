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

    val uiState: StateFlow<UiState> =
        combine(redditRepository.getDashboardModels(), redditRepository.networkError) { models, error ->
            if (error.isNotEmpty()) {
                UiState(errorMessage = error, state = UiState.State.Error)
            } else {
                val postOfInterest = models.find { it.isCurrentPostOfInterest }
                UiState(models = models, currentPostOfInterest = postOfInterest, state = UiState.State.Data)
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
//                            updateUiState(_uiState.value.copy(selectedCardModel = event.cardModel))
//                            submitAction(UiAction.ShowExtraInformationDialog)
                        }
                    }
                }
                UiEvent.ExtraInformationDialogConfirmButtonClicked -> {
                    submitAction(UiAction.NoAction)
                }
                is UiEvent.UserClickedOnCard -> {
                    updateCurrentPostOfInterest(event.cardModel)
                }
            }
        }
    }

    private fun updateCurrentPostOfInterest(model: DashboardCardModel) = viewModelScope.launch(Dispatchers.IO) {
        val currentPostOfInterest = uiState.value.currentPostOfInterest
        val userAlreadyMarkedPostOfInterest = currentPostOfInterest != null && currentPostOfInterest.id != model.id
        if (userAlreadyMarkedPostOfInterest) return@launch
        redditRepository.updatePostOfInterest(model.id, model.isCurrentPostOfInterest)
    }

    private fun deleteModel(modelId: String) = viewModelScope.launch(Dispatchers.IO) {
        redditRepository.deleteEntity(modelId)
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
        var currentPostOfInterest : DashboardCardModel? = null,
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