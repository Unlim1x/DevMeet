package ru.lim1x.repository.profile_repository

import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.domain.models.Tag
import ru.lim1x.domain.models.User
import ru.lim1x.repository.mock_source.FakeDataSource
import ru.lim1x.repository.mock_source.MockDataSource

internal class ProfileRepository(
    private val dataSource: MockDataSource,
    private val newDataSource: FakeDataSource
) : IProfileRepository {
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
        return newDataSource.tags
    }

    override fun updateTag(tagId: Int) {
        //todo: запрос на сервер или в базу

        if (newDataSource.usersTags.contains(tagId))
            newDataSource.usersTags.remove(tagId)
        else
            newDataSource.usersTags.add(tagId)
    }
}