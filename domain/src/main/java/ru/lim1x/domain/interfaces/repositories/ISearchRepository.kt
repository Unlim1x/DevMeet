package ru.lim1x.domain.interfaces.repositories

import ru.lim1x.domain.models.Event
import ru.lim1x.domain.models.SearchState

interface ISearchRepository {
    fun search(text: String): SearchState
    fun searchMore(text: String, limit: Int, skip: Int): List<Event>
}