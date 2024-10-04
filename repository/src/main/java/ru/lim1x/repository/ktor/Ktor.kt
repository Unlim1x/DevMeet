package ru.lim1x.repository.ktor

import de.jensklingenberg.ktorfit.converter.FlowConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

private val httpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json { isLenient = true; ignoreUnknownKeys = true })
    }
    install(Logging) {
        level = LogLevel.ALL
    }
}
private val ktorfitMap = ktorfit {
    baseUrl("https://static-maps.yandex.ru/")
    httpClient(httpClient)
    converterFactories(
        FlowConverterFactory(),
    )

}
private val ktorfitGeo = ktorfit {
    baseUrl("https://geocode-maps.yandex.ru/")
    httpClient(httpClient)
    converterFactories(
        FlowConverterFactory(),
    )

}

data object Ktor : KoinComponent {
    val mapfit = ktorfitMap
    val geofit = ktorfitGeo
}