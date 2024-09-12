package ru.lim1x.repository.mock_source


import ru.lim1x.domain.models.Tag
import ru.lim1x.repository.models.CommunityRailData
import ru.lim1x.repository.models.DeveloperCommunityData
import ru.lim1x.repository.models.EventData
import ru.lim1x.repository.models.PersonData
import ru.lim1x.repository.models.PersonRailData
import java.time.LocalDate
import kotlin.random.Random


internal class FakeDataSource {

    private val personsList: MutableList<PersonData> = mutableListOf()
    private val events: List<EventData>
        get() = fakeEvents

    val tags: List<Tag>
        get() = tagsList

    val usersTags: MutableList<Int> = mutableListOf()

    private val tagsList = MutableList(GPTLists.listOfTags.size) {
        Tag(it, GPTLists.listOfTags[it])
    }

    private val eventsQuantity = 60

    private val fakeEvents = MutableList(eventsQuantity) {
        combineEvent(it)
    }

    private fun fakeDescription(): String {
        return GPTLists.eventDescriptions.shuffled(Random).take(4).joinToString(separator = " ")
    }

    private fun fakeAttendeesQuantity() = Random.nextInt(0, 30)
    private val fakeAttendees = MutableList(eventsQuantity) {
        generateListOfPersons(size = fakeAttendeesQuantity())

    }


    private fun combineEvent(id: Int): EventData {
        return EventData(
            name = GPTLists.eventNames.shuffled(Random).random(),
            date = LocalDate.of(2024, Random.nextInt(9, 13), Random.nextInt(1, 30)),
            place = GPTLists.addresses.shuffled(Random).random(),
            tags = GPTLists.listOfTags.shuffled(Random).take(3),
            id = id,
            url = GPTLists.eventImagesUrls.shuffled(Random).random()
        )
    }

    private val fakeCommunities = MutableList(GPTLists.developerCommunities.size) {
        val community = GPTLists.developerCommunities.shuffled().random()
        DeveloperCommunityData(
            it,
            community,
            imageUri = GPTLists.developerCommunitiesUrls[community] as Any
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
            return events.subList(12 + skip, events.size - 1)
        else
            return emptyList()
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
}