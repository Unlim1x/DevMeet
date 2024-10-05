package ru.unlim1x.ui.screens.event_detailed

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import ru.lim1x.domain.interfaces.interactors.IGetDetailedEventFullInfoInteractor
import ru.lim1x.domain.models.SearchRequest
import ru.unlim1x.old_ui.screens.MainViewModel
import ru.unlim1x.ui.mappers.mapEventListToUi
import ru.unlim1x.ui.mappers.mapPersonToUi
import ru.unlim1x.ui.mappers.mapToUi
import ru.unlim1x.ui.screens.main_screen.MainScreenEvent
import ru.unlim1x.ui.screens.main_screen.MainScreenViewState

internal class DetailedEventScreenViewModel(
    private val getDetailedEventFullInfoInteractor: IGetDetailedEventFullInfoInteractor,
    private val id: Int
) : MainViewModel<DetailedEventScreenEvent, DetailedEventScreenViewState>() {
    private val userActionFlow = MutableSharedFlow<DetailedEventScreenEvent>(replay = 1)
    override val _viewState: MutableStateFlow<DetailedEventScreenViewState> =
        MutableStateFlow(DetailedEventScreenViewState.Loading)

    init {
        //subscribeOnUserActions()
        getDetailedEventFullInfoInteractor.invoke().onEach { event ->
            if (event.id == id) {
                _viewState.update {
                    DetailedEventScreenViewState.Display(
                        imageUri = event.url,
                        header = event.name,
                        description = event.description,
                        date = event.date,
                        address = event.address,
                        tags = event.tags,
                        presenter = event.speaker.mapToUi(),
                        nearestSubwayStation = event.nearestSubway,
                        mapImageUri = event.mapUrl,
                        comingPeople = event.attendees.mapPersonToUi(),
                        organizer = event.organizer,
                        isSubscribedToCommunity = false,
                        otherEventsOfCommunity = event.otherMeetings.mapEventListToUi(),
                        latitude = event.latitude,
                        longitude = event.longitude
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun obtain(event: DetailedEventScreenEvent) {
        userActionFlow.tryEmit(event)
    }

    private fun subscribeOnUserActions() {
        userActionFlow.debounce(300).onEach {

        }.launchIn(viewModelScope)
    }


    override fun viewState(): StateFlow<DetailedEventScreenViewState> {
        return _viewState
    }
}