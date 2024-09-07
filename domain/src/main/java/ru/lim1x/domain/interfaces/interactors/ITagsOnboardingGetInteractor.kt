package ru.lim1x.domain.interfaces.interactors

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.Tag

interface ITagsOnboardingGetInteractor {
    fun execute(): Flow<List<Tag>>

}