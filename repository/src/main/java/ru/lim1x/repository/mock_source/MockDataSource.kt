package ru.lim1x.repository.mock_source

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.update
import ru.lim1x.domain.models.AuthorizationResult
import ru.lim1x.domain.models.Community
import ru.lim1x.domain.models.CommunityDetailed
import ru.lim1x.domain.models.Coordinates
import ru.lim1x.domain.models.LoremIpsum
import ru.lim1x.domain.models.Meeting
import ru.lim1x.domain.models.MeetingDetailed
import ru.lim1x.domain.models.TimeAndPlace
import ru.lim1x.domain.models.User
import java.lang.Thread.State

internal class MockDataSource {


    private val listOfTags = listOf("Junior", "Python", "Moscow")
    private val visitorsIds = listOf(2, 3, 4, 5)
    private val visitorAvatarUrl =
        "https://10wallpaper.com/wallpaper/1280x1024/2012/Ann_Sophie_2020_Fashion_Model_Celebrity_Photo_1280x1024.jpg"
    private val communityAvatarUrl = "https://sun9-15.userapi.com/impg/oSoxeolE-9kQoZ7fclxO_sgWttivq1I_QjLUnQ/YgvDRmvyiNA.jpg?size=604x508&quality=96&sign=cd296b6740a7786c23aff88057a1fa4f&type=album"

    private var currentUser: User = User(
        name = "",
        phone = "",
        id = 1,
        avatarURL = visitorAvatarUrl,
        hasAvatar = true
    )

    private val meeting = Meeting(
        name = "Developer meeting",
        timeAndPlace = TimeAndPlace(
            place = "Moscow",
            date = 13,
            month = 9,
            year = 2024
        ),
        isFinished = false,
        tags = listOfTags,
        id = 0
    )

    private val community=Community(
        imageUrl = communityAvatarUrl,
        name = "Designa",
        quantityMembers = 10000,
        id = 0
    )
    private val communityDetailed=CommunityDetailed(
        imageUrl = communityAvatarUrl,
        name = "Designa",
        quantityMembers = 10000,
        id = 0,
        description = LoremIpsum.Short.text,
        coordinates = Coordinates(1f,1f)
    )



    private val listCommunities= MutableList(20){
        community.copy(id = it)
    }

    private val listMeetingsIdVisitingByUser: MutableList<Int> = mutableListOf()

    private fun visitorsMap() = listCommunities.associateBy({it.id}, {visitorsIds}).toMutableMap().map {entry->
        var list = entry.value
        //Log.e("Mock1", entry.toString())
        if (listMeetingsIdVisitingByUser.any { it == entry.key }){
            list = list.plus(currentUser.id)
        }
        Log.e("Mock2", list.toString())
        list
    }

    private val meetingDetailed:MutableStateFlow<MeetingDetailed> = MutableStateFlow(
        MeetingDetailed(
        name = "Developer meeting",
        timeAndPlace = TimeAndPlace(
            place = "Moscow",
            date = 13,
            month = 9,
            year = 2024
        ),
        isFinished = true,
        tags = listOfTags,
        id = 0,
        description = LoremIpsum.Short.text,
        visitorsIds = visitorsMap()[0]

    ))

    private val stateMeeting:MutableStateFlow<MeetingDetailed> = meetingDetailed


    private fun updateMeetingDetailed(meetingId:Int){

        meetingDetailed.update {
            meetingDetailed.value.copy(id = meetingId, visitorsIds = visitorsMap()[meetingId])
        }

    }


    private val listMeetingsAll: MutableList<Meeting> = MutableList(15) {
       meeting.copy(isFinished = it%4==0, id = it)

    }

    private val listMeetingsActive: MutableList<Meeting> = MutableList(3) {
        meeting.copy(id=it)
    }


    private val listMeetingsFinished: MutableList<Meeting> = MutableList(3) {
        meeting.copy(id = it, isFinished = true)
    }


    fun getCommunities():List<Community>{
        return listCommunities
    }
    fun getCommunityInfo(communityId:Int):CommunityDetailed{
        return communityDetailed.copy(id = communityId)
    }

    fun getAllMeetings(): List<Meeting> {
        return listMeetingsAll
    }

    fun getActiveMeetings(): List<Meeting> {
        return listMeetingsActive
    }

    fun getFinishedMeetings(): List<Meeting> {
        return listMeetingsFinished
    }

    fun getVisitingMeetings(): List<Meeting> {
        return listMeetingsAll.filter { listMeetingsIdVisitingByUser.contains(it.id) }
    }

    fun addUserToVisitingList(userId: Int, meetingId: Int): Boolean {
        listMeetingsIdVisitingByUser.add(
            meetingId
        )
        updateMeetingDetailed(meetingId)
        return true
    }

    fun removeUserFromVisitingList(meetingId: Int): Boolean {
        try {
            listMeetingsIdVisitingByUser.removeIf { it == meetingId }
            updateMeetingDetailed(meetingId)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun getDetailedMeetingInfo(meetingId: Int): MutableStateFlow<MeetingDetailed> {
        updateMeetingDetailed(meetingId)
        return stateMeeting
    }

    fun saveUserName(username: String, userSurname: String): Boolean {
        currentUser = currentUser.copy(name = username, surname = userSurname)
        return true
    }

    fun registerUserPhone(phoneNumber: String): Boolean {
        currentUser = currentUser.copy(phone = phoneNumber)
        return true
    }

    fun getCurrentUserInfo(userId: Int):User{
        return currentUser
    }

    fun getVisitorAvatarById(id:Int):String{
        return visitorAvatarUrl
    }


    fun validateCode(code:String):AuthorizationResult{
        return if (listOf("4568","1234","5555").any { code == it })
            AuthorizationResult(true, currentUser.id)
        else
            AuthorizationResult(false, -1)
    }

    fun userId() = currentUser.id
}