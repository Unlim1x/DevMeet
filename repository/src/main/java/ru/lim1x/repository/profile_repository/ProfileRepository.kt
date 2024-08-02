package ru.lim1x.repository.profile_repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.repository.mock_source.MockDataSource
import ru.lim1x.domain.models.User

internal class ProfileRepository(private val dataSource:MockDataSource) :IProfileRepository{
    override fun loadProfile(userId: Int): StateFlow<User> {
        return MutableStateFlow(dataSource.getCurrentUserInfo(userId))
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
}