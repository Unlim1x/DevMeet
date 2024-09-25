package ru.lim1x.domain.repository

import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.domain.models.Tag
import ru.lim1x.domain.models.User

class ProfileRepositoryStub(private val dataSource:DataSourceTest) : IProfileRepository {
    override fun loadProfile(userId: Int): StateFlow<User> {
        return dataSource.getCurrentUserInfo(userId)
    }


    override fun saveNumber(phoneNumber: String): Boolean {
        return dataSource.registerUserPhone(phoneNumber = phoneNumber)
    }

    override fun saveUserName(userId: Int, userName: String, userSurname: String): Boolean {
        return dataSource.saveUserName(username = userName, userSurname = userSurname)
    }

    override fun userId(): Int {
        return dataSource.userId()
    }

    override fun saveUserPhoto(stirngUri: String) {
        dataSource.updateUserPhoto(uriString = stirngUri)
    }

    override fun getUsersTags(): List<Tag> {
        return emptyList()
        //TODO("Not yet implemented")
    }

    override fun updateTag(tagId: Int) {
        //TODO("Not yet implemented")
    }
}