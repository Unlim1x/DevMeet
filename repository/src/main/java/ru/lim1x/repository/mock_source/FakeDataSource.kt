package ru.lim1x.repository.mock_source


import ru.lim1x.domain.models.Person
import ru.lim1x.domain.models.Tag
import ru.lim1x.repository.models.CommunityRailRepository
import ru.lim1x.repository.models.EventRepository
import java.time.LocalDate
import kotlin.random.Random


internal class FakeDataSource {
    val events: List<EventRepository>
        get() = fakeEvents

    val tags: List<Tag>
        get() = tagsList

    val usersTags: MutableList<Int> = mutableListOf()

    private val tagsList = MutableList(GPTLists.listOfTags.size) {
        Tag(it, GPTLists.listOfTags[it])
    }

    private val eventsQuantity = 20

    private val fakeEvents = MutableList(eventsQuantity) {
        combineEvent(it)
    }

    private fun fakeDescription(): String {
        return GPTLists.eventDescriptions.shuffled(Random).take(4).joinToString(separator = " ")
    }

    private val fakeAttendees = MutableList(eventsQuantity) {
        if (it < 7) {
            mutableMapOf(
                it to Person(
                    Random.nextInt(),
                    name = GPTLists.maleNames.shuffled(Random).random(),
                    avatarURL = ""
                )
            )
        } else {
            mutableMapOf(
                it to Person(
                    Random.nextInt(),
                    name = GPTLists.femaleNames.shuffled(Random).random(),
                    avatarURL = ""
                )
            )
        }

    }.shuffled().toMutableList()


    private fun combineEvent(id: Int): EventRepository {
        return EventRepository(
            name = GPTLists.eventNames.shuffled(Random).random(),
            date = LocalDate.of(2024, Random.nextInt(1, 13), Random.nextInt(1, 32)),
            place = GPTLists.addresses.shuffled(Random).random(),
            tags = GPTLists.listOfTags.shuffled(Random).take(3),
            id = id,
            url = GPTLists.eventImagesUrls.shuffled(Random).random()
        )
    }

    private val fakeCommunityRails: MutableList<CommunityRailRepository> =
        MutableList(2) {
            CommunityRailRepository(
                title = GPTLists.railCommunityTitles.shuffled().random(),
                listId = MutableList<Int>(5) {
                    Random.nextInt(1, GPTLists.developerCommunities.size + 1)
                }
            )
        }
}