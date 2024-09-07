package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.interactors.ITagsOnboardingGetInteractor
import ru.lim1x.domain.interfaces.interactors.ITagsOnboardingUpdateInteractor
import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.domain.models.Tag


internal class TagsOnboardingGetInteractor() : ITagsOnboardingGetInteractor, KoinComponent {

    private val innerInteractor: InnerTagsInteractor by inject()

    override fun execute(): Flow<List<Tag>> {
        return innerInteractor.getTags()
    }

}

internal class TagsOnboardingUpdateInteractor() : ITagsOnboardingUpdateInteractor, KoinComponent {


    private val innerInteractor: InnerTagsInteractor by inject()
    override fun execute(tagId: Int) {
        innerInteractor.updateTag(tagId)
    }
}


internal class InnerTagsInteractor() : KoinComponent {


    private val userActionFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)
    private val getTagsUseCase: GetTagsUseCase by inject()

    fun getTags(): Flow<List<Tag>> {
        userActionFlow.tryEmit(-1)
        return getTagsUseCase.observeTags()
    }

    fun updateTag(tagId: Int) {
        userActionFlow.tryEmit(tagId)
    }

    fun observeUserActionFlow() = userActionFlow

}


internal class GetTagsUseCase() : KoinComponent {
    private val profileRepository: IProfileRepository by inject()
    private val tagsFlow: MutableStateFlow<List<Tag>> =
        MutableStateFlow(profileRepository.getUsersTags())
    private val innerTagsInteractor: InnerTagsInteractor by inject()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeTags(): Flow<List<Tag>> = innerTagsInteractor.observeUserActionFlow().mapLatest {
        println(it)
        if (it >= 0) {
            println(it)
            profileRepository.updateTag(it)
            tagsFlow.update { list ->
                val tag = list[it].copy(isSelected = !list[it].isSelected)
                val newList = list.toMutableList()
                newList[it] = tag
                newList
            }
        }
        tagsFlow.value
    }
}


