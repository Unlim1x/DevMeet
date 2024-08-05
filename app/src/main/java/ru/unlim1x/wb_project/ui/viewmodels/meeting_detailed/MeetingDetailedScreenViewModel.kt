package ru.unlim1x.wb_project.ui.viewmodels.meeting_detailed

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
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
    private val getUserAvatarByIdUseCase: IGetUserAvatarByIdUseCase
) : MainViewModel<MeetingDetailedScreenEvent, MeetingDetailedScreenViewState>() {
    override val _viewState: MutableStateFlow<MeetingDetailedScreenViewState> =
        MutableStateFlow(MeetingDetailedScreenViewState.Loading)

    private val _meetingFlow: MutableStateFlow<MeetingDetailed?> = MutableStateFlow(null)
    private val _meetingExt: MutableStateFlow<MeetingDetailedExt?> = MutableStateFlow(null)
    private val _avatarsURL: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

    private val meetingFlow = _meetingFlow.asStateFlow()
    private val meetingExt = _meetingExt.asStateFlow()
    private val avatarsURL = _avatarsURL.asStateFlow()

    private var currentUserId: Int = -1
    private var meetingId: Int = -1
    private var meetingInitialValue: MeetingDetailedExt? = null

    init {
        viewModelScope.launch {
            observeMeetingFlow()
        }
    }

    private fun updateDisplayGoState(go: Boolean) {
        _viewState.value = (MeetingDetailedScreenViewState.Display(
            meetingExt,
            avatarsURL,
            go,
            meetingInitialValue
        ))
    }

    private fun loadDetails() {
        meetingDetailedInfoByIdUseCase.execute(meetingId).onEach {
            if (meetingId == it?.id) {
                _meetingFlow.value = it
                currentUserId = getCurrentUserUseCase.execute()
                _meetingExt.update { updateMeetingExt() }
                _meetingExt.value?.let { meeting ->
                    _avatarsURL.update { meeting.visitors.map { it.second } }
                }
                meetingInitialValue = _meetingExt.value
                meetingExt.value?.visitors?.any { it.first == currentUserId }
                    ?.let { updateDisplayGoState(it) }

            }
        }.launchIn(viewModelScope)

    }

    private fun observeMeetingFlow() {
        meetingFlow.onEach {
            if (meetingId == it?.id) {
                it.let {
                    _meetingExt.update {
                        updateMeetingExt()
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateMeetingExt(): MeetingDetailedExt? {
        val idAndAvatarList: MutableList<IdAndAvatar> = mutableListOf()
        meetingFlow.value?.visitorsIds?.forEach {
            idAndAvatarList.add(
                IdAndAvatar(
                    first = it,
                    second = getUserAvatarByIdUseCase.execute(it).orEmpty()
                )
            )
        }

        return meetingFlow.value?.mapToExt(visitors = idAndAvatarList)
    }

    private fun willGo(value: Boolean) {
        val succeed = setUserVisitingUseCase.execute(
            meetingId = meetingId,
            userId = currentUserId,
            isVisiting = value
        )
        if (succeed) {
            updateDisplayGoState(go = value)
        }
    }

    private fun reduce(event: MeetingDetailedScreenEvent.LoadScreen) {
        meetingId = event.meetingId
        loadDetails()
    }


    override fun obtain(event: MeetingDetailedScreenEvent) {
        when (event) {
            is MeetingDetailedScreenEvent.WillGo -> willGo(true)

            is MeetingDetailedScreenEvent.WillNotGo -> willGo(false)

            is MeetingDetailedScreenEvent.LoadScreen -> reduce(event)
        }
    }

    override fun viewState(): StateFlow<MeetingDetailedScreenViewState> {
        return _viewState.asStateFlow()
    }

}