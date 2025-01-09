package com.williamfq.data.repository

import com.williamfq.data.dao.UserActivityDao
import com.williamfq.domain.repository.UserActivityRepository
import com.williamfq.domain.models.UserActivityStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserActivityRepositoryImpl @Inject constructor(
    private val userActivityDao: UserActivityDao
) : UserActivityRepository {

    override suspend fun incrementTextMessageCount(userId: String, period: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                userActivityDao.incrementMessageCount(userId, period)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun incrementCallCount(userId: String, isVideoCall: Boolean): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                userActivityDao.incrementCallCount(userId, isVideoCall)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun logReaction(userId: String, reaction: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                userActivityDao.logReaction(userId, reaction)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun getUserActivityStats(userId: String): Result<UserActivityStats> =
        withContext(Dispatchers.IO) {
            try {
                // Obtiene el modelo de la capa data
                val dataStats = userActivityDao.getUserActivityStats(userId)

                // Transforma el modelo a la capa domain
                val domainStats = UserActivityStats(
                    messagesText = dataStats.messagesText,
                    callsMade = dataStats.callsMade,
                    lastUpdated = dataStats.lastUpdated
                )

                // Devuelve el modelo transformado como Result.success
                Result.success(domainStats)
            } catch (e: Exception) {
                // Maneja las excepciones y encapsula en un Result.failure
                Result.failure(e)
            }
        }

}
