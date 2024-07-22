package ru.unlim1x.wb_project.ui.viewmodels.meeting_detailed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetCurrentUserIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetMeetingDetailedInfoByIdUseCase
import ru.lim1x.domain.interfaces.usecases.ISetUserVisitingMeetingValueUseCase
import ru.lim1x.domain.models.MeetingDetailedExt
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class MeetingDetailedScreenViewModel(
    private val meetingDetailedInfoByIdUseCase: IGetMeetingDetailedInfoByIdUseCase,
    private val setUserVisitingUseCase: ISetUserVisitingMeetingValueUseCase,
    private val getCurrentUserUseCase: IGetCurrentUserIdUseCase
) : MainViewModel<MeetingDetailedScreenEvent, MeetingDetailedScreenViewState>() {
    private val _viewState: MutableLiveData<MeetingDetailedScreenViewState> =
        MutableLiveData(MeetingDetailedScreenViewState.Init)

    private lateinit var meetingFlow: Flow<MeetingDetailedExt>
    private lateinit var avatarsURL: Flow<List<String>>

    private lateinit var meetingInitialValue :MeetingDetailedExt

    override fun obtain(event: MeetingDetailedScreenEvent) {
        when (event) {
            is MeetingDetailedScreenEvent.OpenScreen -> reduce(
                event,
                MeetingDetailedScreenViewState.Init
            )

            is MeetingDetailedScreenEvent.WillGo -> reduce(
                event, MeetingDetailedScreenViewState.DisplayNotGo(
                    meetingFlow,
                    avatarsURL,
                    false,
                    meetingInitialValue
                )
            )

            is MeetingDetailedScreenEvent.WillNotGo -> reduce(
                event, MeetingDetailedScreenViewState.DisplayGo(
                    meetingFlow,
                    avatarsURL,
                    true,
                    meetingInitialValue
                )
            )


        }
    }

    private fun reduce(
        event: MeetingDetailedScreenEvent,
        state: MeetingDetailedScreenViewState.Init
    ) {
        when (event) {
            is MeetingDetailedScreenEvent.OpenScreen -> {
                loadDetails(event.meetingId)
            }

            else -> throw NotImplementedError("Unexpected state")
        }
    }

    fun loadDetails(meetingId: Int) {
        viewModelScope.launch {

            meetingFlow = meetingDetailedInfoByIdUseCase.execute(meetingId)
            val currentUserId = getCurrentUserUseCase.execute()

            avatarsURL = meetingFlow.map { it.visitors.map { it.second } }
            meetingInitialValue = meetingFlow.first()
            Log.e("AAAAA", "size= ${avatarsURL.last().size}")
            if (meetingFlow.last().visitors.any { it.first == currentUserId }) {
                _viewState.postValue(
                    MeetingDetailedScreenViewState.DisplayGo(
                        meeting = meetingFlow,
                        avatarsURL,
                        true,
                        meetingInitialValue
                    )
                )
            } else {
                _viewState.postValue(
                    MeetingDetailedScreenViewState.DisplayNotGo(
                        meeting = meetingFlow,
                        avatarsURL,
                        false,
                        meetingInitialValue
                    )
                )
            }
        }
    }

    private fun reduce(
        event: MeetingDetailedScreenEvent,
        state: MeetingDetailedScreenViewState.DisplayGo
    ) {

        when (event) {
            is MeetingDetailedScreenEvent.WillNotGo -> {
                fetchNotGo(event.meetingId)
            }

            else -> throw NotImplementedError("Unexpected state")
        }
    }

    private fun reduce(
        event: MeetingDetailedScreenEvent,
        state: MeetingDetailedScreenViewState.DisplayNotGo
    ) {

        when (event) {
            is MeetingDetailedScreenEvent.WillGo -> {
                fetchGo(event.meetingId)
            }

            else -> throw NotImplementedError("Unexpected state")
        }
    }

    override fun viewState(): LiveData<MeetingDetailedScreenViewState> {
        return _viewState
    }

    private fun fetchGo(meetingId: Int) {
        viewModelScope.launch {
            setUserVisitingUseCase.execute(
                userId = getCurrentUserUseCase.execute(),
                isVisiting = true,
                meetingId = meetingId
            )
            //meetingFlow = meetingDetailedInfoByIdUseCase.execute(meetingId)
            //avatarsURL = meetingFlow.map { it.visitors.map { it.second } }
            Log.e("AAAAA", "size= ${avatarsURL.last().size}")
            _viewState.postValue(
                MeetingDetailedScreenViewState.DisplayGo(
                    meeting = meetingFlow,
                    avatarsURL,
                    true,
                    meetingInitialValue
                )
            )
        }
    }

    private fun fetchNotGo(meetingId: Int) {
        viewModelScope.launch {
            setUserVisitingUseCase.execute(
                userId = getCurrentUserUseCase.execute(),
                isVisiting = false,
                meetingId = meetingId
            )
            Log.e("AAAAA", "size= ${avatarsURL.last().size}")
            _viewState.postValue(
                MeetingDetailedScreenViewState.DisplayNotGo(
                    meeting = meetingFlow,
                    avatarsURL,
                    false,
                    meetingInitialValue
                )
            )
        }
    }
}