package ru.lim1x.repository.mock_source


import ru.lim1x.domain.models.Tag
import ru.lim1x.repository.models.CommunityRailData
import ru.lim1x.repository.models.DeveloperCommunityData
import ru.lim1x.repository.models.EventData
import ru.lim1x.repository.models.EventDetailedData
import ru.lim1x.repository.models.OrganizerData
import ru.lim1x.repository.models.PersonData
import ru.lim1x.repository.models.PersonRailData
import ru.lim1x.repository.models.SpeakerData
import java.time.LocalDate
import kotlin.random.Random


internal class FakeDataSource {

    private val personsList: MutableList<PersonData> = mutableListOf()
    private val events: List<EventData>
        get() = fakeEvents

    val tags: List<Tag>
        get() = tagsList

    val eventTags: List<Tag>
        get() = tagsEventList

    val usersTags: MutableList<Int> = mutableListOf()

    private val tagsList = MutableList(GPTLists.listOfTags.size) {
        Tag(it, GPTLists.listOfTags[it])
    }

    private val tagsEventList = MutableList(GPTLists.listOfTags.size) {
        Tag(it, GPTLists.listOfTags[it])
    }.apply { add(Tag(-1, "Все категории", isSelected = true)) }

    private val eventsQuantity = 100

    private val fakeCommunities = MutableList(GPTLists.developerCommunities.size) {
        val community = GPTLists.developerCommunities[it]
        DeveloperCommunityData(
            it,
            community,
            imageUri = GPTLists.developerCommunitiesUrls[community] as Any
        )
    }
    private val eventOrganizerMap: MutableMap<Int, Int> = mutableMapOf()



    private val fakeAttendees = MutableList(eventsQuantity) {
        generateListOfPersons(size = fakeAttendeesQuantity())

    }

    private val fakeEvents = MutableList(eventsQuantity) {
        combineEvent(it)
    }
    private val fakeDetailedEvents = MutableList(eventsQuantity) {
        combineDetailedEvent(it)
    }


    private fun fakeEventDescription(): String {
        return GPTLists.eventDescriptions.shuffled(Random).take(4).joinToString(separator = " ")
    }

    private fun fakeAttendeesQuantity() = Random.nextInt(0, 30)

    private fun fakeOrganizer(id: Int): OrganizerData {
        val community = fakeCommunities[id]
        return OrganizerData(
            id = community.id,
            name = community.name,
            imageUri = community.imageUri,
            description = GPTLists.communityDescriptions.shuffled().random()
        )
    }

    private fun fakeSpeaker(): SpeakerData {
        val random = when (Random.nextInt(1, 100) < 50) {
            true -> Sex.MALE
            false -> Sex.FEMALE
        }
        val person = generatePerson(random)
        return SpeakerData(
            name = person.name + " " + GPTLists.surnames.random(),
            imageUri = person.imageUri ?: "",
            description = GPTLists.speakerDescriptions.shuffled().random()
        )
    }



    private fun fakeCommunityRails() =
        CommunityRailData(
                title = GPTLists.railCommunityTitles.shuffled().random(),
                listId = fakeCommunities.shuffled(Random).subList(2, 7)
            )


    fun getMainEvents(): List<EventData> {

        return events.subList(0, 5).map {
            it.copy(date = LocalDate.now().plusDays(1))
        }
    }

    fun getSoonEvents(): List<EventData> {
        return events.subList(5, 12)
    }

    fun loadMoreEvents(limit: Int, skip: Int): List<EventData> {
        val leftEdge = 12 + skip
        val rightEdge = 12 + skip + limit

        if (rightEdge < events.size)
            return events.subList(12 + skip, 12 + skip + limit)
        else if (leftEdge < events.size - 2)
            return events.subList(12 + skip, events.size)
        else
            return emptyList()
    }

    fun loadMoreEvents(limit: Int, skip: Int, tagList: List<Int>): List<EventData> {
        if (tagList.contains(-1)) {
            return loadMoreEvents(limit, skip)
        } else {
            val filteredList = events.filter { event ->
                event.tags.any { tag -> tags.find { it.text == tag }?.id in tagList }
            }

            val filteredListSize = filteredList.size
            val leftEdge = skip
            val rightEdge = skip + limit


            if (rightEdge < filteredListSize)
                return filteredList.subList(skip, skip + limit)
            else if (leftEdge < filteredListSize - 2)
                return filteredList.subList(skip, filteredListSize)
            else
                return emptyList()
        }
    }

    fun loadCommunityRail(): CommunityRailData {
        return fakeCommunityRails()
    }

    enum class Sex {
        MALE,
        FEMALE
    }

    private fun randomPersonUri(sex: Sex): String {
        val baseUrl = "https://randomuser.me/api/portraits/"
        val stringBuilder = StringBuilder()
        stringBuilder.append(baseUrl)
        when (sex) {
            Sex.MALE -> stringBuilder.append("men/")
            Sex.FEMALE -> stringBuilder.append("women/")
        }
        stringBuilder.append(Random.nextInt(1, 100))
        return stringBuilder.append(".jpg").toString()
    }

    private fun fakePersonsToKnowQuantity() = Random.nextInt(5, 12)
    fun fakePersonsToKnow(): PersonRailData {
        val size = fakePersonsToKnowQuantity()
        return PersonRailData("Вы можете их знать", generateListOfPersons(size))

    }

    private fun generateListOfPersons(size: Int): MutableList<PersonData> {
        return MutableList(size) { personQuantity ->
            if (personQuantity < size / 2) {
                generatePerson(Sex.MALE)
            } else {
                generatePerson(Sex.FEMALE)
            }

        }.shuffled().toMutableList()
    }


    private fun generatePerson(sex: Sex): PersonData {
        val person = when (sex) {
            Sex.MALE -> {
                PersonData(
                    id = personsList.size, name = GPTLists.maleNames.shuffled().random(),
                    mainTag = GPTLists.listOfTags.random(), imageUri = randomPersonUri(sex)
                )
            }

            Sex.FEMALE -> {
                PersonData(
                    id = personsList.size, name = GPTLists.femaleNames.shuffled().random(),
                    mainTag = GPTLists.listOfTags.random(), imageUri = randomPersonUri(sex)
                )
            }
        }
        personsList.add(person)
        return person
    }

    private fun combineEvent(id: Int): EventData {
        eventOrganizerMap.putIfAbsent(id, fakeCommunities.random().id)
        return EventData(
            name = GPTLists.eventNames.shuffled(Random).random(),
            date = LocalDate.of(2024, Random.nextInt(9, 13), Random.nextInt(1, 30)),
            place = GPTLists.addresses.shuffled(Random).random(),
            tags = GPTLists.listOfTags.shuffled(Random).take(3),
            id = id,
            url = GPTLists.eventImagesUrls.shuffled(Random).random()
        )
    }

    private fun combineDetailedEvent(id: Int): EventDetailedData {
        val fakeEvent = fakeEvents.find { it.id == id }!!
        return EventDetailedData(
            name = fakeEvent.name,
            description = fakeEventDescription(),
            date = fakeEvent.date,
            shortAddress = fakeEvent.place,
            tags = fakeEvent.tags,
            id = fakeEvent.id,
            url = fakeEvent.url,
            speaker = fakeSpeaker(),
            address = "Санкт-Петербург, " + fakeEvent.place,
            attendees = fakeAttendees[id],
            organizer = fakeOrganizer(eventOrganizerMap[id]!!),
            otherMeetings = events.filter { event ->
                eventOrganizerMap.filterValues { it == eventOrganizerMap[id]!! }.keys.filter { it != id }
                    .any { event.id == it }
            },
            maxAttendeesQuantity = 30
        )
    }

    fun searchEvents(text: String): Triple<List<EventData>, CommunityRailData, Pair<String, List<EventData>>> {
        val filteredList = events.filter { event ->
            event.name.contains(
                text,
                ignoreCase = true
            ) || event.tags.any { tag -> tag.contains(text, ignoreCase = true) }
        }
        val rail = CommunityRailData(
            title = GPTLists.railCommunityTitles.shuffled()
                .find { it.contains(text, ignoreCase = true) }
                ?: GPTLists.railCommunityTitles.shuffled().random(),
            listId = fakeCommunities.shuffled(Random).subList(2, 7)
        )
        val additionHeader =
            GPTLists.railEventTitles.shuffled().find { it.contains(text, ignoreCase = true) }
                ?: GPTLists.railEventTitles.shuffled().random()
        val additionList = events.filter { event ->
            event.name.contains(
                text,
                ignoreCase = true
            ) || event.tags.any { tag -> tag.contains(text, ignoreCase = true) }
        }.shuffled().take(5)

        return Triple(filteredList.take(3), rail, Pair(additionHeader, additionList))
    }

    fun searchMore(text: String, limit: Int, skip: Int): List<EventData> {

        val filteredList = events.filter { event ->
            event.name.contains(
                text,
                ignoreCase = true
            ) || event.tags.any { tag -> tag.contains(text, ignoreCase = true) }
        }
        val leftEdge = 3 + skip
        val rightEdge = 3 + skip + limit
        println("searchListSize = ${filteredList.size}")
        if (rightEdge < filteredList.size)
            return filteredList.subList(3 + skip, 3 + skip + limit)
        else if (leftEdge < filteredList.size - 2)
            return filteredList.subList(3 + skip, filteredList.size)
        else
            return emptyList()

    }

    fun getDetailedEvent(id: Int): EventDetailedData {
        return fakeDetailedEvents[id]
    }

    fun getEventById(id: Int): EventData {
        return events[id]
    }



}