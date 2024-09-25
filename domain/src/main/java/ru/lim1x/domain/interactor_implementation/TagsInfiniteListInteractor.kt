package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.interactors.ITagsInfiniteListGetInteractor
import ru.lim1x.domain.interfaces.interactors.ITagsInfiniteListUpdateInteractor
import ru.lim1x.domain.interfaces.repositories.IEventRepository
import ru.lim1x.domain.models.Tag

internal class TagsInfiniteListGetInteractor : ITagsInfiniteListGetInteractor, KoinComponent {

    private val innerInteractor: InnerTagsInfiniteListInteractor by inject()

    override fun execute(): Flow<List<Tag>> {
        return innerInteractor.getTags()
    }

}

internal class TagsInfiniteListUpdateInteractor : ITagsInfiniteListUpdateInteractor,
    KoinComponent {


    private val innerInteractor: InnerTagsInfiniteListInteractor by inject()
    override fun execute(tagId: Int) {
        innerInteractor.updateTag(tagId)
    }
}


internal class InnerTagsInfiniteListInteractor : KoinComponent {


    private val userActionFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)
    private val getTagsUseCase: GetTagsInfiniteListUseCase by inject()

    init {
        userActionFlow.tryEmit(-10)
    }

    fun getTags(): Flow<List<Tag>> {
        //userActionFlow.tryEmit(-10)
        return getTagsUseCase.observeTags()
    }

    fun updateTag(tagId: Int) {
        userActionFlow.tryEmit(tagId)
    }

    fun observeUserActionFlow() = userActionFlow

}


internal class GetTagsInfiniteListUseCase : KoinComponent {
    private val profileRepository: IEventRepository by inject()
    private val tagsFlow: MutableStateFlow<List<Tag>> =
        MutableStateFlow(profileRepository.getAllTags())
    private val innerTagsInteractor: InnerTagsInfiniteListInteractor by inject()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeTags(): Flow<List<Tag>> =
        innerTagsInteractor.observeUserActionFlow().mapLatest { id ->
            if (id >= -1) {
                tagsFlow.update { list ->
                    val updatedList = list.map { tag ->
                        when {
                            id != -1 && tag.id == id -> tag.copy(isSelected = !tag.isSelected)
                            id != -1 && tag.id == -1 -> tag.copy(isSelected = false)
                            id == -1 -> tag.copy(isSelected = tag.id == -1)
                            else -> tag
                        }
                    }

                    if (updatedList.none { it.isSelected }) {
                        updatedList.map { tag ->
                            if (tag.id == -1) tag.copy(isSelected = true) else tag
                        }
                    } else {
                        updatedList
                    }
                }
            }
            println("UPDATE IN TAGS INTERACTOR FLOW: ${tagsFlow.value}")
            tagsFlow.value
        }
}