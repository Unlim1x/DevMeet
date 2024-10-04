package ru.lim1x.repository.ktor

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val response: GeoObjectCollectionResponse?
)

@Serializable
data class GeoObjectCollectionResponse(
    val GeoObjectCollection: GeoObjectCollection?
)

@Serializable
data class GeoObjectCollection(
    val featureMember: List<FeatureMember>?
)

@Serializable
data class FeatureMember(
    val GeoObject: GeoObject?
)

@Serializable
data class GeoObject(
    val Point: Point?
)

@Serializable
data class Point(
    val pos: String?
)