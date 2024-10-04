package ru.lim1x.repository.ktor

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import kotlinx.coroutines.flow.Flow

internal interface GeoCoderApi {
    @GET("1.x/?apikey={key}&geocode={address}&format=json")
    fun getGeoObjectCollectionByAddress(
        @Path("key") apiKey: String,
        @Path("address") address: String
    ): Flow<Response>
}