package ru.lim1x.repository.mappers

import ru.lim1x.domain.models.CommunityRail
import ru.lim1x.domain.models.DeveloperCommunity
import ru.lim1x.domain.models.Event
import ru.lim1x.domain.models.Organizer
import ru.lim1x.domain.models.Person
import ru.lim1x.domain.models.PersonRail
import ru.lim1x.domain.models.Speaker
import ru.lim1x.repository.models.CommunityRailData
import ru.lim1x.repository.models.DeveloperCommunityData
import ru.lim1x.repository.models.EventData
import ru.lim1x.repository.models.OrganizerData
import ru.lim1x.repository.models.PersonData
import ru.lim1x.repository.models.PersonRailData
import ru.lim1x.repository.models.SpeakerData

internal fun List<EventData>.mapEventListToDomain(): List<Event> {
    return this.map {
        it.mapEventToDomain()
    }
}

internal fun EventData.mapEventToDomain(): Event {
    return Event(
        name = this.name,
        date = this.date,
        place = this.place,
        tags = this.tags,
        id = this.id,
        url = this.url
    )
}

internal fun CommunityRailData.mapCommunityRailToDomain(): CommunityRail {
    return CommunityRail(title = this.title, contentList = this.listId.mapCommunityListToDomain())
}

internal fun List<CommunityRailData>.mapCommunityRailListToDomain(): List<CommunityRail> {
    return this.map {
        it.mapCommunityRailToDomain()
    }
}

internal fun DeveloperCommunityData.mapCommunityToDomain(): DeveloperCommunity {
    return DeveloperCommunity(id = this.id, name = this.name, imageUri = this.imageUri)
}

internal fun List<DeveloperCommunityData>.mapCommunityListToDomain(): List<DeveloperCommunity> {
    return this.map {
        it.mapCommunityToDomain()
    }
}

internal fun PersonData.mapToDomain(): Person {
    return Person(
        id = this.id,
        name = this.name,
        mainTag = this.mainTag,
        avatarURL = this.imageUri as String
    )
}

internal fun List<PersonData>.mapPersonToDomain(): List<Person> {
    return this.map {
        it.mapToDomain()
    }
}

internal fun PersonRailData.mapToDomain(): PersonRail {
    return PersonRail(title = this.title, contentList = this.listPersons.mapPersonToDomain())
}

internal fun SpeakerData.mapToDomain(): Speaker {
    return Speaker(name = this.name, imageUri = this.imageUri, description = this.description)
}

internal fun OrganizerData.mapToDomain(): Organizer {
    return Organizer(id, imageUri, description, name)
}
