package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.interactors.IGetMainScreenSearchState
import ru.lim1x.domain.interfaces.interactors.ISearchRequestInteractor
import ru.lim1x.domain.interfaces.repositories.ISearchRepository
import ru.lim1x.domain.models.Rail
import ru.lim1x.domain.models.RailType
import ru.lim1x.domain.models.SearchRequest
import ru.lim1x.domain.models.SearchState

internal class SearchRequestInteractor : ISearchRequestInteractor, KoinComponent {

    private val innerSearch: InnerSearchUseCase by inject()
    override fun invoke(request: SearchRequest) {
        innerSearch.search(request)

    }
}

internal class InnerSearchUseCase : KoinComponent {
    private val userInputFlow = MutableSharedFlow<SearchRequest>(replay = 1)
    fun search(request: SearchRequest) {
        userInputFlow.tryEmit(request)
    }

    fun observeFlow(): SharedFlow<SearchRequest> {
        return userInputFlow
    }
}

internal class GetSearchStateInteractor : IGetMainScreenSearchState, KoinComponent {

    private val innerSearch: InnerSearchUseCase by inject()
    private val searchRepository: ISearchRepository by inject()
    private var state =
        SearchState(emptyList(), Rail(railType = RailType.Nothing, content = ""), "", emptyList())
    private var previousSearch = ""
    private var skip = 0
    private var limit = 5

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun invoke(): Flow<SearchState> {
        return innerSearch.observeFlow().debounce(300).mapLatest {
            when (it) {
                is SearchRequest.Search -> {
                    if (previousSearch != it.text) {
                        previousSearch = it.text
                        state = searchRepository.search(it.text)
                        skip = 0
                    }
                    state
                }

                SearchRequest.SearchMore -> {
                    println("Searching more")
                    println("previous list size ${state.searchedEvents.size}")
                    state = state.copy(
                        searchedEvents = state.searchedEvents.toMutableList().apply {
                            addAll(
                                searchRepository.searchMore(
                                    previousSearch,
                                    limit,
                                    skip
                                )
                            )
                        })
                    println("after loading list size${state.searchedEvents.size}")
                    skip += limit
                    state
                }
            }
        }
    }
}