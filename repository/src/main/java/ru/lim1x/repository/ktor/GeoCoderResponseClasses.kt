package ru.lim1x.repository.ktor

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val response: GeoObjectCollectionResponse? = null
)

@Serializable
data class GeoObjectCollectionResponse(
    val GeoObjectCollection: GeoObjectCollection? = null
)

@Serializable
data class GeoObjectCollection(
    val metaDataProperty: MetaDataProperty? = null,
    val featureMember: List<FeatureMember>? = null
)

@Serializable
data class MetaDataProperty(
    val GeocoderResponseMetaData: GeocoderResponseMetaData? = null
)

@Serializable
data class GeocoderResponseMetaData(
    val request: String? = null,
    val results: String? = null,
    val found: String? = null
)

@Serializable
data class FeatureMember(
    val GeoObject: GeoObject? = null
)

@Serializable
data class GeoObject(
    val metaDataProperty: GeoObjectMetaDataProperty? = null,
    val name: String? = null,
    val description: String? = null,
    val boundedBy: BoundedBy? = null,
    val uri: String? = null,
    val Point: Point? = null
)

@Serializable
data class GeoObjectMetaDataProperty(
    val GeocoderMetaData: GeocoderMetaData? = null
)

@Serializable
data class GeocoderMetaData(
    val precision: String? = null,
    val text: String? = null,
    val kind: String? = null,
    val Address: Address? = null,
    val AddressDetails: AddressDetails? = null
)

@Serializable
data class Address(
    val country_code: String? = null,
    val formatted: String? = null,
    val postal_code: String? = null,
    val Components: List<Component>? = null
)

@Serializable
data class AddressDetails(
    val Country: Country? = null
)

@Serializable
data class Country(
    val AddressLine: String? = null,
    val CountryNameCode: String? = null,
    val CountryName: String? = null,
    val AdministrativeArea: AdministrativeArea? = null
)

@Serializable
data class AdministrativeArea(
    val AdministrativeAreaName: String? = null,
    val Locality: Locality? = null
)

@Serializable
data class Locality(
    val LocalityName: String? = null,
    val Thoroughfare: Thoroughfare? = null
)

@Serializable
data class Thoroughfare(
    val ThoroughfareName: String? = null,
    val Premise: Premise? = null
)

@Serializable
data class Premise(
    val PremiseNumber: String? = null,
    val PostalCode: PostalCode? = null
)

@Serializable
data class PostalCode(
    val PostalCodeNumber: String? = null
)

@Serializable
data class BoundedBy(
    val Envelope: Envelope? = null
)

@Serializable
data class Envelope(
    val lowerCorner: String? = null,
    val upperCorner: String? = null
)

@Serializable
data class Point(
    val pos: String? = null
)

@Serializable
data class Component(
    val kind: String? = null,
    val name: String? = null
)