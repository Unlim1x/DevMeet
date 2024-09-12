package ru.lim1x.domain.interfaces.interactors

interface ILoadMoreInfiniteListInteractor {
    fun execute(currentLoadedListSize: Int): Unit
}