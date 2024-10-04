package ru.lim1x.repository.ktor

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import kotlinx.coroutines.flow.Flow

internal interface MapApi {

    @GET("v1?ll={pos}&spn=0.016457,0.00619&apikey={key}")
    fun getMapFlow(@Path("pos") pos: String, @Path("key") apiKey: String): Flow<String>


}