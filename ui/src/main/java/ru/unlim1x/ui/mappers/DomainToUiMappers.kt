package ru.unlim1x.ui.mappers

import ru.lim1x.domain.models.CommunityRail
import ru.lim1x.domain.models.DeveloperCommunity
import ru.lim1x.domain.models.Event
import ru.lim1x.domain.models.Person
import ru.lim1x.domain.models.PersonRail
import ru.lim1x.domain.models.Tag
import ru.unlim1x.ui.kit.tag.TagUi
import ru.unlim1x.ui.models.CommunityRailUI
import ru.unlim1x.ui.models.CommunityUI
import ru.unlim1x.ui.models.EventUI
import ru.unlim1x.ui.models.PersonRailUi
import ru.unlim1x.ui.models.PersonUi
import java.time.format.DateTimeFormatter

internal fun List<Event>.mapEventListToUi(): List<EventUI> {
    return if (this.isNotEmpty())
        this.map {
            it.mapEventToUi()
        }
    else
        emptyList()
}

internal fun Event.mapEventToUi(): EventUI {
    return EventUI(
        name = this.name,
        date = this.date.format(DateTimeFormatter.ofPattern("d MMMM")),
        address = this.place,
        tags = this.tags,
        id = this.id,
        imageUri = this.url
    )
}

internal fun CommunityRail.mapCommunityRailToUi(): CommunityRailUI {
    return CommunityRailUI(
        title = this.title,
        contentList = this.contentList.mapCommunityListToUi()
    )
}

internal fun List<CommunityRail>.mapCommunityRailListToUi(): List<CommunityRailUI> {
    return this.map {
        it.mapCommunityRailToUi()
    }
}

internal fun DeveloperCommunity.mapCommunityToUi(): CommunityUI {
    return CommunityUI(
        id = this.id,
        name = this.name,
        imageUri = this.imageUri,
        isSubscribed = false
    )
}

internal fun List<DeveloperCommunity>.mapCommunityListToUi(): List<CommunityUI> {
    return this.map {
        it.mapCommunityToUi()
    }
}

internal fun Person.mapToDomain(): PersonUi {
    return PersonUi(
        id = this.id,
        name = this.name,
        mainTag = this.mainTag ?: "",
        imageUri = this.avatarURL ?: ""
    )
}

internal fun List<Person>.mapPersonToUi(): List<PersonUi> {
    return this.map {
        it.mapToDomain()
    }
}

internal fun List<Tag>.mapTagToUi(): List<TagUi> {
    return this.map {
        TagUi(id = it.id, text = it.text, isSelected = it.isSelected)
    }
}

internal fun PersonRail.mapToPersonRailUi(): PersonRailUi {
    return PersonRailUi(title = this.title, contentList = this.contentList.mapPersonToUi())
}