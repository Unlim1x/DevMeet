package ru.unlim1x.ui.screens.event_detailed


import ru.lim1x.domain.models.Organizer
import ru.lim1x.domain.models.Tag
import ru.unlim1x.ui.models.CommunityUI
import ru.unlim1x.ui.models.EventUI
import ru.unlim1x.ui.models.PersonUi
import ru.unlim1x.ui.models.PresenterUi
import java.time.LocalDate

internal sealed class DetailedEventScreenViewState {
    data object Loading:DetailedEventScreenViewState()
    data class Display(val imageUri:Any, val header:String, val description:String,
        val date: LocalDate, val address:String, val tags:List<Tag>,
                       val presenter: PresenterUi,
                       val nearestSubwayStation: String,
                       val mapImageUri: Any,
                       val comingPeople: List<PersonUi>,
                       val organizer: Organizer,
                       val isSubscribedToCommunity: Boolean,
                       val otherEventsOfCommunity: List<EventUI>,
                       val latitude: Float? = null,
                       val longitude: Float? = null
    ) : DetailedEventScreenViewState()
}