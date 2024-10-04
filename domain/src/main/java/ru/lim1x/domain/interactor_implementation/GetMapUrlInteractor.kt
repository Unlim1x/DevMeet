package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.interfaces.interactors.IGetMapUrlInteractor
import ru.lim1x.domain.interfaces.repositories.IMapRepository

class GetMapUrlInteractor(private val mapRepository: IMapRepository) : IGetMapUrlInteractor {

    override fun execute(address: String): Flow<String?> {
        return mapRepository.getMapUrl("СПБ,$address")
    }
}