package ru.lim1x.domain.interfaces.interactors

import ru.lim1x.domain.models.RailType

interface ILoadRailInteractor {
    fun execute(railType: RailType): Unit
}