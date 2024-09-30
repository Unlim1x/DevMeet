package ru.lim1x.domain.interfaces.interactors

import ru.lim1x.domain.models.SearchRequest

interface ISearchRequestInteractor {
    fun invoke(request: SearchRequest)
}