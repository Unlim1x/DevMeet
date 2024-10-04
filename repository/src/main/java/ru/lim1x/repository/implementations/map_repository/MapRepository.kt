package ru.lim1x.repository.implementations.map_repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.repositories.IMapRepository
import ru.lim1x.repository.ktor.Ktor
import ru.lim1x.repository.ktor.createGeoCoderApi


class MapRepository : IMapRepository, KoinComponent {

    private val staticMapApiKey = "fee80b90-0b23-4bc3-b962-b0bd8abcdc58"
    private val geoCoderApiKey = "837e769c-93d5-4f48-99bc-7bdafd349f19"
    private val zoom = 16

    private val ktor: Ktor by inject()

    private val geoCoderApi = ktor.geofit.createGeoCoderApi()


    override fun getMapUrl(address: String): Flow<String?> {
        return geoCoderApi.getGeoObjectCollectionByAddress(
            apiKey = geoCoderApiKey,
            address = address
        ).map {
            val coordinates =
                it.response?.GeoObjectCollection?.featureMember?.first()?.GeoObject?.Point?.pos?.replace(
                    " ",
                    ","
                )
            println("COORDINATES ${coordinates}")
            ktor.mapfit.baseUrl + "v1?ll=$coordinates&z=$zoom&pt=$coordinates,pm2vvl&apikey=$staticMapApiKey"
        }
    }

    override fun getNearestSubwayName(address: String): String {
        TODO("Not yet implemented")
    }
}