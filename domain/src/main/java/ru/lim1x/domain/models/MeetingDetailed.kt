package ru.lim1x.domain.models

data class MeetingDetailed(
    val name: String,
    val timeAndPlace: TimeAndPlace,
    val isFinished: Boolean,
    val tags: List<String>,
    val description: String,
    val visitorsIds:List<Int>,
    val id: Int = 0
){
    fun mapToExt(visitors:List<IdAndAvatar>):MeetingDetailedExt{
        return MeetingDetailedExt(
                name, timeAndPlace, isFinished, tags, description, visitors, id
            )

    }
}

data class MeetingDetailedExt(
    val name: String,
    val timeAndPlace: TimeAndPlace,
    val isFinished: Boolean,
    val tags: List<String>,
    val description: String,
    val visitors:List<IdAndAvatar>,
    val id: Int = 0
)


