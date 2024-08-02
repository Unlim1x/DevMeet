package ru.unlim1x.wb_project.ui.viewmodels.meeting_detailed

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetCurrentUserIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetMeetingDetailedInfoByIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetUserAvatarByIdUseCase
import ru.lim1x.domain.interfaces.usecases.ISetUserVisitingMeetingValueUseCase
import ru.lim1x.domain.models.IdAndAvatar
import ru.lim1x.domain.models.MeetingDetailed
import ru.lim1x.domain.models.MeetingDetailedExt
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class MeetingDetailedScreenViewModel(
    private val meetingDetailedInfoByIdUseCase: IGetMeetingDetailedInfoByIdUseCase,
    private val setUserVisitingUseCase: ISetUserVisitingMeetingValueUseCase,
    private val getCurrentUserUseCase: IGetCurrentUserIdUseCase,
    private val getUserAvatarByIdUseCase: IGetUserAvatarByIdUseCase,
    private val meetingId: Int
) : MainViewModel<MeetingDetailedScreenEvent, MeetingDetailedScreenViewState>() {
    private val _viewState: MutableLiveData<MeetingDetailedScreenViewState> =
        MutableLiveData(MeetingDetailedScreenViewState.Init)

    private lateinit var meetingFlow: MutableStateFlow<MeetingDetailed>
    private lateinit var meetingExt: MutableStateFlow<MeetingDetailedExt>
    private lateinit var avatarsURL: Flow<List<String>>
    private var currentUserId: Int = 0
    private lateinit var meetingInitialValue :MeetingDetailedExt

    init {
        viewModelScope.launch {
            meetingFlow = meetingDetailedInfoByIdUseCase.execute(meetingId)
            currentUserId = getCurrentUserUseCase.execute()
            mapForView()
            avatarsURL = meetingExt.map { it.visitors.map { it.second } }
            meetingInitialValue = meetingExt.value
            loadDetails(meetingId)
            observeMeetingFlow()
        }

    }

    override fun obtain(event: MeetingDetailedScreenEvent) {
        when (event) {
            is MeetingDetailedScreenEvent.OpenScreen -> reduce(
                event,
                MeetingDetailedScreenViewState.Init
            )

            is MeetingDetailedScreenEvent.WillGo -> reduce(
                event, MeetingDetailedScreenViewState.DisplayNotGo(
                    meetingExt,
                    avatarsURL,
                    false,
                    meetingInitialValue
                )
            )

            is MeetingDetailedScreenEvent.WillNotGo -> reduce(
                event, MeetingDetailedScreenViewState.DisplayGo(
                    meetingExt,
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
                //loadDetails(event.meetingId)
            }

            else -> throw NotImplementedError("Unexpected state")
        }
    }

    private fun loadDetails(meetingId: Int) {

            if (meetingExt.value.visitors.any { it.first == currentUserId }) {
                _viewState.postValue(
                    MeetingDetailedScreenViewState.DisplayGo(
                        meeting = meetingExt,
                        avatarsURL,
                        true,
                        meetingInitialValue
                    )
                )
            } else {
                _viewState.postValue(
                    MeetingDetailedScreenViewState.DisplayNotGo(
                        meeting = meetingExt,
                        avatarsURL,
                        false,
                        meetingInitialValue
                    )
                )
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

    private fun mapForView(){
        meetingExt = MutableStateFlow(updateMeetingExt())

    }

    private fun observeMeetingFlow(){
        meetingFlow.onEach {
            meetingExt.update {
               updateMeetingExt()
            }
        }.launchIn(viewModelScope)
    }

    private fun updateMeetingExt():MeetingDetailedExt{
        val idAndAvatarList: MutableList<IdAndAvatar> = mutableListOf()
        meetingFlow.value.visitorsIds.forEach {
            idAndAvatarList.add(IdAndAvatar(first = it, second = getUserAvatarByIdUseCase.execute(it)))
        }
        return meetingFlow.value.mapToExt(visitors = idAndAvatarList)
    }

    private fun fetchGo(meetingId: Int) {
        viewModelScope.launch {
            setUserVisitingUseCase.execute(
                userId = getCurrentUserUseCase.execute(),
                isVisiting = true,
                meetingId = meetingId
            )
            avatarsURL = meetingExt.map { it.visitors.map { it.second } }
            _viewState.postValue(
                MeetingDetailedScreenViewState.DisplayGo(
                    meeting = meetingExt,
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
            avatarsURL = meetingExt.map { it.visitors.map { it.second } }
            _viewState.postValue(
                MeetingDetailedScreenViewState.DisplayNotGo(
                    meeting = meetingExt,
                    avatarsURL,
                    false,
                    meetingInitialValue
                )
            )
        }
    }
}