package ru.lim1x.repository.implementations.map_repository

import android.util.Log
import de.jensklingenberg.ktorfit.Callback
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.repositories.IMapRepository
import ru.lim1x.domain.models.Coordinates
import ru.lim1x.repository.ktor.Ktor
import ru.lim1x.repository.ktor.Response
import ru.lim1x.repository.ktor.createGeoCoderApi


class MapRepository : IMapRepository, KoinComponent {

    private val staticMapApiKey = "fee80b90-0b23-4bc3-b962-b0bd8abcdc58"
    private val geoCoderApiKey = "837e769c-93d5-4f48-99bc-7bdafd349f19"
    private val zoom = 16

    private val ktor: Ktor by inject()

    private val geoCoderApi = ktor.geofit.createGeoCoderApi()
    private val coordinatesFlow: MutableSharedFlow<Pair<String, String>> =
        MutableSharedFlow(replay = 1)


    override fun getMapUrlAndCoordinates(address: String): Flow<Pair<String?, Coordinates>> {
        return geoCoderApi.getGeoObjectCollectionByAddress(
            apiKey = geoCoderApiKey,
            address = address
        ).map {
            val coordinates =
                it.response?.GeoObjectCollection?.featureMember?.first()?.GeoObject?.Point?.pos?.replace(
                    " ",
                    ","
                )
            println("response ${it}")
            println("COORDINATES ${coordinates}")
            coordinatesFlow.tryEmit(Pair(address, coordinates!!))
            Pair(
                ktor.mapfit.baseUrl + "v1?ll=$coordinates&z=$zoom&pt=$coordinates,pm2vvl&apikey=$staticMapApiKey",
                Coordinates(
                    latitude = coordinates.substringAfter(',').toFloat(),
                    longitude = coordinates.substringBefore(',').toFloat()
                )
            )
        }.catch { e ->
            Log.e("MapRepository", e.stackTraceToString())
            emit(Pair(null.toString(), Coordinates(0f, 0f)))
        }
    }


    override fun getNearestSubwayName(address: String): Flow<String?> {
        return coordinatesFlow.mapLatest {
            if (address == it.first) {
                val res = geoCoderApi.getNearestSubwayStation(
                    apiKey = geoCoderApiKey,
                    coordinates = it.second
                ).map {
                    val response = it.response?.GeoObjectCollection?.featureMember?.first()
                        ?.GeoObject?.metaDataProperty?.GeocoderMetaData?.Address?.Components?.firstOrNull { it.kind == "metro" }?.name
                        ?: ""
                    response
                }.catch { e ->
                    Log.e("MapRepository", e.stackTraceToString())
                    emit(null.toString())
                }
                res.single()
            } else {
                (null.toString())
            }
        }

    }
}